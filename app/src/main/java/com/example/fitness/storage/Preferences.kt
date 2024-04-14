package com.example.fitness.storage

import android.content.Context
import android.content.SharedPreferences

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
}