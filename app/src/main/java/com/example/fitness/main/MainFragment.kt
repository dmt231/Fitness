package com.example.fitness.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.create.CreateFragment
import com.example.fitness.databinding.LayoutMainFragmentBinding
import com.example.fitness.explore.ExploreFragment
import com.example.fitness.progress.ProgressFragment
import com.example.fitness.setting.SettingFragment

class MainFragment : Fragment() {
    private lateinit var viewBinding : LayoutMainFragmentBinding
    private var type : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutMainFragmentBinding.inflate(inflater, container, false)
        getData()
        onSelectedItemMenuBar()
        return viewBinding.root
    }
    private fun getData(){
        val bundle = arguments
        if(bundle != null){
            type = bundle["Menu"] as String
            if(type == "Explore"){
                changedToExplore()
                viewBinding.bottomBar.menu.findItem(R.id.explore).isChecked = true
            }else if(type == "Progress"){
                changedToProgress()
                viewBinding.bottomBar.menu.findItem(R.id.progress).isChecked = true
            }else if(type == "Create"){
                val tab = bundle["Tab"] as String
                changedToCreate(tab)
                viewBinding.bottomBar.menu.findItem(R.id.create).isChecked = true
            }
        }
    }

    private fun changedToExplore(){
        val exploreFragment = ExploreFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.mainLayout, exploreFragment)
        fragmentTrans.commit()
    }
    private fun changedToProgress(){
        val progressFragment = ProgressFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.mainLayout, progressFragment)
        fragmentTrans.commit()
    }
    private fun onSelectedItemMenuBar(){
        viewBinding.bottomBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.explore ->{
                    changedToExplore()
                    viewBinding.bottomBar.menu.findItem(R.id.explore).isChecked=true
                }
                R.id.progress ->{
                    changedToProgress()
                    viewBinding.bottomBar.menu.findItem(R.id.progress).isChecked=true
                }
                R.id.create ->{
                    changedToCreate("Workout")
                    viewBinding.bottomBar.menu.findItem(R.id.create).isChecked = true
                }
                R.id.setting -> {
                    changedToSetting()
                    viewBinding.bottomBar.menu.findItem(R.id.setting).isChecked = true
                }
            }
            false
        }
    }

    private fun changedToSetting() {
        val settingFragment = SettingFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.mainLayout, settingFragment)
        fragmentTrans.commit()
    }

    private fun changedToCreate(tab : String) {
        val createFragment = CreateFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Tab", tab)
        createFragment.arguments = bundle
        fragmentTrans.replace(R.id.mainLayout, createFragment)
        fragmentTrans.commit()
    }
}