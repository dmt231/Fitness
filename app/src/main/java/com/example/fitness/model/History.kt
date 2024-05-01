package com.example.fitness.model

import android.health.connect.datatypes.units.Percentage

class History(idUser : String, Workout: String, date : String, percentage: String, duration : String) : java.io.Serializable {
    private var idUser : String? = null
    private var Workout : String? = null
    private var date : String? = null
    private var percentage : String? =null
    private var duration : String? = null

    init {
        this.idUser = idUser
        this.Workout = Workout
        this.date = date
        this.percentage = percentage
        this.duration = duration
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
        return Workout
    }
}