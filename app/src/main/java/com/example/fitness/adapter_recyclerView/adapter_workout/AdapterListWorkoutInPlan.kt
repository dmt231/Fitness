package com.example.fitness.adapter_recyclerView.adapter_workout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomItemListWorkoutInPlanBinding
import com.example.fitness.model.WorkoutInPlan

class AdapterListWorkoutInPlan(listWorkoutInPlan : ArrayList<WorkoutInPlan>, clickListener : WorkoutItemClickListener ) : RecyclerView.Adapter<ViewHolderListWorkoutInPlan> (){
    private var listWorkoutInPlan : ArrayList<WorkoutInPlan>
    private var clickListener : WorkoutItemClickListener
    init {
        this.listWorkoutInPlan = listWorkoutInPlan
        this.clickListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListWorkoutInPlan {
        val viewBinding = LayoutCustomItemListWorkoutInPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListWorkoutInPlan(viewBinding)
    }

    override fun getItemCount(): Int {
        return listWorkoutInPlan.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderListWorkoutInPlan, position: Int) {
        val model = listWorkoutInPlan[position]
        if(model.img != null){
            Glide.with(holder.viewBinding.imagesCustomList)
                .load(model.img)
                .into(holder.viewBinding.imagesCustomList)
        }
        if(model.workout == "Rest") {
            holder.viewBinding.txtWorkout.text = "Ngày Nghỉ"
            holder.viewBinding.txtDay.text = "Ngày ${model.day} "
            holder.viewBinding.imagesCustomList.visibility = View.GONE
            holder.viewBinding.relativeLayout.visibility = View.GONE
        }else{
            holder.viewBinding.txtWorkout.text = model.workout
            holder.viewBinding.txtDay.text = "Ngày ${model.day} "
            holder.viewBinding.timeTxt.text = "${model.time} Phút"
            holder.viewBinding.numberExerciseTxt.text = "${model.numberExercise} Bài Tập"
            holder.viewBinding.imagesCustomList.visibility = View.VISIBLE
            holder.viewBinding.relativeLayout.visibility = View.VISIBLE
        }
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            if(model.workout != "Rest")
            {
                clickListener.passIdWorkout(model.workout)
            }
        }
    }
    interface WorkoutItemClickListener{
        fun passIdWorkout(idWorkout : String)
    }
}
class ViewHolderListWorkoutInPlan(viewBinding : LayoutCustomItemListWorkoutInPlanBinding) : RecyclerView.ViewHolder(viewBinding.root){
    var viewBinding : LayoutCustomItemListWorkoutInPlanBinding
    init {
        this.viewBinding = viewBinding
    }
}