package com.example.fitness.create.create_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutCreateNameWorkoutBinding

class CreateNameWorkout : Fragment() {
    private lateinit var viewBinding : LayoutCreateNameWorkoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutCreateNameWorkoutBinding.inflate(inflater, container, false)
        viewBinding.btnMoveToAddExercise.setOnClickListener {
            if(validateInput()){
                changeToSelectExercise(viewBinding.editTextSetName.text.toString())
            }
        }
        viewBinding.layoutMain.setOnClickListener {
            //Do Nothing
        }
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return viewBinding.root
    }

    private fun changeToSelectExercise(nameWorkout : String) {
        val selectExercise = SelectExerciseFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("nameWorkout", nameWorkout)
        selectExercise.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, selectExercise)
        fragmentTrans.addToBackStack(selectExercise.tag)
        fragmentTrans.commit()
    }
    private fun validateInput() : Boolean{
        if(viewBinding.editTextSetName.text.isEmpty()) {
            viewBinding.editTextSetName.error = "Vui Lòng Điền Tên Buổi Tập"
            return false
        }
        return true
    }
}