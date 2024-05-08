package com.example.fitness.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fitness.model.Plan
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
                        Log.d("Name : ", name)
                        listPlan.add(planModel)
                    }
                    planLiveData.value = listPlan
                }
            }
    }
    fun updateAuthorPlan(){
        fireStore.collection("Plan")
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    for(document in task.result){
                        document.reference.update("author", "fitness author").addOnCompleteListener {
                            if(it.isSuccessful){
                                Log.d("Status Update : ", "Success")
                            }
                        }
                    }
                }
            }
    }
}