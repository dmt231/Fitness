package com.example.fitness.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fitness.create.model.PersonalPlan
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.model.ExerciseInWorkout
import com.example.fitness.model.Plan
import com.example.fitness.model.Workout
import com.example.fitness.model.WorkoutInPlan
import com.google.firebase.firestore.FirebaseFirestore

class PlanRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    private val planLiveData: MutableLiveData<ArrayList<Plan>> =
        MutableLiveData<ArrayList<Plan>>()

    fun getPlanLiveData(): MutableLiveData<ArrayList<Plan>> {
        return planLiveData
    }

    fun getPlanByDay(day: String) {
        fireStore.collection("Plan")
            .whereEqualTo("dayOfWeek", day)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val listPlan = ArrayList<Plan>()
                    for (plan in it.result) {
                        val id = plan.getString("IdPlan")!!
                        val name = plan.getString("Name")!!
                        val overview = plan.getString("Overview")!!
                        val repeat = plan.getString("Repeat")!!
                        val dayOfWeek = plan.getString("dayOfWeek")!!
                        val difficulty = plan.getString("difficulty")!!
                        val goals = plan.getString("goals")!!
                        val imgCover = plan.getString("imgCover")!!
                        val listWorkoutReceived: List<Map<String, Any>> =
                            plan.get("listWorkout") as List<Map<String, Any>>
                        val listWorkoutInPlan = ArrayList<WorkoutInPlan>()

                        for (data in listWorkoutReceived) {
                            val workout = data["workout"] as String
                            val orderDay = data["orderDay"] as String
                            val workoutInPlan = WorkoutInPlan(workout, orderDay)
                            listWorkoutInPlan.add(workoutInPlan)
                        }
                        val planModel = Plan(
                            id,
                            name,
                            overview,
                            repeat,
                            dayOfWeek,
                            difficulty,
                            goals,
                            imgCover,
                            listWorkoutInPlan
                        )
                        listPlan.add(planModel)
                    }
                    planLiveData.value = listPlan
                }
            }
    }
    fun getPlanByGoal(goal: String) {
        fireStore.collection("Plan")
            .whereEqualTo("goals", goal)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val listPlan = ArrayList<Plan>()
                    for (plan in it.result) {
                        val id = plan.getString("IdPlan")!!
                        val name = plan.getString("Name")!!
                        val overview = plan.getString("Overview")!!
                        val repeat = plan.getString("Repeat")!!
                        val dayOfWeek = plan.getString("dayOfWeek")!!
                        val difficulty = plan.getString("difficulty")!!
                        val goals = plan.getString("goals")!!
                        val imgCover = plan.getString("imgCover")!!
                        val listWorkoutReceived: List<Map<String, Any>> =
                            plan.get("listWorkout") as List<Map<String, Any>>
                        val listWorkoutInPlan = ArrayList<WorkoutInPlan>()

                        for (data in listWorkoutReceived) {
                            val workout = data["workout"] as String
                            val orderDay = data["orderDay"] as String
                            val workoutInPlan = WorkoutInPlan(workout, orderDay)
                            listWorkoutInPlan.add(workoutInPlan)
                        }
                        val planModel = Plan(
                            id,
                            name,
                            overview,
                            repeat,
                            dayOfWeek,
                            difficulty,
                            goals,
                            imgCover,
                            listWorkoutInPlan
                        )
                        listPlan.add(planModel)
                    }
                    planLiveData.value = listPlan
                }
            }
    }
    fun getPlanByUserId(userId : String, queryListPlan: QueryListPlan){
        fireStore.collection("Plan")
            .whereEqualTo("author", userId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val listPlan = ArrayList<PersonalPlan>()
                    if (!it.result.isEmpty) {
                        for(plan in it.result){
                            val id = plan.getString("IdPlan")!!
                            val name = plan.getString("Name")!!
                            val listWorkoutReceived: List<Map<String, Any>> =
                                plan.get("listWorkout") as List<Map<String, Any>>
                            val listWorkoutInPlan = ArrayList<PersonalWorkout>()

                            for (data in listWorkoutReceived) {
                                val nameWorkout = data["nameWorkout"] as String
                                val idWorkout = data["idWorkout"] as String
                                val listExercise: List<Map<String, Any>> =
                                    data["listExercise"] as List<Map<String, Any>>

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
                                    idWorkout,
                                    nameWorkout,
                                    listExerciseInWorkout
                                )
                                listWorkoutInPlan.add(workoutModel)
                            }
                            val planModel = PersonalPlan(id, name, listWorkoutInPlan)
                            listPlan.add(planModel)
                        }
                        queryListPlan.onFoundListPlanListener(listPlan)
                    }else{
                        queryListPlan.onNotFoundListener()
                    }
                }
            }
    }
    fun createPlanByUser(userId: String, plan : PersonalPlan, queryListPlan: QueryListPlan ){
        val collectionRef = fireStore.collection("Plan")
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["IdPlan"] = "Id ${plan.idPlan}"
        hashMap["Name"] = plan.namePlan
        hashMap["author"] = userId
        hashMap["listWorkout"] = plan.listWorkout
        collectionRef.document(plan.idPlan + " " +userId).set(hashMap).addOnCompleteListener {
            if (it.isSuccessful) {
                queryListPlan.onSuccessListener()
            } else {
                Log.d("Status", "Failed")
            }
        }
    }
    interface QueryListPlan{
        fun onSuccessListener()
        fun onNotFoundListener()
        fun onFoundListPlanListener(listPlan : ArrayList<PersonalPlan>)
    }
}