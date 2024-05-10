package com.example.fitness.adapter_recyclerView.adapter_plan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.create.model.PersonalPlan
import com.example.fitness.databinding.LayoutCustomItemPersonalListPlanBinding

class AdapterListPersonalPlan(listPlan: ArrayList<PersonalPlan>, onDetailPlanClick : OnDetailPlanClick) : RecyclerView.Adapter<ListPersonalPlanViewHolder>()  {
    private var listPlan : ArrayList<PersonalPlan>
    private var onDetailPlanClick : OnDetailPlanClick
    init {
        this.listPlan = listPlan
        this.onDetailPlanClick = onDetailPlanClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPersonalPlanViewHolder {
        val viewBinding = LayoutCustomItemPersonalListPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListPersonalPlanViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listPlan.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListPersonalPlanViewHolder, position: Int) {
        val model = listPlan[position]
        holder.viewBinding.txtNameWorkout.text = model.namePlan
        holder.viewBinding.numberWorkout.text = "${model.listWorkout.size} Buổi Tập"
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onDetailPlanClick.onClick(model)
        }
    }
    interface OnDetailPlanClick{
        fun onClick(plan : PersonalPlan)
    }
}
class ListPersonalPlanViewHolder(viewBinding : LayoutCustomItemPersonalListPlanBinding) : RecyclerView.ViewHolder(viewBinding.root){
    var viewBinding : LayoutCustomItemPersonalListPlanBinding
    init {
        this.viewBinding = viewBinding
    }
}