package com.example.fitness.create.create_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_workout.AdapterListPersonalWorkout
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.databinding.LayoutPersonalWorkoutBinding
import com.example.fitness.model.Workout
import com.example.fitness.repository.WorkoutRepository
import com.example.fitness.storage.Preferences

class PersonalWorkoutFragment : Fragment() {
    private lateinit var viewBinding : LayoutPersonalWorkoutBinding
    private lateinit var workoutRepository: WorkoutRepository
    private lateinit var preferences: Preferences
    private lateinit var listWorkoutForResult : ArrayList<PersonalWorkout>
    private lateinit var adapter : AdapterListPersonalWorkout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutPersonalWorkoutBinding.inflate(inflater, container, false)
        workoutRepository = WorkoutRepository()
        preferences = Preferences(requireContext())
        getData(preferences.getUserIdValues().toString())
        listWorkoutForResult = ArrayList()
        viewBinding.btnCreateWorkout.setOnClickListener {
            changeToCreateNameWorkout()
        }
        viewBinding.btnCreate.setOnClickListener {
            changeToCreateNameWorkout()
        }
        setUpRecyclerView()
        return viewBinding.root
    }
    private fun getData(userId : String){
        workoutRepository.getWorkoutByUser(userId, object : WorkoutRepository.QueryListWorkout{

            override fun onNotFoundListener() {
                viewBinding.recyclerViewMyWorkout.visibility = View.GONE
                viewBinding.linearLayout.visibility = View.VISIBLE
                viewBinding.btnCreate.visibility = View.INVISIBLE
            }

            override fun onFoundListWorkoutListener(listWorkout: ArrayList<PersonalWorkout>) {
                viewBinding.recyclerViewMyWorkout.visibility = View.VISIBLE
                viewBinding.linearLayout.visibility = View.GONE
                viewBinding.btnCreate.visibility = View.VISIBLE
                for(workout in listWorkout){
                    val position = listWorkoutForResult.size
                    listWorkoutForResult.add(workout)
                    adapter.notifyItemInserted(position)
                }
            }
        })
    }
    private fun changeToCreateNameWorkout(){
        val createNameWorkout = CreateNameWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("WorkoutFor", "Workout")
        createNameWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, createNameWorkout)
        fragmentTrans.addToBackStack(createNameWorkout.tag)
        fragmentTrans.commit()
    }
    private fun setUpRecyclerView(){
        viewBinding.recyclerViewMyWorkout.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewMyWorkout.layoutManager = layout
        adapter = AdapterListPersonalWorkout(listWorkoutForResult, object : AdapterListPersonalWorkout.OnWorkoutClickListener{
            override fun onWorkoutClickListener(workout: PersonalWorkout) {
                changeToDetailWorkout(workout)
            }
        })
        viewBinding.recyclerViewMyWorkout.adapter = adapter
    }
    private fun changeToDetailWorkout(workout : PersonalWorkout){
        val detailPersonalWorkout = DetailPersonalWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("workoutPersonal", workout)
        detailPersonalWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, detailPersonalWorkout)
        fragmentTrans.addToBackStack(null)
        fragmentTrans.commit()
    }
}