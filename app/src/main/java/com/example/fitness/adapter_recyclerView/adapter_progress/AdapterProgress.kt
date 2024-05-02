package com.example.fitness.adapter_recyclerView.adapter_progress

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.databinding.CustomLayoutItemHistoryBinding
import com.example.fitness.model.History

class AdapterProgress(listHistory : ArrayList<History>) : RecyclerView.Adapter<ProgressViewHolder>() {
    private var listHistory : ArrayList<History>
    init {
        this.listHistory = listHistory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
       val viewBinding = CustomLayoutItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgressViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val model = listHistory[position]
        holder.viewBinding.nameWorkout.text = model.getName()
        holder.viewBinding.dateWorkout.text = "Ngày: ${model.getDate()}"
        holder.viewBinding.durationWorkout.text = "Thời Gian: ${model.getDuration()}"
        holder.viewBinding.percentageWorkout.text = "Hoàn thành: ${model.getPercentage()} %"
    }
}
class ProgressViewHolder(viewBinding : CustomLayoutItemHistoryBinding) : RecyclerView.ViewHolder(viewBinding.root){
    var viewBinding : CustomLayoutItemHistoryBinding
    init {
        this.viewBinding = viewBinding
    }
}