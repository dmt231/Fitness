package com.example.fitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitness.databinding.ActivityMainBinding
import com.example.fitness.main.MainFragment

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
        bundle.putString("Tab", "Explore")
        mainFragment.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, mainFragment)
        fragmentTrans.commit()
    }

}