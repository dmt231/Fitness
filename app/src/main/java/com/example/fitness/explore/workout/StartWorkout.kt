package com.example.fitness.explore.workout

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.adapter_recyclerView.adapter_excercise.AdapterExerciseInStartWorkout
import com.example.fitness.databinding.LayoutStartWorkoutBinding
import com.example.fitness.model.ExerciseInWorkout
import com.example.fitness.model.Workout

class StartWorkout : Fragment() {
    private lateinit var viewBinding: LayoutStartWorkoutBinding
    private var adapter: AdapterExerciseInStartWorkout? = null
    private var listExercise: ArrayList<ExerciseInWorkout> = ArrayList()
    private lateinit var workout: Workout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutStartWorkoutBinding.inflate(inflater, container, false)
        viewBinding.linearLayoutMain.setOnClickListener {

        }
        getDataWorkout()
        setUpData()
        setUpRecyclerView()
        return viewBinding.root
    }

    private fun getDataWorkout() {
        val bundle = arguments
        if (bundle != null) {
            workout = bundle["workout"] as Workout
        }
        handleListExercise()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleListExercise() {
        val listResult = workout.listExercise
        if (listResult != null) {
            for (exercise in listResult) {
                val setRep = exercise.getSetRep()
                Log.d("SetRep", setRep.toString())
                if (setRep != null) {
                    if (!setRep.contains("Minutes") && !setRep.contains("Second") && !setRep.contains("Hour")) {
                        val splitSetRep = setRep.split(" ")
                        val set = splitSetRep[0].toInt()
                        val rep = splitSetRep[3].toInt()
                        exercise.setRep(rep.toString())
                        for(i in 0 until set){
                            listExercise.add(exercise)
                        }
                    }else{
                        exercise.setRep(setRep)
                        listExercise.add(exercise)
                    }
                }
            }
            adapter?.notifyDataSetChanged()
        }
    }

    private fun setUpData() {
        viewBinding.nameWorkout.text = workout.name
    }

    private fun setUpRecyclerView() {
        viewBinding.recyclerViewExerciseInWorkout.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewExerciseInWorkout.layoutManager = layout
        adapter = AdapterExerciseInStartWorkout(listExercise)
        viewBinding.recyclerViewExerciseInWorkout.adapter = adapter
    }
}