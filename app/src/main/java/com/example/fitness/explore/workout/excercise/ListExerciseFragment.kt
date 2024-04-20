package com.example.fitness.explore.workout.excercise

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.adapter_recyclerView.adapter_excercise.AdapterForListExercise
import com.example.fitness.databinding.LayoutExerciseBinding
import com.example.fitness.model.Exercise
@SuppressLint("NotifyDataSetChanged")
class ListExerciseFragment : Fragment() {
    private lateinit var viewBinding : LayoutExerciseBinding
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var adapterListExercise : AdapterForListExercise
    private var listExercise : ArrayList<Exercise> = ArrayList()
    private var type : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutExerciseBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        setUpRecyclerView()
        getDataFromMenu()
        handleInputData()
        return viewBinding.root
    }

    private fun handleInputData() {
        if(type == "Full Body"){
            getAllDataFromServer()
        }
        else{
            getDataFromServer(type)
        }
    }
    private fun getDataFromServer(type: String) {
        viewModel.getLiveDataExercise(type)?.observe(viewLifecycleOwner){
            if(it != null){
                for(exercise in it){
                    listExercise.add(exercise)
                }
                adapterListExercise.notifyDataSetChanged()
            }
        }
    }

    private fun getAllDataFromServer(){
        viewModel.getAllLiveDataExercise()?.observe(viewLifecycleOwner
        ) {
            if(it != null){
                for(exercise in it){
                    listExercise.add(exercise)
                }
                adapterListExercise.notifyDataSetChanged()
            }
        }
    }
    private fun setUpRecyclerView(){
        viewBinding.recyclerViewExercise.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewExercise.layoutManager = layout
        adapterListExercise = AdapterForListExercise(listExercise)
        viewBinding.recyclerViewExercise.adapter = adapterListExercise
    }
    private fun getDataFromMenu(){
        val bundle = arguments
        if(bundle != null){
            this.type = bundle["bodyPart"] as String
        }
    }
}