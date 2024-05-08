package com.example.fitness.create.create_workout

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_excercise.AdapterSelectedExercise
import com.example.fitness.databinding.LayoutSelectExerciseBinding
import com.example.fitness.explore.workout.excercise.DetailExercise
import com.example.fitness.explore.workout.excercise.ExerciseViewModel
import com.example.fitness.model.Exercise
import com.example.fitness.model.ExerciseInWorkout


class SelectExerciseFragment : Fragment() {
    private lateinit var viewBinding : LayoutSelectExerciseBinding
    private lateinit var exerciseViewModel : ExerciseViewModel
    private lateinit var adapterSelectExercise : AdapterSelectedExercise
    private lateinit var listExercise : ArrayList<Exercise>
    private lateinit var listExerciseInWorkout : ArrayList<ExerciseInWorkout>
    private var nameWorkout : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutSelectExerciseBinding.inflate(inflater, container, false)
        listExercise = ArrayList()
        listExerciseInWorkout = ArrayList()
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        getNameWorkoutData()
        setUpRecyclerView()
        getNameWorkoutData()
        getExerciseData()
        searchExercise()
        viewBinding.mainLayout.setOnClickListener {
            //DO NOTHING
        }
        viewBinding.btnAddExercise.setOnClickListener {
            handleListExerciseInWorkout()
            changeToSaveInfoWorkout()
        }
        viewBinding.btCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return viewBinding.root
    }

    private fun getExerciseData() {
        exerciseViewModel.getAllLiveDataExercise()?.observe(viewLifecycleOwner){
            if(it != null){
                viewBinding.progressBar.visibility = View.GONE
                for(exercise in it){
                    val position = listExercise.size
                    listExercise.add(position, exercise)
                    adapterSelectExercise.notifyItemInserted(position)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        viewBinding.recyclerViewExercise.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewExercise.layoutManager = layout
        adapterSelectExercise = AdapterSelectedExercise(listExercise, object :
            AdapterSelectedExercise.OnClickListenerExercise{
            override fun onClickListener(exercise: Exercise) {
                changeToDetailExercise(exercise)
            }

            @SuppressLint("SetTextI18n")
            override fun countNumberExerciseSelected(number: Int) {
                if(number != 0){
                val txtRecent = requireContext().getString(R.string.add_exercise_in_workout)
                viewBinding.btnAddExercise.text = "$txtRecent (${number.toString()})"}
            }

        })
        viewBinding.recyclerViewExercise.adapter = adapterSelectExercise
    }

    private fun changeToDetailExercise(exercise : Exercise) {
        val detailExercise = DetailExercise()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("exercise", exercise)
        detailExercise.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, detailExercise)
        fragmentTrans.addToBackStack(detailExercise.tag)
        fragmentTrans.commit()
    }

    private fun getNameWorkoutData() {
        val bundle = arguments
        if(bundle != null){
            nameWorkout = bundle["nameWorkout"] as String
        }
    }
    private fun searchExercise(){
        viewBinding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapterSelectExercise.getFilter().filter(p0.toString())
                adapterSelectExercise.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
    private fun handleListExerciseInWorkout(){
        for(exercise in listExercise){
            if(exercise.getStatusChecked()){
                val exerciseInWorkout = ExerciseInWorkout(exercise.getId(),null)
                exerciseInWorkout.setName(exercise.getName().toString())
                exerciseInWorkout.setImage(exercise.getImage().toString())
                exerciseInWorkout.setChecked(exercise.getStatusChecked())
                listExerciseInWorkout.add(exerciseInWorkout)
            }
        }
    }
    private fun changeToSaveInfoWorkout(){
        val saveWorkout = SaveInfoWorkoutFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("nameWorkout", nameWorkout)
        bundle.putSerializable("listExercise", listExerciseInWorkout)
        saveWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, saveWorkout)
        fragmentTrans.addToBackStack(saveWorkout.tag)
        fragmentTrans.commit()
    }
}