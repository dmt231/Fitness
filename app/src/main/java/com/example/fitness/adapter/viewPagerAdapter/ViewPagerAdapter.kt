package com.example.fitness.adapter.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitness.authentication.welcome.FirsTextFragment
import com.example.fitness.authentication.welcome.FourFragment
import com.example.fitness.authentication.welcome.SecondTextFragment
import com.example.fitness.authentication.welcome.ThirdFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val numberPages = 4

    override fun getItemCount(): Int {
        return numberPages
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return FirsTextFragment()
            }
            1 -> {
                return SecondTextFragment()
            }
            2 -> {
                return ThirdFragment()
            }
            3 -> {
                return FourFragment()
            }
        }
        return FirsTextFragment()
    }
}