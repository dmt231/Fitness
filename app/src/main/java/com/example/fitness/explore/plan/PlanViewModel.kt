package com.example.fitness.explore.plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness.model.Plan
import com.example.fitness.repository.PlanRepository

class PlanViewModel : ViewModel() {
    private var listPlanModel: MutableLiveData<ArrayList<Plan>>? = null
    private var planRepository: PlanRepository? = null

    fun getLiveDataPlan(type : String, query : String): MutableLiveData<ArrayList<Plan>>? {
        listPlanModel = MutableLiveData()
        planRepository = PlanRepository()
        loadDataFromRepository(type, query);
        return listPlanModel;
    }

    private fun loadDataFromRepository(type : String, query : String) {
        planRepository!!.getPlanLiveData().observeForever {
            if (it.isNotEmpty() && it != null) {
                listPlanModel?.value = it
            }
        }
        when(type){
            "Day" -> {
                planRepository!!.getPlanByDay(query)
            }
            "Goal" -> {
                planRepository!!.getPlanByGoal(query)
            }
        }
    }
}