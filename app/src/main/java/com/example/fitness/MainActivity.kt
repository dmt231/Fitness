package com.example.fitness

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness.databinding.ActivityMainBinding
import com.example.fitness.main.MainFragment
import com.example.fitness.setting.broadcast.ReminderBroadcastReceiver


class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        replaceMainFragment()
    }
    private fun replaceMainFragment(){
        val mainFragment = MainFragment()
        val fragmentTrans = this.supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Menu", "Explore")
        mainFragment.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, mainFragment)
        fragmentTrans.commit()
    }

}