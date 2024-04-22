package com.example.fitness.explore.workout

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.adapter_recyclerView.adapter_excercise.AdapterForListExercise
import com.example.fitness.adapter_recyclerView.adapter_workout.AdapterForListWorkout
import com.example.fitness.databinding.LayoutListOfWorkoutBinding
import com.example.fitness.model.Exercise
import com.example.fitness.model.Workout

class ListWorkout : Fragment() {
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var viewBinding : LayoutListOfWorkoutBinding
    private lateinit var adapterListWorkout: AdapterForListWorkout
    private var listWorkout: ArrayList<Workout> = ArrayList()
    private var title : String = ""
    private var type : String = ""
    private var query : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
        viewBinding = LayoutListOfWorkoutBinding.inflate(inflater, container, false)
        getDataFromMenu()
        setUpTitleData()
        getListOfWorkout()
        setUpRecyclerView()
        return viewBinding.root
    }
    private fun getDataFromMenu(){
        val bundle = arguments
        if(bundle != null){
            type = bundle["Type"] as String
            title = bundle["Title"] as String
            query = bundle["Query"] as String
        }
    }
    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun getListOfWorkout(){
        workoutViewModel.getLiveDataWorkout(type, query)?.observe(viewLifecycleOwner){
            for(workout in it) {
                listWorkout.add(workout)
            }
            viewBinding.progressBar.visibility = View.GONE
            viewBinding.numberWorkout.text = it.size.toString() + " Buổi Tập"
            adapterListWorkout.notifyDataSetChanged()
        }
    }
    private fun setUpTitleData(){
        viewBinding.titleType.text = title
    }
    private fun setUpRecyclerView(){
        viewBinding.recyclerViewWorkout.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewWorkout.layoutManager = layout
        adapterListWorkout = AdapterForListWorkout(listWorkout, object : AdapterForListWorkout.OnClickListener{
            override fun onClickListener(workout: Workout) {
                TODO("Not yet implemented")
            }
        })
        viewBinding.recyclerViewWorkout.adapter = adapterListWorkout
    }
}