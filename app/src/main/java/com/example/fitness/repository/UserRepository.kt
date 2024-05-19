package com.example.fitness.repository

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fitness.model.History
import com.example.fitness.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    private val historyWorkoutLiveData: MutableLiveData<ArrayList<History>> =
        MutableLiveData<ArrayList<History>>()

    fun getHistoryLiveData(): MutableLiveData<ArrayList<History>> {
        return historyWorkoutLiveData
    }

    //Register User
    fun createAuthUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        activity: Activity,
        onCompleteListener: OnCompleteListener
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    createFireStoreUser(
                        firstName,
                        lastName,
                        email,
                        password,
                        auth.currentUser?.uid.toString(),
                        onCompleteListener
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        activity,
                        "Đăng ký thất bại, vui lòng kiểm tra lại email !",
                        Toast.LENGTH_SHORT,
                    ).show()
                    onCompleteListener.onFailedListener()
                }
            }
    }

    fun loginUser(
        email: String,
        password: String,
        activity: Activity,
        onCompleteListener: OnCompleteListener
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    onCompleteListener.onSuccessListener(auth.currentUser!!.uid)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        activity,
                        "Đăng nhập thất bại, vui lòng kiểm tra lại email và mật khẩu !",
                        Toast.LENGTH_SHORT,
                    ).show()
                    onCompleteListener.onFailedListener()
                }
            }
    }

    private fun createFireStoreUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        authUser: String,
        onCompleteListener: OnCompleteListener
    ) {
        val collectionRef = fireStore.collection("User")
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["firstName"] = firstName
        hashMap["lastName"] = lastName
        hashMap["email"] = email
        hashMap["password"] = password
        hashMap["userId"] = authUser
        collectionRef.document(authUser).set(hashMap).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Status Create : ", "Success")
                onCompleteListener.onSuccessListener(authUser)
            } else {
                Log.d("Status Create : ", "Failed")
            }
        }
    }

    interface OnCompleteListener {
        fun onSuccessListener(userId: String)
        fun onFailedListener()
    }

    fun updateDetailInfoForUser(
        gender: String,
        birthDay: String,
        metric: String,
        weight: String,
        height: String,
        bmi: String,
        userId: String,
        deadlift: String,
        squat: String,
        benchPress: String,
    ) {
        val documentRef = fireStore.collection("User").document(userId)
        documentRef.update(
            "gender", gender, "birthday", birthDay,
            "metric", metric, "weight", weight, "height", height, "bmi", bmi, "deadlift",deadlift, "squat", squat, "bench press" , benchPress
        )
            .addOnCompleteListener {
                Log.d("Status Update : ", "Success")
            }
    }
    fun updateHeightValueForUser(height: String, bmi: String, userId : String){
        val documentRef = fireStore.collection("User").document(userId)
        documentRef.update(
           "height", height, "bmi", bmi
        )
            .addOnCompleteListener {
                Log.d("Status Update : ", "Success")
            }
    }
    fun updateWeightValueForUser(weight: String, bmi: String, userId : String){
        val documentRef = fireStore.collection("User").document(userId)
        documentRef.update(
            "weight", weight, "bmi", bmi
        )
            .addOnCompleteListener {
                Log.d("Status Update : ", "Success")
            }
    }
    fun createHistoryForUser(userId : String?, idWorkout : String?, date : String?, percentage : String?, duration : String?){
        val collectionRef = fireStore.collection("History")
        val hashMap: MutableMap<String, Any> = HashMap()
        hashMap["idUser"] = userId!!
        hashMap["workout"] = idWorkout!!
        hashMap["date"] = date!!
        hashMap["percentage"] = percentage!!
        hashMap["duration"] = duration!!
        collectionRef.document().set(hashMap).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Status Create : ", "Success")
            } else {
                Log.d("Status Create : ", "Failed")
            }
        }
    }
    fun getHistoryForUser(userId : String?){
        val collectionRef = fireStore.collection("History")
        collectionRef.whereEqualTo("idUser", userId)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val listHistory: ArrayList<History> = ArrayList()
                    for(history in it.result){
                        val workoutResult = history.getString("workout")
                        val date = history.getString("date")
                        val duration = history.getString("duration")
                        val percentage = history.getString("percentage")
                        val historyId = history.reference.id
                        val historyModel = History(historyId,userId!!, workoutResult!!, date!!, percentage!!,duration!!)
                        listHistory.add(historyModel)
                    }
                    historyWorkoutLiveData.value = listHistory
                }else{
                    Log.d("Error", it.exception.toString())
                }
            }
    }
    fun deleteHistoryForUser(documentId : String){
        val collectionRef = fireStore.collection("History")
        collectionRef.document(documentId)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val document = it.result
                    if(document != null && document.exists()){
                        document.reference.delete()
                            .addOnSuccessListener {
                                Log.d("Remove state", "Success")
                            }
                            .addOnFailureListener{
                                Log.d("Remove state", "Failed")
                            }
                    }
                }
            }
    }
    fun updateRecordValue(userId: String, value: String, type : String){
        val documentRef = fireStore.collection("User").document(userId)
        when(type){
            "deadlift" ->{documentRef.update("deadlift", value)}
            "squat" ->{documentRef.update("squat", value)}
            "benchPress"->{documentRef.update("bench press", value)}
        }
    }
    fun getRecordForUser(userId: String, getRecord : GetRecord){
        val collectionRef = fireStore.collection("User")
        collectionRef.document(userId)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val deadLift = it.result.getString("deadlift")
                    val squat = it.result.getString("squat")
                    val benchPress = it.result.getString("bench press")
                    getRecord.onReturnValue(deadLift!!, squat!!, benchPress!!)
                }
            }
    }
    fun getInformationForUser(userId: String, onQuerySuccessUser : OnQuerySuccessUser){
        val collectionRef = fireStore.collection("User")
        collectionRef.document(userId)
            .get()
            .addOnCompleteListener {
                val firstName = it.result.getString("firstName")!!
                val lastName = it.result.getString("lastName")!!
                val email = it.result.getString("email")!!
                val gender = it.result.getString("gender")!!
                val birthDay = it.result.getString("birthday")!!
                val height = it.result.getString("height")!!
                val weight = it.result.getString("weight")!!
                val bmi = it.result.getString("bmi")!!
                val measure = it.result.getString("metric")!!
                val user = User(firstName, lastName, email, gender,birthDay, bmi,height,weight, measure)
                onQuerySuccessUser.onQueryInfoUSerSuccess(user)
            }
    }
    interface GetRecord{
        fun onReturnValue(deadlift : String, squat : String, benchPress : String)
    }
    interface OnQuerySuccessUser{
        fun onQueryInfoUSerSuccess(user : User)
    }
}