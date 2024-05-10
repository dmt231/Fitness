package com.example.fitness.create.create_plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.create.create_workout.CreateNameWorkout
import com.example.fitness.databinding.LayoutCreateNamePlanBinding

class CreateNamePlan : Fragment() {
    private lateinit var viewBinding : LayoutCreateNamePlanBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutCreateNamePlanBinding.inflate(inflater, container, false)
        viewBinding.btnMoveToAddWorkout.setOnClickListener {
            if(validateInput()){
                changeToCreateNameWorkout(viewBinding.editTextSetName.text.toString())
            }
        }
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return viewBinding.root
    }

    private fun changeToCreateNameWorkout(name : String) {
        val createNameWorkout = CreateNameWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("WorkoutFor", "Plan")
        bundle.putString("NamePlan", name)
        createNameWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, createNameWorkout)
        fragmentTrans.addToBackStack(createNameWorkout.tag)
        fragmentTrans.commit()
    }

    private fun validateInput() : Boolean{
        if(viewBinding.editTextSetName.text.isEmpty()) {
            viewBinding.editTextSetName.error = "Vui Lòng Điền Tên Kế Hoạch Tập"
            return false
        }
        return true
    }
}