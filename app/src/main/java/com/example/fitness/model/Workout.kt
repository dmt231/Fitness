package com.example.fitness.model

class Workout(
    id: String?,
    difficulty: String?,
    equipment: String?,
    overview: String?,
    name: String?,
    bodyPart: String?,
    type: String?,
    imgCovered: String?,
    time: Int?, repeat: Int?, listExercise: ArrayList<ExerciseInWorkout>) : java.io.Serializable{
     var id: String? = null
     var difficulty: String? = null
     var equipment: String? = null
     var overview: String? = null
     var name: String? = null
     var bodyPart: String? = null
     var type: String? = null
     var imgCovered: String? = null
     var time: Int? = null
     var repeat: Int? = null
     var listExercise: ArrayList<ExerciseInWorkout>? = null
    init {
        this.id = id
        this.equipment = equipment
        this.difficulty = difficulty
        this.overview = overview
        this.name = name
        this.bodyPart = bodyPart
        this.type = type
        this.imgCovered = imgCovered
        this.time = time
        this.repeat =repeat
        this.listExercise = listExercise
    }
}
