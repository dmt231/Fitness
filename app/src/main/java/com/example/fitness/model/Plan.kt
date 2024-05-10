package com.example.fitness.model

class Plan(
    val idPlan: String,
    val namePlan: String,
    val overview: String?,
    val repeat: String?,
    val dayOfWeek: String?,
    val difficulty: String?,
    val goal: String?,
    val imgCover: String?,
    val listWorkout: ArrayList<WorkoutInPlan>
) : java.io.Serializable {
}