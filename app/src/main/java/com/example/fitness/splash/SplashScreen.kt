package com.example.fitness.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.fitness.MainActivity

import com.example.fitness.R
import com.example.fitness.authentication.LoginSignUpActivity
import com.google.firebase.auth.FirebaseAuth


class SplashScreen : AppCompatActivity() {
    private val delayTime : Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            handleNextActivity()
        }, delayTime)
    }
    private fun handleNextActivity(){
        val user = FirebaseAuth.getInstance().currentUser
        if(user == null){
            val intent = Intent(this, LoginSignUpActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}