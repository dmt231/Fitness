package com.example.fitness.explore.workout.excercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.fitness.model.Exercise
import com.example.fitness.repository.ExerciseRepository

class ExerciseViewModel : ViewModel() {
    private var listExerciseViewModel :MutableLiveData<ArrayList<Exercise>>? = null
    private var exerciseRepository : ExerciseRepository? =null

    fun getLiveDataExercise() : MutableLiveData<ArrayList<Exercise>>?{
        listExerciseViewModel = MutableLiveData()
        exerciseRepository = ExerciseRepository()
        loadDataFromRepository();
        return listExerciseViewModel;
    }

    private fun loadDataFromRepository() {
        exerciseRepository!!.getExerciseLiveData().observeForever {
            if(it.isNotEmpty() && it != null){
                listExerciseViewModel?.value = it
            }
        }
        exerciseRepository?.getAllExercise()
    }
}