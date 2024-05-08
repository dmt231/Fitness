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
import com.example.fitness.databinding.LayoutSaveInfoWorkoutBinding
import com.example.fitness.main.MainFragment
import com.example.fitness.model.ExerciseInWorkout
import com.example.fitness.model.Workout
import com.example.fitness.repository.WorkoutRepository
import com.example.fitness.storage.Preferences

class SaveInfoWorkoutFragment : Fragment() {
    private lateinit var viewBinding : LayoutSaveInfoWorkoutBinding
    private var nameWorkout = ""
    private lateinit var listExerciseInWorkout : ArrayList<ExerciseInWorkout>
    private lateinit var adapter : AdapterSelectSetRepForExercise
    private lateinit var workoutRepository: WorkoutRepository
    private lateinit var preferences : Preferences
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
            val workout = Workout(nameWorkout, null,null,null,nameWorkout,null,null,null,null,null, listExerciseInWorkout)
            workoutRepository.createWorkoutByUser(preferences.getUserIdValues()!!, workout, object : WorkoutRepository.AddSuccessListener{
                override fun addSuccessListener() {
                    changeToMainFragment()
                }
            })
        }
        viewBinding.layoutMain.setOnClickListener {
            //Do Nothing
        }
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return viewBinding.root
    }

    private fun changeToMainFragment() {
        val mainFragment = MainFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Tab", "Create")
        mainFragment.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, mainFragment)
        fragmentTrans.commit()
    }

    private fun getDataWorkout(){
        val bundle = arguments
        if(bundle != null){
            nameWorkout = bundle["nameWorkout"] as String
            viewBinding.editTextSetName.setText(nameWorkout)
            listExerciseInWorkout = bundle["listExercise"] as ArrayList<ExerciseInWorkout>
        }
    }
    private fun setUpRecyclerView(){
        viewBinding.recyclerViewListExercise.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewListExercise.layoutManager = layout
        adapter = AdapterSelectSetRepForExercise(listExerciseInWorkout, object : AdapterSelectSetRepForExercise.ShowToast{
            override fun showToast() {
                showToast("Lưu Thành Công")
            }
        })
        viewBinding.recyclerViewListExercise.adapter = adapter
    }
    private fun showToast(message : String){
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

}