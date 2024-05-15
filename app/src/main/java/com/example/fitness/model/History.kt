package com.example.fitness.model

class History(
    historyId: String?,
    idUser: String, Workout: String, date: String, percentage: String, duration: String) : java.io.Serializable {
    private var idUser : String? = null
    private var workout : String? = null
    private var date : String? = null
    private var percentage : String? =null
    private var duration : String? = null
    private var historyId : String? = null
    init {
        this.idUser = idUser
        this.workout = Workout
        this.date = date
        this.percentage = percentage
        this.duration = duration
        this.historyId = historyId
    }
     fun getDate() : String?{
        return date
    }
     fun getPercentage() : String?{
        return percentage
    }
     fun getDuration() : String?{
        return duration
    }
     fun getName() : String?{
        return workout
    }
    fun getId() : String? {
        return historyId
    }
}