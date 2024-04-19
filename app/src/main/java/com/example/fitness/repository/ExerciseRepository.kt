package com.example.fitness.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fitness.model.Exercise
import com.google.firebase.firestore.FirebaseFirestore


class ExerciseRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    private val exerciseLiveData: MutableLiveData<ArrayList<Exercise>> =
        MutableLiveData<ArrayList<Exercise>>()

    fun getExerciseLiveData(): MutableLiveData<ArrayList<Exercise>> {
        return exerciseLiveData
    }

    //Get All exercise
    fun getAllExercise() {
        fireStore.collection("Excercice")
            .orderBy("Id")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    val listExercise: ArrayList<Exercise> = ArrayList()
                    for (exercise in it.result) {
                        val id = exercise.getString("Id")
                        val description = exercise.getString("Description")
                        val equipment = exercise.getString("Equipement")
                        val instruction = exercise.getString("Instruction")
                        val name = exercise.getString("Name")
                        val part = exercise.getString("Part")
                        val type = exercise.getString("Type")
                        val imgCovered = exercise.getString("imgCovered")
                        val srcVideo = exercise.getString("srcVideo")
                        val exerciseModel = Exercise(
                            id,
                            description,
                            equipment,
                            instruction,
                            name,
                            part,
                            type,
                            imgCovered,
                            srcVideo
                        )
                        listExercise.add(exerciseModel)
                    }
                    exerciseLiveData.value = listExercise
                } else {
                    Log.d("Error", it.exception.toString())
                }
            }
    }
}