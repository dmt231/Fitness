package com.example.fitness.create.create_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_excercise.AdapterListExerciseInWorkout
import com.example.fitness.databinding.LayoutDetailPersonalWorkoutBinding
import com.example.fitness.explore.workout.StartWorkout
import com.example.fitness.model.Workout


class DetailPersonalWorkout : Fragment() {
    private lateinit var viewBinding : LayoutDetailPersonalWorkoutBinding
    private var workout: Workout? = null
    private lateinit var adapter: AdapterListExerciseInWorkout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutDetailPersonalWorkoutBinding.inflate(inflater, container, false)
        getDataWorkout()
        viewBinding.btnStartWorkout.setOnClickListener {
            onChangeToStartWorkout(workout!!)
        }
        viewBinding.linearLayoutMain.setOnClickListener {
            //Do Nothing
        }
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return viewBinding.root
    }

    private fun bindingData() {
        viewBinding.nameWorkout.text = workout?.name
    }

    private fun getDataWorkout(){
        val bundle = arguments
        if(bundle != null){
            this.workout = bundle["workoutPersonal"] as Workout
            bindingData()
            setUpRecyclerView()
        }
    }
    private fun setUpRecyclerView(){
        viewBinding.recyclerViewListExercise.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewListExercise.layoutManager = layout
        adapter = AdapterListExerciseInWorkout(
            workout!!.listExercise!!,
            object : AdapterListExerciseInWorkout.OnClickListenerExerciseInWorkout {
                override fun onClickListener(idExercise: String) {

                }

            })
        viewBinding.recyclerViewListExercise.adapter = adapter
    }
    private fun onChangeToStartWorkout(workout: Workout) {
        val startWorkout = StartWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("workout", workout)
        startWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, startWorkout)
        fragmentTrans.addToBackStack(startWorkout.tag)
        fragmentTrans.commit()
    }
}