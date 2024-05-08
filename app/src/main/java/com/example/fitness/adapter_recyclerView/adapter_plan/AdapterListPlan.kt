package com.example.fitness.adapter_recyclerView.adapter_plan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomItemPlanBinding
import com.example.fitness.model.Plan

class AdapterListPlan(listPlan: ArrayList<Plan>, onClickListener : OnClickPlanListener) : RecyclerView.Adapter<ViewHolderListPlan>() {
    private var listPlan: ArrayList<Plan>
    private var onClickListener : OnClickPlanListener
    init {
        this.listPlan = listPlan
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPlan {
        val viewBinding =
            LayoutCustomItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListPlan(viewBinding)
    }

    override fun getItemCount(): Int {
        return listPlan.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderListPlan, position: Int) {
        val model = listPlan[position]
        holder.viewBinding.txtPlan.text = model.namePlan
        holder.viewBinding.dayTxt.text = "${model.dayOfWeek} Ngày"
        when (model.difficulty) {
            "Advanced" -> {
                holder.viewBinding.levelTxt.text = "Trung Bình"
            }
            "Hard" -> {
                holder.viewBinding.levelTxt.text = "Khó"
            }
            "Beginner" -> {
                holder.viewBinding.levelTxt.text = "Dễ"
            }
        }
        Glide.with(holder.viewBinding.imagesPlanList)
            .load(model.imgCover)
            .into(holder.viewBinding.imagesPlanList)
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onClickListener.onClick(model)
        }
    }
    interface OnClickPlanListener{
        fun onClick(plan : Plan)
    }
}

class ViewHolderListPlan(viewBinding: LayoutCustomItemPlanBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemPlanBinding

    init {
        this.viewBinding = viewBinding
    }
}