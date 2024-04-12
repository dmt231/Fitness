package com.example.fitness.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitness.R
import com.example.fitness.authentication.welcome.WelcomeFragment

class LoginSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sign_up)
        onChangedToWelcome()
    }
    private fun onChangedToWelcome(){
        val welcomeFragment = WelcomeFragment();
        val fragmentTrans = this.supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layoutLoginSignUp, welcomeFragment)
        fragmentTrans.addToBackStack(welcomeFragment.tag)
        fragmentTrans.commit()
    }
}