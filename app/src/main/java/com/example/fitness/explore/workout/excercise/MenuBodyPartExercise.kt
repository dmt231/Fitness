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
            onChangedToListExercise("Full Body")
        }
        viewBinding.bicep.setOnClickListener {
            onChangedToListExercise("Bicep")
        }
        viewBinding.tricep.setOnClickListener {
            onChangedToListExercise("Tricep")
        }
        viewBinding.backBody.setOnClickListener {
            onChangedToListExercise("Back")
        }
        viewBinding.chest.setOnClickListener {
            onChangedToListExercise("Chest")
        }
        viewBinding.Shoulder.setOnClickListener {
            onChangedToListExercise("Shoulder")
        }
        viewBinding.Quads.setOnClickListener {
            onChangedToListExercise("Quads")
        }
        viewBinding.Hamstring.setOnClickListener {
            onChangedToListExercise("Hamstring")
        }
        viewBinding.Glutes.setOnClickListener {
            onChangedToListExercise("Glutes")
        }
        viewBinding.Calves.setOnClickListener {
            onChangedToListExercise("Calf")
        }
        viewBinding.Abs.setOnClickListener {
            onChangedToListExercise("Abs & Core")
        }
        return viewBinding.root
    }
    private fun onChangedToListExercise(bodyPart : String){
        val listExercise = ListExerciseFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("bodyPart", bodyPart)
        listExercise.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, listExercise)
        fragmentTrans.addToBackStack(listExercise.tag)
        fragmentTrans.commit()
    }
}