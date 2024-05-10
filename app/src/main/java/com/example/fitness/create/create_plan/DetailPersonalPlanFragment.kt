package com.example.fitness.create.create_plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_workout.AdapterListWorkoutInPersonalPlan
import com.example.fitness.create.create_workout.DetailPersonalWorkout
import com.example.fitness.create.model.PersonalPlan
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.databinding.LayoutDetailPersonalPlanBinding

class DetailPersonalPlanFragment : Fragment() {
    private lateinit var viewBinding : LayoutDetailPersonalPlanBinding
    private var listWorkout : ArrayList<PersonalWorkout>? = null
    private lateinit var adapter : AdapterListWorkoutInPersonalPlan
    private var plan : PersonalPlan? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutDetailPersonalPlanBinding.inflate(inflater, container, false)
        getData()
        viewBinding.linearLayoutMain.setOnClickListener {
            //DO NOTHING
        }
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return viewBinding.root
    }
    private fun getData(){
        val bundle = arguments
        if(bundle != null){
            plan = bundle["personalPlan"] as PersonalPlan
            viewBinding.nameWorkout.text = plan!!.namePlan
            listWorkout = plan!!.listWorkout
            setUpRecyclerView()
        }
    }
    private fun setUpRecyclerView(){
        viewBinding.recyclerViewListWorkout.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewListWorkout.layoutManager = layout
        adapter = AdapterListWorkoutInPersonalPlan(listWorkout!!, object :
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
}