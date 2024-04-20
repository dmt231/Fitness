package com.example.fitness.explore.workout.excercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fitness.model.Exercise
import com.example.fitness.repository.ExerciseRepository

class ExerciseViewModel : ViewModel() {
    private var listExerciseViewModel: MutableLiveData<ArrayList<Exercise>>? = null
    private var exerciseRepository: ExerciseRepository? = null

    fun getAllLiveDataExercise(): MutableLiveData<ArrayList<Exercise>>? {
        listExerciseViewModel = MutableLiveData()
        exerciseRepository = ExerciseRepository()
        loadAllDataFromRepository();
        return listExerciseViewModel;
    }

    private fun loadAllDataFromRepository() {
        exerciseRepository!!.getExerciseLiveData().observeForever {
            if (it.isNotEmpty() && it != null) {
                listExerciseViewModel?.value = it
            }
        }
        exerciseRepository?.getAllExercise()
    }

    fun getLiveDataExercise(bodyPart: String): MutableLiveData<ArrayList<Exercise>>? {
        listExerciseViewModel = MutableLiveData()
        exerciseRepository = ExerciseRepository()
        loadDataFromRepository(bodyPart);
        return listExerciseViewModel;
    }

    private fun loadDataFromRepository(bodyPart: String) {
        exerciseRepository!!.getExerciseLiveData().observeForever {
            if (it.isNotEmpty() && it != null) {
                listExerciseViewModel?.value = it
            }
        }
        exerciseRepository?.getExerciseByPart(bodyPart)
    }

}