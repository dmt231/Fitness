package com.example.fitness.model

class ExerciseInWorkout(id: String?, setRep : String?) {
    private var idExcercice: String? = null
    private var setRep : String? = null
    init {
        this.idExcercice = id
        this.setRep = setRep
    }
    fun getIdExercise() : String?{
        return idExcercice;
    }
    fun getSetRep() : String?{
        return setRep;
    }
}