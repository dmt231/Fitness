package com.example.fitness.explore.workout.excercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutMenuExcerciseBinding

class MenuBodyPartExercise : Fragment() {
    private lateinit var viewBinding : LayoutMenuExcerciseBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutMenuExcerciseBinding.inflate(inflater, container, false)
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewBinding.fullBody.setOnClickListener {
            onChangedToListExercise()
        }
        return viewBinding.root
    }
    private fun onChangedToListExercise(){
        val listExercise = ListExerciseFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.layout_main_activity, listExercise)
        fragmentTrans.addToBackStack(listExercise.tag)
        fragmentTrans.commit()
    }
}