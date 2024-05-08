package com.example.fitness.adapter_recyclerView.adapter_workout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.databinding.LayoutCustomItemPersonalListWorkoutBinding
import com.example.fitness.model.Workout

class AdapterListPersonalWorkout(listWorkout : ArrayList<Workout>, onClickListener : OnWorkoutClickListener) : RecyclerView.Adapter<PersonalWorkoutViewHolder>() {
    private var listWorkout : ArrayList<Workout>
    private var onClick : OnWorkoutClickListener
    init {
        this.listWorkout = listWorkout
        this.onClick = onClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalWorkoutViewHolder {
        val viewBinding = LayoutCustomItemPersonalListWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonalWorkoutViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listWorkout.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PersonalWorkoutViewHolder, position: Int) {
        val model = listWorkout[position]
        holder.viewBinding.txtNameWorkout.text = model.name.toString()
        holder.viewBinding.numberExercise.text = "${model.listExercise?.size.toString()} Bài Tập"
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onClick.onWorkoutClickListener(model)
        }
    }
    interface OnWorkoutClickListener{
        fun onWorkoutClickListener(workout : Workout)
    }
}
class PersonalWorkoutViewHolder(viewBinding : LayoutCustomItemPersonalListWorkoutBinding) : RecyclerView.ViewHolder(viewBinding.root){
    var viewBinding : LayoutCustomItemPersonalListWorkoutBinding
    init {
        this.viewBinding = viewBinding
    }
}
