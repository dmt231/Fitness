package com.example.fitness.explore.plan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_workout.AdapterListWorkoutInPlan
import com.example.fitness.databinding.LayoutDetailPlanBinding
import com.example.fitness.explore.workout.DetailWorkout
import com.example.fitness.explore.workout.excercise.DetailExercise
import com.example.fitness.model.Plan
import com.example.fitness.model.Workout
import com.example.fitness.repository.WorkoutRepository

class DetailPlan : Fragment() {
    private lateinit var viewBinding : LayoutDetailPlanBinding
    private lateinit var workoutRepository: WorkoutRepository
    private lateinit var adapter : AdapterListWorkoutInPlan
    private var plan : Plan? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutDetailPlanBinding.inflate(inflater, container, false)
        workoutRepository = WorkoutRepository()
        getData()
        bindData()
        viewBinding.linearLayoutMain.setOnClickListener {

        }
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        setUpRecyclerView()
        getWorkoutInPlan()
        return viewBinding.root
    }

    private fun setUpRecyclerView() {
        viewBinding.recyclerViewListWorkout.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewListWorkout.layoutManager = layout
        adapter = AdapterListWorkoutInPlan(plan!!.listWorkout, object : AdapterListWorkoutInPlan.WorkoutItemClickListener{
            override fun passIdWorkout(idWorkout: String) {
                changeToDetailWorkout(idWorkout)
            }
        })
        viewBinding.recyclerViewListWorkout.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getWorkoutInPlan() {
        for(workoutModel in plan?.listWorkout!!) {
            if (workoutModel.workout != "Rest") {
                workoutRepository.getWorkoutByDocumentId(
                    workoutModel.workout,
                    object : WorkoutRepository.OnCompleteListener {
                        override fun onCompleteListener(workout: Workout) {
                            workoutModel.time = workout.time.toString()
                            workoutModel.img = workout.imgCovered
                            workoutModel.numberExercise = workout.listExercise?.size.toString()
                            adapter.notifyDataSetChanged()
                        }
                    })
            }
        }
    }

    private fun getData(){
        val bundle = arguments
        if(bundle != null){
            this.plan = bundle["plan"] as Plan
        }
    }
    @SuppressLint("SetTextI18n")
    private fun bindData(){
        Glide.with(viewBinding.image)
            .load(plan?.imgCover)
            .into(viewBinding.image)
        viewBinding.nameWorkout.text = plan?.namePlan
        viewBinding.txtDescription.text = plan?.overview
        viewBinding.dayTxt.text = plan?.dayOfWeek + " Ngày"
        when(plan?.goal){
            "Athleticism" ->{ viewBinding.goal.text = "Vận Động Viên" }
            "Strength" -> { viewBinding.goal.text = "Sức Mạnh" }
            "Size" -> { viewBinding.goal.text = "Kích Thước" }
            "Home" -> { viewBinding.goal.text = "Tại Nhà" }
        }
        when(plan?.difficulty) {
            "Advanced" -> {
                viewBinding.level.text = "Trung Bình"
            }
            "Hard" -> {
                viewBinding.level.text = "Khó"
            }
            "Beginner" -> {
                viewBinding.level.text = "Dễ"
            }
        }
        if(plan?.repeat!!.isNotEmpty()){
            viewBinding.numberRepeat.text = plan?.repeat
        }else{
            viewBinding.numberRepeat.visibility = View.INVISIBLE
            viewBinding.imgRepeat.visibility = View.INVISIBLE
        }
    }
    private fun changeToDetailWorkout(workout : String){
        val detailWorkout = DetailWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("workoutFromPlan", workout)
        detailWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, detailWorkout)
        fragmentTrans.addToBackStack(detailWorkout.tag)
        fragmentTrans.commit()
    }
}