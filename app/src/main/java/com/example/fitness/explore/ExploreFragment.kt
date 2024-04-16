package com.example.fitness.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.databinding.LayoutExploreBinding
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
        return viewBinding.root
    }
    private fun setUpTabLayout(){
        viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setText("Buổi Tập"))
        viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setText("Lịch Tập"))
        viewBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }
}