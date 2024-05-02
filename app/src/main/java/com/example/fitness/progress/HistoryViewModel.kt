package com.example.fitness.progress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness.model.History
import com.example.fitness.repository.UserRepository

class HistoryViewModel : ViewModel() {
    private var listHistoryViewModel: MutableLiveData<ArrayList<History>>? = null
    private var userRepository: UserRepository? = null

    fun getAllLiveDataExercise(userId : String?): MutableLiveData<ArrayList<History>>? {
        listHistoryViewModel = MutableLiveData()
        userRepository = UserRepository()
        loadDataHistoryRepository(userId);
        return listHistoryViewModel;
    }

    private fun loadDataHistoryRepository(userId: String?) {
        userRepository!!.getHistoryLiveData().observeForever {
            if (it.isNotEmpty() && it != null) {
                listHistoryViewModel?.value = it
            }
        }
        userRepository?.getHistoryForUser(userId)
    }
}