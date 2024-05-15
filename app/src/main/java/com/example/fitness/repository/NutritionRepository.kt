package com.example.fitness.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fitness.model.Exercise
import com.example.fitness.model.Nutrition
import com.google.firebase.firestore.FirebaseFirestore

class NutritionRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    private val nutritionLiveData: MutableLiveData<ArrayList<Nutrition>> =
        MutableLiveData<ArrayList<Nutrition>>()

    fun getNutritionLiveData(): MutableLiveData<ArrayList<Nutrition>> {
        return nutritionLiveData
    }
    fun getAllNutrition() {
        fireStore.collection("Nutrition")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    val listNutrition: ArrayList<Nutrition> = ArrayList()
                    for (nutrition in it.result) {
                       val name = nutrition.getString("Name")!!
                       val image = nutrition.getString("Image")!!
                       val kcal = nutrition.getString("Kcal")!!
                        val protein = nutrition.getString("Protein")!!
                        val cab = nutrition.getString("Cab")!!
                        val fat = nutrition.getString("Fat")!!
                        val nutritionModel = Nutrition(name,kcal,image,protein,cab,fat)
                        listNutrition.add(nutritionModel)
                    }
                    nutritionLiveData.value = listNutrition
                } else {
                    Log.d("Error", it.exception.toString())
                }
            }
    }
}