package com.example.fitness.explore.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_plan.AdapterGoal
import com.example.fitness.adapter_recyclerView.adapter_plan.AdapterPlanDay
import com.example.fitness.databinding.LayoutPlanFragmentBinding
import com.example.fitness.explore.workout.ListWorkout
import com.example.fitness.model.Goal

class PlanFragment : Fragment() {
    private lateinit var viewBinding: LayoutPlanFragmentBinding
    private lateinit var adapterPlanDay: AdapterPlanDay
    private lateinit var adapterGoal: AdapterGoal
    private lateinit var listGoal: ArrayList<Goal>
    private lateinit var listDay: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutPlanFragmentBinding.inflate(inflater, container, false)
        listDay = ArrayList()
        listGoal = ArrayList()
        setUpDayData()
        setUpGoalData()
        setUpDayRecyclerView()
        setUpGoalRecyclerView()
        return viewBinding.root
    }

    private fun setUpGoalRecyclerView() {
        viewBinding.recyclerViewGoal.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        viewBinding.recyclerViewGoal.layoutManager = gridLayoutManager
        adapterGoal = AdapterGoal(listGoal, object : AdapterGoal.OnClickGoalListener {
            override fun onClickGoal(goal: String, title: String) {
                onChangedToListPlan("Goal", goal, title)
            }
        })
        viewBinding.recyclerViewGoal.adapter = adapterGoal

    }

    private fun setUpGoalData() {
        listGoal.add(
            Goal(
                "Athleticism",
                "https://blog.thewodlife.com.au/wp-content/uploads/2022/04/two-female-athletes-doing-box-jumps.png"
            )
        )
        listGoal.add(
            Goal(
                "Strength",
                "https://cdn.outsideonline.com/wp-content/uploads/2017/05/12/deadlift-workout-lead_s.jpg"
            )
        )
        listGoal.add(
            Goal(
                "Size",
                "https://i.pinimg.com/736x/ac/b0/d5/acb0d5be8b648a70f3e19eddfb50f250.jpg"
            )
        )
        listGoal.add(
            Goal(
                "Home",
                "https://media.istockphoto.com/id/1313995787/photo/vertical-portrait-of-handsome-sporty-african-american-man-doing-side-plank-exercise-at-home.jpg?s=612x612&w=0&k=20&c=jQSo4N6LGQAntnx9bxb9IEFYtFsHNjasz7KZtw_9C1k="
            )
        )
    }

    private fun setUpDayData() {
        listDay.add("3")
        listDay.add("4")
        listDay.add("5")
        listDay.add("6")
    }

    private fun setUpDayRecyclerView() {
        viewBinding.recyclerViewDay.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.recyclerViewDay.layoutManager = layout
        adapterPlanDay = AdapterPlanDay(listDay, object : AdapterPlanDay.OnClickDayListener {
            override fun onClick(day: String, title: String) {
                onChangedToListPlan("Day", day, title)
            }
        })
        viewBinding.recyclerViewDay.adapter = adapterPlanDay
    }

    private fun onChangedToListPlan(type: String, query: String, title: String) {
        val listPlan = ListPlanFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Type", type)
        bundle.putString("Query", query)
        bundle.putString("Title", title)
        listPlan.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, listPlan)
        fragmentTrans.addToBackStack(listPlan.tag)
        fragmentTrans.commit()
    }
}