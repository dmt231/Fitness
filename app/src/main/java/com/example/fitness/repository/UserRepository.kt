package com.example.fitness.repository

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

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
        userId: String
    ) {
        val documentRef = fireStore.collection("User").document(userId)
        documentRef.update(
            "gender", gender, "birthday", birthDay,
            "metric", metric, "weight", weight, "height", height, "bmi", bmi
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
}