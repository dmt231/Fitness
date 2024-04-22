package com.example.fitness.explore.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fitness.model.Exercise
import com.example.fitness.model.Workout
import com.example.fitness.repository.ExerciseRepository
import com.example.fitness.repository.WorkoutRepository

class WorkoutViewModel : ViewModel() {
    private var listWorkoutViewModel: MutableLiveData<ArrayList<Workout>>? = null
    private var workoutRepository: WorkoutRepository? = null

    fun getLiveDataWorkout(type : String, query : String): MutableLiveData<ArrayList<Workout>>? {
        listWorkoutViewModel = MutableLiveData()
        workoutRepository = WorkoutRepository()
        loadDataFromRepository(type, query);
        return listWorkoutViewModel;
    }

    private fun loadDataFromRepository(type : String, query : String) {
        workoutRepository!!.getWorkoutLiveData().observeForever {
            if (it.isNotEmpty() && it != null) {
                listWorkoutViewModel?.value = it
            }
        }
        when(type){
            "Type" -> {
                workoutRepository!!.getWorkoutByType(query)
            }
            "Time" -> {
                if(query.contains("<")){
                    workoutRepository!!.getWorkoutByTimeEstimated(query)
                }
                else{
                    workoutRepository!!.getWorkoutByTimeExact(query)
                }
            }
            "Equipment" ->{
                workoutRepository!!.getWorkoutByEquipment(query)
            }
            "Body Part" -> {
                workoutRepository!!.getWorkoutByBodyPart(query)
            }
        }
    }

}