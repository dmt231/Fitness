package com.example.fitness.create.create_workout

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_excercise.AdapterSelectSetRepForExercise
import com.example.fitness.create.create_plan.SaveInfoPlanFragment
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.databinding.LayoutSaveInfoWorkoutBinding
import com.example.fitness.main.MainFragment
import com.example.fitness.model.ExerciseInWorkout
import com.example.fitness.model.Workout
import com.example.fitness.repository.WorkoutRepository
import com.example.fitness.storage.Preferences
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SaveInfoWorkoutFragment : Fragment() {
    private lateinit var viewBinding: LayoutSaveInfoWorkoutBinding
    private var nameWorkout = ""
    private lateinit var listExerciseInWorkout: ArrayList<ExerciseInWorkout>
    private lateinit var adapter: AdapterSelectSetRepForExercise
    private lateinit var workoutRepository: WorkoutRepository
    private lateinit var preferences: Preferences
    private var typeForWorkout: String = ""
    private var namePlan: String = ""
    private var listWorkout : ArrayList<PersonalWorkout>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutSaveInfoWorkoutBinding.inflate(inflater, container, false)
        preferences = Preferences(requireContext())
        getDataWorkout()
        setUpRecyclerView()
        workoutRepository = WorkoutRepository()
        viewBinding.btnSaveData.setOnClickListener {
            if(validateSaveData()){
                val workout = PersonalWorkout(
                    nameWorkout + getTimeRecent(),
                    nameWorkout,
                    listExerciseInWorkout
                )
                when (typeForWorkout) {
                    "Workout" -> {
                        workoutRepository.createWorkoutByUser(
                            preferences.getUserIdValues()!!,
                            workout,
                            object : WorkoutRepository.AddSuccessListener {
                                override fun addSuccessListener() {
                                    changeToMainFragment()
                                }
                            })
                    }
                    "Plan" -> {
                        if(listWorkout == null){
                            listWorkout = ArrayList()
                            listWorkout!!.add(workout)
                        }else{
                            listWorkout?.add(workout)
                        }
                        changeToSaveInfoPlanFragment()
                    }
                }
            }else{
                showToast("Hãy điền đủ thông tin các bài tập")
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

    private fun changeToSaveInfoPlanFragment() {
        val saveInfoPlanFragment = SaveInfoPlanFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("NamePlan", namePlan)
        bundle.putSerializable("listWorkout", listWorkout)
        saveInfoPlanFragment.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, saveInfoPlanFragment)
        fragmentTrans.addToBackStack(saveInfoPlanFragment.tag)
        fragmentTrans.commit()
    }

    private fun changeToMainFragment() {
        val mainFragment = MainFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Menu", "Create")
        bundle.putString("Tab", "Workout")
        mainFragment.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, mainFragment)
        fragmentTrans.commit()
    }

    private fun getDataWorkout() {
        val bundle = arguments
        if (bundle != null) {
            nameWorkout = bundle["nameWorkout"] as String
            viewBinding.editTextSetName.setText(nameWorkout)
            listExerciseInWorkout = bundle["listExercise"] as ArrayList<ExerciseInWorkout>
            typeForWorkout = bundle["WorkoutFor"] as String
            if (bundle["NamePlan"] != null) {
                this.namePlan = bundle["NamePlan"] as String
            }
            if(bundle["listWorkout"] != null){
                this.listWorkout = bundle["listWorkout"] as ArrayList<PersonalWorkout>
            }
        }
    }

    private fun setUpRecyclerView() {
        viewBinding.recyclerViewListExercise.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewListExercise.layoutManager = layout
        adapter = AdapterSelectSetRepForExercise(
            listExerciseInWorkout,
            object : AdapterSelectSetRepForExercise.ShowToast {
                override fun showToast() {
                    showToast("Lưu Thành Công")
                }
            })
        viewBinding.recyclerViewListExercise.adapter = adapter
    }

    private fun showToast(message: String) {
        val toast = Toast(activity)
        val inflater = requireActivity().layoutInflater
        val viewInflate: View = inflater.inflate(
            R.layout.layout_custom_toast,
            requireActivity().findViewById(R.id.custom_toast)
        )
        val textMessage = viewInflate.findViewById<TextView>(R.id.text_toast)
        textMessage.text = message
        toast.view = viewInflate
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.duration = Toast.LENGTH_LONG
        toast.show()
    }
    private fun validateSaveData() : Boolean{
        var check = true
        for(exercise in listExerciseInWorkout){
            if(exercise.getSetAndRep() == null){
                check = false
            }
        }
        return check
    }
    private fun getTimeRecent(): String {
        val calendarToday = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(calendarToday.time)
    }
}