package com.example.fitness.create.create_plan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_workout.AdapterListWorkoutInPersonalPlan
import com.example.fitness.create.create_workout.CreateNameWorkout
import com.example.fitness.create.create_workout.DetailPersonalWorkout
import com.example.fitness.create.model.PersonalPlan
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.databinding.LayoutSavePlanBinding
import com.example.fitness.main.MainFragment
import com.example.fitness.model.Plan
import com.example.fitness.model.Workout
import com.example.fitness.repository.PlanRepository
import com.example.fitness.storage.Preferences

class SaveInfoPlanFragment : Fragment() {
    private lateinit var viewBinding : LayoutSavePlanBinding
    private lateinit var listWorkout : ArrayList<PersonalWorkout>
    private lateinit var adapter : AdapterListWorkoutInPersonalPlan
    private lateinit var planRepository: PlanRepository
    private lateinit var preferences : Preferences
    private var namePlan = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutSavePlanBinding.inflate(inflater, container, false)
        listWorkout = ArrayList()
        planRepository = PlanRepository()
        preferences = Preferences(requireContext())
        setUpRecyclerView()
        getData()
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewBinding.btnAddMoreWorkout.setOnClickListener {
            changeToAddWorkout()
        }
        viewBinding.btnSaveData.setOnClickListener {
            val personalPlan = PersonalPlan("Id$namePlan", namePlan, listWorkout)
            planRepository.createPlanByUser(preferences.getUserIdValues()!!, personalPlan, object : PlanRepository.QueryListPlan{
                override fun onSuccessListener() {
                    changeToMainFragment()
                }

                override fun onNotFoundListener() {

                }

                override fun onFoundListPlanListener(listPlan: ArrayList<PersonalPlan>) {

                }
            })
        }
        return viewBinding.root
    }

    private fun changeToMainFragment() {
        val mainFragment = MainFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Menu", "Create")
        bundle.putString("Tab", "Plan")
        mainFragment.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, mainFragment)
        fragmentTrans.commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(){
        val bundle = arguments
        if(bundle != null){
            namePlan = bundle["NamePlan"] as String
            viewBinding.editTextSetName.setText(namePlan)
            if(bundle["listWorkout"] != null){
                val listWorkoutData = bundle["listWorkout"] as ArrayList<PersonalWorkout>
                for(workout in listWorkoutData){
                    val position = listWorkout.size
                    listWorkout.add(position, workout)
                    adapter.notifyItemInserted(position)
                }
            }
        }
    }
    private fun setUpRecyclerView(){
        viewBinding.recyclerViewListWorkout.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewListWorkout.layoutManager = layout
        adapter = AdapterListWorkoutInPersonalPlan(listWorkout, object :
            AdapterListWorkoutInPersonalPlan.OnWorkoutClickListener{
            override fun onWorkoutClickListener(workout: PersonalWorkout) {
               changeToDetailWorkout(workout)
            }
        })
        viewBinding.recyclerViewListWorkout.adapter = adapter
    }
    private fun changeToDetailWorkout(workout : PersonalWorkout){
        val detailPersonalWorkout = DetailPersonalWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("workoutPersonal", workout)
        detailPersonalWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, detailPersonalWorkout)
        fragmentTrans.addToBackStack(null)
        fragmentTrans.commit()
    }
    private fun changeToAddWorkout(){
        val createNameWorkout = CreateNameWorkout()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("WorkoutFor", "Plan")
        bundle.putString("NamePlan", namePlan)
        bundle.putSerializable("listWorkout", listWorkout)
        createNameWorkout.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, createNameWorkout)
        fragmentTrans.addToBackStack(createNameWorkout.tag)
        fragmentTrans.commit()
    }
}