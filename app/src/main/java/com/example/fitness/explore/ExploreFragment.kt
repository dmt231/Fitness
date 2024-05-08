package com.example.fitness.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutExploreBinding
import com.example.fitness.explore.plan.PlanFragment
import com.example.fitness.explore.workout.MenuWorkoutFragment
import com.google.android.material.tabs.TabLayout

class ExploreFragment : Fragment() {
    private lateinit var viewBinding : LayoutExploreBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutExploreBinding.inflate(inflater, container, false)
        setUpTabLayout()
        changedToWorkoutMenuScreen()
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
                    changedToWorkoutMenuScreen()
                }
                else{
                    changedToPlanMenuScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun changedToPlanMenuScreen() {
        val planFragment = PlanFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.contentLayout, planFragment)
        fragmentTrans.addToBackStack(planFragment.tag)
        fragmentTrans.commit()
    }

    private fun changedToWorkoutMenuScreen(){
        val menuWorkoutFragment = MenuWorkoutFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.contentLayout, menuWorkoutFragment)
        fragmentTrans.addToBackStack(menuWorkoutFragment.tag)
        fragmentTrans.commit()
    }
}