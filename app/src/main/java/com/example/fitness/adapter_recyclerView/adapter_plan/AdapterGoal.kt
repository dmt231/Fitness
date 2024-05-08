package com.example.fitness.adapter_recyclerView.adapter_plan

import android.annotation.SuppressLint
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomCardViewGoalBinding
import com.example.fitness.model.Goal

class AdapterGoal(listGoal : ArrayList<Goal>, onClickGoal : OnClickGoalListener) : RecyclerView.Adapter<GoalViewHolder>() {
    private var listGoal : ArrayList<Goal>
    private var onClickGoal : OnClickGoalListener
    init {
        this.listGoal = listGoal
        this.onClickGoal = onClickGoal
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val viewBinding = LayoutCustomCardViewGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listGoal.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val model = listGoal[position]
        holder.viewBinding.txtGoal.text = model.goal
        when(model.goal){
            "Athleticism" ->{holder.viewBinding.txtGoal.text = "Vận Động Viên"}
            "Strength" -> {holder.viewBinding.txtGoal.text = "Sức Mạnh"}
            "Size" -> {holder.viewBinding.txtGoal.text = "Kích Thước"}
            "Home" -> {holder.viewBinding.txtGoal.text = "Tại Nhà"}
        }
        Glide.with(holder.viewBinding.imgGoal)
            .load(model.image)
            .into(holder.viewBinding.imgGoal)
        holder.viewBinding.imgGoal.setOnClickListener {
            when(model.goal){
                "Athleticism" ->{onClickGoal.onClickGoal(model.goal,"Vận Động Viên")}
                "Strength" -> {onClickGoal.onClickGoal(model.goal,"Sức Mạnh")}
                "Size" -> {onClickGoal.onClickGoal(model.goal,"Kích Thước")}
                "Home" -> {onClickGoal.onClickGoal(model.goal,"Tại Nhà")}
            }
        }
    }
    interface OnClickGoalListener{
        fun onClickGoal(goal : String, title : String)
    }

}
class GoalViewHolder(viewBinding : LayoutCustomCardViewGoalBinding) : RecyclerView.ViewHolder(viewBinding.root){
    var viewBinding : LayoutCustomCardViewGoalBinding
    init {
        this.viewBinding = viewBinding
    }
}