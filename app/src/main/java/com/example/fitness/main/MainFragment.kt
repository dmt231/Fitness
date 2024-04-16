package com.example.fitness.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutMainFragmentBinding
import com.example.fitness.explore.ExploreFragment

class MainFragment : Fragment() {
    private lateinit var viewBinding : LayoutMainFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutMainFragmentBinding.inflate(inflater, container, false)
        changedToExplore()
        return viewBinding.root
    }
    private fun changedToExplore(){
        val exploreFragment = ExploreFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.mainLayout, exploreFragment)
        fragmentTrans.commit()
    }
}