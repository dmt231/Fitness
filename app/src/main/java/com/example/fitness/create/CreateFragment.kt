package com.example.fitness.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.create.create_plan.PersonalPlanFragment
import com.example.fitness.create.create_workout.PersonalWorkoutFragment
import com.example.fitness.databinding.LayoutCreateFragmentBinding
import com.google.android.material.tabs.TabLayout

class CreateFragment : Fragment() {
    private lateinit var viewBinding : LayoutCreateFragmentBinding
    private var type = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutCreateFragmentBinding.inflate(inflater, container, false)
        setUpTabLayout()
        getData()
        return viewBinding.root
    }
    private fun getData(){
        val bundle = arguments
        if(bundle != null){
            type = bundle["Tab"] as String
            Log.d("Type", type)
            when(type){
                "Workout" -> {
                    changedToCreateWorkoutScreen()
                    viewBinding.tabLayout.getTabAt(0)?.select()
                }
                "Plan" ->{
                    changedToCreatePlanScreen()
                    viewBinding.tabLayout.getTabAt(1)?.select()
                }
            }
        }
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
                else if (position == 1){
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
        val personalPlanFragment = PersonalPlanFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.contentLayout, personalPlanFragment)
        fragmentTrans.commit()
    }

    private fun changedToCreateWorkoutScreen() {
        val personalWorkoutFragment = PersonalWorkoutFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.contentLayout, personalWorkoutFragment)
        fragmentTrans.commit()
    }
}