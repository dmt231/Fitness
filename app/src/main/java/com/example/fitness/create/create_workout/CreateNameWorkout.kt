package com.example.fitness.create.create_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.databinding.LayoutCreateNameWorkoutBinding
import com.example.fitness.model.Workout

class CreateNameWorkout : Fragment() {
    private lateinit var viewBinding : LayoutCreateNameWorkoutBinding
    private var typeForWorkout : String = ""
    private var namePlan : String = ""
    private var listWorkout : ArrayList<PersonalWorkout>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutCreateNameWorkoutBinding.inflate(inflater, container, false)
        getTypeForWorkout()
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
        bundle.putString("WorkoutFor", typeForWorkout)
        if(namePlan != ""){
            bundle.putString("NamePlan", namePlan)
        }
        if(listWorkout != null){
            bundle.putSerializable("listWorkout", listWorkout)
        }
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
    private fun getTypeForWorkout(){
        val bundle = arguments
        if(bundle != null){
            typeForWorkout = bundle["WorkoutFor"] as String
            if(bundle["NamePlan"] != null){
                this.namePlan = bundle["NamePlan"] as String
            }
            if(bundle["listWorkout"] != null){
                this.listWorkout = bundle["listWorkout"] as ArrayList<PersonalWorkout>
            }
        }
    }
}