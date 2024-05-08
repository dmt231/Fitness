package com.example.fitness.adapter_recyclerView.adapter_plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.databinding.LayoutCustomItemByDayBinding

class AdapterPlanDay(listDay : ArrayList<String>, onClickListener : OnClickDayListener) : RecyclerView.Adapter<DayViewHolder>() {
    private var listDay : ArrayList<String>
    private var onClickDayListener : OnClickDayListener
    init {
        this.listDay = listDay
        this.onClickDayListener = onClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val viewBinding = LayoutCustomItemByDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listDay.size
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
       val model = listDay[position]
        holder.viewBinding.txtForDay.text = model
        holder.viewBinding.txtForDay.setOnClickListener {
            onClickDayListener.onClick(model, "$model Ngày")
        }
    }
    interface OnClickDayListener{
        fun onClick(day : String, title : String)
    }
}
class DayViewHolder(viewBinding : LayoutCustomItemByDayBinding) : RecyclerView.ViewHolder(viewBinding.root){
    var viewBinding : LayoutCustomItemByDayBinding
    init {
        this.viewBinding = viewBinding
    }
}