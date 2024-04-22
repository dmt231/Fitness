package com.example.fitness.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fitness.model.Exercise
import com.example.fitness.model.ExerciseInWorkout
import com.example.fitness.model.Workout
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("UNCHECKED_CAST")
class WorkoutRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    private val workoutLiveData: MutableLiveData<ArrayList<Workout>> =
        MutableLiveData<ArrayList<Workout>>()

    fun getWorkoutLiveData(): MutableLiveData<ArrayList<Workout>> {
        return workoutLiveData
    }

    fun getWorkoutByType(type: String) {
        fireStore.collection("Workout")
            .whereEqualTo("Type", type)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    val listWorkout = ArrayList<Workout>()
                    for (workout in it.result) {
                        val id = workout.getString("IdWorkout")
                        val difficulty = workout.getString("Difficulty")
                        val equipment = workout.getString("Equipement")
                        val name = workout.getString("Name")
                        val overview = workout.getString("Overview")
                        val typeWorkout = workout.getString("Type")
                        val bodyPart = workout.getString("BodyPart")
                        val repeat = workout.get("Repeat", Int::class.java)
                        val time = workout.get("Time", Int::class.java)
                        val imgCovered = workout.getString("imgCovered")
                        val listExercise: List<Map<String, Any>> =
                            workout.get("ListOfExcercice") as List<Map<String, Any>>

                        val listExerciseInWorkout = ArrayList<ExerciseInWorkout>()
                        for (map in listExercise) {
                            val idExercise = map["idExcercice"] as String
                            val setRep = map["setRep"] as String
                            val exerciseInWorkout = ExerciseInWorkout(idExercise, setRep)
                            listExerciseInWorkout.add(exerciseInWorkout)
                        }
                        val workoutModel = Workout(
                            id,
                            difficulty,
                            equipment,
                            overview,
                            name,
                            bodyPart,
                            typeWorkout,
                            imgCovered,
                            time,
                            repeat,
                            listExerciseInWorkout
                        )
                        listWorkout.add(workoutModel)
                    }
                    workoutLiveData.value = listWorkout
                } else {
                    Log.d("Error", it.exception.toString())
                }
            }
    }

    fun getWorkoutByTimeExact(time: String) {
        fireStore.collection("Workout")
            .whereEqualTo("Time", time.toInt())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    val listWorkout = ArrayList<Workout>()
                    for (workout in it.result) {
                        val timeWorkout = workout.get("Time", Int::class.java)
                        val id = workout.getString("IdWorkout")
                        val difficulty = workout.getString("Difficulty")
                        val equipment = workout.getString("Equipement")
                        val name = workout.getString("Name")
                        val overview = workout.getString("Overview")
                        val typeWorkout = workout.getString("Type")
                        val bodyPart = workout.getString("BodyPart")
                        val repeat = workout.get("Repeat", Int::class.java)
                        val imgCovered = workout.getString("imgCovered")
                        val listExercise: List<Map<String, Any>> =
                            workout.get("ListOfExcercice") as List<Map<String, Any>>

                        val listExerciseInWorkout = ArrayList<ExerciseInWorkout>()
                        for (map in listExercise) {
                            val idExercise = map["idExcercice"] as String
                            val setRep = map["setRep"] as String
                            val exerciseInWorkout = ExerciseInWorkout(idExercise, setRep)
                            listExerciseInWorkout.add(exerciseInWorkout)
                        }


                        val workoutModel = Workout(
                            id,
                            difficulty,
                            equipment,
                            overview,
                            name,
                            bodyPart,
                            typeWorkout,
                            imgCovered,
                            timeWorkout,
                            repeat,
                            listExerciseInWorkout
                        )
                        listWorkout.add(workoutModel)
                    }
                    workoutLiveData.value = listWorkout
                } else {
                    Log.d("Error", it.exception.toString())
                }
            }
    }

    fun getWorkoutByTimeEstimated(time: String) {
        val timePart = time.split(" ")
        fireStore.collection("Workout")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    val listWorkout = ArrayList<Workout>()
                    for (workout in it.result) {
                        val timeWorkout = workout.get("Time", Int::class.java)
                        if (time.contains("<")) {
                            if (timeWorkout!! < timePart[1].toInt()) {
                                val id = workout.getString("IdWorkout")
                                val difficulty = workout.getString("Difficulty")
                                val equipment = workout.getString("Equipement")
                                val name = workout.getString("Name")
                                val overview = workout.getString("Overview")
                                val typeWorkout = workout.getString("Type")
                                val bodyPart = workout.getString("BodyPart")
                                val repeat = workout.get("Repeat", Int::class.java)
                                val imgCovered = workout.getString("imgCovered")
                                val listExercise: List<Map<String, Any>> =
                                    workout.get("ListOfExcercice") as List<Map<String, Any>>

                                val listExerciseInWorkout = ArrayList<ExerciseInWorkout>()
                                for (map in listExercise) {
                                    val idExercise = map["idExcercice"] as String
                                    val setRep = map["setRep"] as String
                                    val exerciseInWorkout = ExerciseInWorkout(idExercise, setRep)
                                    listExerciseInWorkout.add(exerciseInWorkout)
                                }


                                val workoutModel = Workout(
                                    id,
                                    difficulty,
                                    equipment,
                                    overview,
                                    name,
                                    bodyPart,
                                    typeWorkout,
                                    imgCovered,
                                    timeWorkout,
                                    repeat,
                                    listExerciseInWorkout
                                )
                                listWorkout.add(workoutModel)
                            }
                        }
                    }
                    workoutLiveData.value = listWorkout
                } else {
                    Log.d("Error", it.exception.toString())
                }
            }
    }
    fun getWorkoutByEquipment(equipment : String){
        fireStore.collection("Workout")
            .orderBy("IdWorkout")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    val listWorkout = ArrayList<Workout>()
                    for (workout in it.result) {
                        val equipmentWorkout = workout.getString("Equipement")
                        if (equipmentWorkout != null) {
                            if(equipmentWorkout.contains(equipment))
                            {
                                val id = workout.getString("IdWorkout")
                                val difficulty = workout.getString("Difficulty")
                                val name = workout.getString("Name")
                                val overview = workout.getString("Overview")
                                val typeWorkout = workout.getString("Type")
                                val bodyPart = workout.getString("BodyPart")
                                val repeat = workout.get("Repeat", Int::class.java)
                                val time = workout.get("Time", Int::class.java)
                                val imgCovered = workout.getString("imgCovered")
                                val listExercise: List<Map<String, Any>> = workout.get("ListOfExcercice") as List<Map<String, Any>>
                                val listExerciseInWorkout = ArrayList<ExerciseInWorkout>()
                                for (map in listExercise) {
                                    val idExercise = map["idExcercice"] as String
                                    val setRep = map["setRep"] as String
                                    val exerciseInWorkout = ExerciseInWorkout(idExercise, setRep)
                                    listExerciseInWorkout.add(exerciseInWorkout)
                                }
                                val workoutModel = Workout(
                                    id,
                                    difficulty,
                                    equipmentWorkout,
                                    overview,
                                    name,
                                    bodyPart,
                                    typeWorkout,
                                    imgCovered,
                                    time,
                                    repeat,
                                    listExerciseInWorkout
                                )
                                listWorkout.add(workoutModel)
                            }
                        }
                    }
                    workoutLiveData.value = listWorkout
                } else {
                    Log.d("Error", it.exception.toString())
                }
            }
    }
    fun getWorkoutByBodyPart(bodyPart : String){
        fireStore.collection("Workout")
            .orderBy("IdWorkout")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    val listWorkout = ArrayList<Workout>()
                    for (workout in it.result) {
                        val bodyPartWorkout = workout.getString("BodyPart")
                        if (bodyPartWorkout != null) {
                            if(bodyPartWorkout.contains(bodyPart))
                            {
                                val id = workout.getString("IdWorkout")
                                val difficulty = workout.getString("Difficulty")
                                val name = workout.getString("Name")
                                val overview = workout.getString("Overview")
                                val equipment = workout.getString("Equipement")
                                val typeWorkout = workout.getString("Type")
                                val repeat = workout.get("Repeat", Int::class.java)
                                val time = workout.get("Time", Int::class.java)
                                val imgCovered = workout.getString("imgCovered")
                                val listExercise: List<Map<String, Any>> = workout.get("ListOfExcercice") as List<Map<String, Any>>
                                val listExerciseInWorkout = ArrayList<ExerciseInWorkout>()
                                for (map in listExercise) {
                                    val idExercise = map["idExcercice"] as String
                                    val setRep = map["setRep"] as String
                                    val exerciseInWorkout = ExerciseInWorkout(idExercise, setRep)
                                    listExerciseInWorkout.add(exerciseInWorkout)
                                }
                                val workoutModel = Workout(
                                    id,
                                    difficulty,
                                    equipment,
                                    overview,
                                    name,
                                    bodyPartWorkout,
                                    typeWorkout,
                                    imgCovered,
                                    time,
                                    repeat,
                                    listExerciseInWorkout
                                )
                                listWorkout.add(workoutModel)
                            }
                        }
                    }
                    workoutLiveData.value = listWorkout
                } else {
                    Log.d("Error", it.exception.toString())
                }
            }
    }
}