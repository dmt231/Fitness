package com.example.fitness.setting.nutrition

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness.model.Nutrition
import com.example.fitness.repository.NutritionRepository

class NutritionViewModel : ViewModel() {
    private var listNutritionModel: MutableLiveData<ArrayList<Nutrition>>? = null
    private var nutritionRepository: NutritionRepository? = null
    fun getLiveDataPlan(): MutableLiveData<ArrayList<Nutrition>>? {
        listNutritionModel = MutableLiveData()
        nutritionRepository = NutritionRepository()
        loadDataFromRepository();
        return listNutritionModel;
    }

    private fun loadDataFromRepository() {
        nutritionRepository?.getNutritionLiveData()?.observeForever {
            if(it!=null){
                listNutritionModel?.value = it
            }
        }
        nutritionRepository?.getAllNutrition()
    }
}