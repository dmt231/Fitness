package com.example.fitness.model

class ExerciseInWorkout(id: String?, setRep : String?) {
    private var idExcercice: String? = null
    private var setRep : String? = null
    private var image : String? = null
    private var isChecked : Boolean = false
    private var name : String? = null
    private var rep : String? = null

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
    fun setImage(image : String){
        this.image = image
    }
    fun setChecked(bool : Boolean){
        this.isChecked = bool
    }
    fun setName(name : String){
        this.name = name
    }
    fun getImage() : String?{
        return image;
    }
    fun getChecked() : Boolean{
        return isChecked;
    }
    fun getName() : String?{
        return name;
    }
    fun setRep(rep : String){
        this.rep = rep
    }
    fun getRep() : String?{
        return rep;
    }
}