package com.example.fitness.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.create.create_workout.MyWorkoutFragment
import com.example.fitness.databinding.LayoutCreateFragmentBinding
import com.google.android.material.tabs.TabLayout

class CreateFragment : Fragment() {
    private lateinit var viewBinding : LayoutCreateFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutCreateFragmentBinding.inflate(inflater, container, false)
        changedToCreateWorkoutScreen()
        setUpTabLayout()
        return viewBinding.root
    }
    private fun setUpTabLayout(){
        viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setText("Buổi Tập"))
        viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setText("Lịch Tập"))
        viewBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        viewBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position
                if(position == 0){
                    changedToCreateWorkoutScreen()
                }
                else{
                    changedToCreatePlanScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun changedToCreatePlanScreen() {

    }

    private fun changedToCreateWorkoutScreen() {
        val myWorkoutFragment = MyWorkoutFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.contentLayout, myWorkoutFragment)
        fragmentTrans.commit()
    }
}