package com.example.fitness.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fitness.create.model.PersonalWorkout
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
    fun createWorkoutByUser(userId: String, workout : PersonalWorkout, addSuccessListener: AddSuccessListener){
        val collectionRef = fireStore.collection("Workout")
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["IdWorkout"] = "Id${workout.idWorkout}"
        hashMap["Name"] = workout.nameWorkout
        hashMap["ListOfExcercice"] = workout.listExercise
        hashMap["author"] = userId
        collectionRef.document(workout.idWorkout + " " +userId).set(hashMap).addOnCompleteListener {
            if (it.isSuccessful) {
                addSuccessListener.addSuccessListener()
            } else {
                Log.d("Status", "Failed")
            }
        }
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
    fun getWorkoutByDocumentId(workoutDocument : String, onCompleteListener: OnCompleteListener){
        fireStore.collection("Workout")
            .document(workoutDocument)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val workout = it.result
                    if(workout.exists()){
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
                        onCompleteListener.onCompleteListener(workoutModel)
                    }
                }
            }
    }
    fun getWorkoutByUser(userId : String, queryListWorkout: QueryListWorkout ){
        fireStore.collection("Workout")
            .whereEqualTo("author", userId)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val listWorkout = ArrayList<PersonalWorkout>()
                    if(!it.result.isEmpty){
                        for(workout in it.result){
                            val name = workout.getString("Name")
                            val id = workout.getString("IdWorkout")
                            val listExercise: List<Map<String, Any>> =
                                workout.get("ListOfExcercice") as List<Map<String, Any>>

                            val listExerciseInWorkout = ArrayList<ExerciseInWorkout>()

                                for (map in listExercise) {

                                    val idExercise = map["idExercise"] as String
                                    val setRep = map["setAndRep"] as String
                                    val image = map["image"] as String
                                    val nameExercise = map["name"] as String
                                    val exerciseInWorkout = ExerciseInWorkout(idExercise, setRep)
                                    exerciseInWorkout.setImage(image)
                                    exerciseInWorkout.setName(nameExercise)
                                    listExerciseInWorkout.add(exerciseInWorkout)
                                }
                            val workoutModel = PersonalWorkout(
                                id!!,
                                name!!,
                                listExerciseInWorkout
                            )
                            listWorkout.add(workoutModel)
                        }
                        queryListWorkout.onFoundListWorkoutListener(listWorkout)
                    }else{
                        queryListWorkout.onNotFoundListener()
                    }
                }
            }
    }

    interface OnCompleteListener {
        fun onCompleteListener(workout: Workout)
    }
    interface QueryListWorkout{
        fun onNotFoundListener()
        fun onFoundListWorkoutListener(listWorkout : ArrayList<PersonalWorkout>)
    }
    interface AddSuccessListener{
        fun addSuccessListener()
    }
}