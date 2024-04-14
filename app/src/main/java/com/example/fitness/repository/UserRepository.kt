package com.example.fitness.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    //Register User
     fun createAuthUser(email : String, password : String, firstName : String, lastName : String , activity : Activity, onSuccessListener: OnSuccessListener){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    createFireStoreUser(firstName, lastName, email, password, auth.currentUser?.uid.toString(), onSuccessListener)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        activity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
     private fun createFireStoreUser(firstName : String, lastName : String, email : String, password : String, authUser : String, onSuccessListener: OnSuccessListener ){
        val collectionRef = fireStore.collection("User")
        val hashMap : MutableMap<String, Any> = HashMap()
        hashMap["firstName"] = firstName
        hashMap["lastName"] = lastName
        hashMap["email"] = email
        hashMap["password"] = password
        hashMap["userId"] = authUser
        collectionRef.document(authUser).set(hashMap).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("Status Create : ", "Success")
                onSuccessListener.onSuccessListener(authUser)
            }else{
                Log.d("Status Create : ", "Failed")
            }
        }
    }
    interface OnSuccessListener{
        fun onSuccessListener(userId : String)
    }
}