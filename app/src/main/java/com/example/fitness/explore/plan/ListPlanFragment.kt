package com.example.fitness.explore.plan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_plan.AdapterListPlan
import com.example.fitness.databinding.LayoutListPlanBinding
import com.example.fitness.model.Plan

class ListPlanFragment : Fragment() {
    private lateinit var viewBinding : LayoutListPlanBinding
    private lateinit var planViewModel : PlanViewModel
    private lateinit var listPlan : ArrayList<Plan>
    private lateinit var adapterPlan : AdapterListPlan
    private var title : String = ""
    private var type : String = ""
    private var query : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutListPlanBinding.inflate(inflater, container, false)
        viewBinding.layoutListPlan.setOnClickListener {

        }
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        planViewModel = ViewModelProvider(this)[PlanViewModel::class.java]
        listPlan = ArrayList()
        getData()
        setUpTitleData()
        setUpRecyclerView()
        retrievePlanData()
        return viewBinding.root
    }

    private fun getData() {
        val bundle = arguments
        if(bundle != null){
            type = bundle["Type"] as String
            title = bundle["Title"] as String
            query = bundle["Query"] as String
        }
    }
    private fun setUpTitleData(){
        viewBinding.titleType.text = title
    }
    private fun setUpRecyclerView(){
        viewBinding.recyclerPlan.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerPlan.layoutManager = layout
        adapterPlan = AdapterListPlan(listPlan, object : AdapterListPlan.OnClickPlanListener {
            override fun onClick(plan: Plan) {
                changeToDetailPlan(plan)
            }

        })
        viewBinding.recyclerPlan.adapter = adapterPlan
    }
    @SuppressLint("SetTextI18n")
    private fun retrievePlanData(){
        planViewModel.getLiveDataPlan(type, query)?.observe(viewLifecycleOwner){
            for(plan in it){
                listPlan.add(plan)
                viewBinding.progressBar.visibility = View.GONE
                viewBinding.numberPlan.text = it.size.toString() + " Lịch Tập"
                adapterPlan.notifyDataSetChanged()
            }
        }
    }
    private fun changeToDetailPlan(plan : Plan){
        val detailPlan = DetailPlan()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("plan", plan)
        detailPlan.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, detailPlan)
        fragmentTrans.addToBackStack(detailPlan.tag)
        fragmentTrans.commit()
    }

}