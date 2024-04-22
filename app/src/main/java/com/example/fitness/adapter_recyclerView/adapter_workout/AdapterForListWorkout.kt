package com.example.fitness.adapter_recyclerView.adapter_workout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomItemListExerciseBinding
import com.example.fitness.databinding.LayoutCustomItemListWorkoutBinding
import com.example.fitness.model.Workout


class AdapterForListWorkout(listWorkout: ArrayList<Workout>,onClick : OnClickListener) : RecyclerView.Adapter<ViewHolderListWorkout>() {
    private var listWorkout: ArrayList<Workout>
    private var onClick : OnClickListener
    init {
        this.listWorkout = listWorkout
        this.onClick = onClick
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListWorkout {
       val viewBinding = LayoutCustomItemListWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListWorkout(viewBinding)
    }

    override fun getItemCount(): Int {
        return listWorkout.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderListWorkout, position: Int) {
        val model = listWorkout[position]
        Glide.with(holder.viewBinding.imagesCustomList)
            .load(model.imgCovered)
            .into(holder.viewBinding.imagesCustomList)
        holder.viewBinding.txtCustomList.text = model.name
        holder.viewBinding.timeTxt.text = model.time.toString() + " Phút"
        when(model.difficulty){
            "Beginner" ->{holder.viewBinding.levelTxt.text = "Dễ"}
            "Advanced"->{holder.viewBinding.levelTxt.text = "Trung Bình"}
            "Hard"->{holder.viewBinding.levelTxt.text = "Khó"}
        }
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onClick.onClickListener(model)
        }
    }
    interface OnClickListener{
        fun onClickListener(workout: Workout)
    }
}
class ViewHolderListWorkout(viewBinding: LayoutCustomItemListWorkoutBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemListWorkoutBinding

    init {
        this.viewBinding = viewBinding
    }
}