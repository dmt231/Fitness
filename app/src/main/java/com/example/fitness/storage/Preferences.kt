package com.example.fitness.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.fitness.model.WorkoutInPlan
import kotlin.math.min

class Preferences(private val context: Context) {
    private val myPreferences : String = "MyPreferences"
    fun putUserIdValues(userId : String){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("userId", userId)
        editor.apply()
    }
    fun getUserIdValues() : String? {
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString("userId", null)
    }
    fun putUserGenderValues(gender : String){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("Gender", gender)
        editor.apply()
    }
    fun getGenderValues() : String? {
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString("Gender", null)
    }
    fun putUserMetricValues(metric : String){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("Metric", metric)
        editor.apply()
    }
    fun getMetricValues() : String? {
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString("Metric", null)
    }
    fun putBirthdayValues(birthDay : String){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("Birthday", birthDay)
        editor.apply()
    }
    fun getBirthdayValues() : String? {
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString("Birthday", null)
    }
    fun putWeightValues(weight : String){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("Weight", weight)
        editor.apply()
    }
    fun getWeightValues() : String? {
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString("Weight", null)
    }
    fun putHeightValues(height : String){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("Height", height)
        editor.apply()
    }
    fun getHeightValues() : String? {
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString("Height", null)
    }
    fun putBMIValues(bmi : String){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("Bmi", bmi)
        editor.apply()
    }
    fun getBMIValues() : String? {
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString("Bmi", null)
    }
    fun deleteAllInformation(){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }
    fun putPermissionNotification(value : Boolean){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putBoolean("Allow_Notification", value)
        editor.apply()
    }
    fun getPermissionNotification() : Boolean{
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("Allow_Notification", false)
    }
    fun putTimeNotification(hour : String, minute : String){
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("Hour", hour)
        editor.putString("Minute", minute)
        editor.apply()
    }
    fun getTimeNotification() : String{
        val sharedPreferences : SharedPreferences? = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        var result = ""
        val hour = sharedPreferences!!.getString("Hour", null)
        val minute = sharedPreferences.getString("Minute", null)
        if(hour != null && minute != null){
            result = "$hour : $minute"
        }
        return result
    }
}