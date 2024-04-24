package com.example.fitness.adapter_recyclerView.adapter_excercise

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutListExerciseInStartWorkoutBinding
import com.example.fitness.model.ExerciseInWorkout

class AdapterExerciseInStartWorkout(listExerciseInWorkout: ArrayList<ExerciseInWorkout>) :
    RecyclerView.Adapter<ViewHolderExerciseInStartWorkout>() {
    private var listExerciseInWorkout: ArrayList<ExerciseInWorkout>

    init {
        this.listExerciseInWorkout = listExerciseInWorkout
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderExerciseInStartWorkout {
        val viewBinding = LayoutListExerciseInStartWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderExerciseInStartWorkout(viewBinding)
    }

    override fun getItemCount(): Int {
        return listExerciseInWorkout.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderExerciseInStartWorkout, position: Int) {
        val exerciseModel = listExerciseInWorkout[position]
        Glide.with(holder.viewBinding.imagesCustomList)
            .load(exerciseModel.getImage())
            .into(holder.viewBinding.imagesCustomList)
        holder.viewBinding.nameExercise.text = exerciseModel.getName()
        if (!exerciseModel.getRep()!!.contains("Minutes") && !exerciseModel.getRep()!!
                .contains("Second") && !exerciseModel.getRep()!!.contains("Hour")
        ) {
            holder.viewBinding.rep.text = exerciseModel.getRep() + " Rep"
        } else {
            holder.viewBinding.rep.text = exerciseModel.getRep()
        }
    }
}

class ViewHolderExerciseInStartWorkout(viewBinding: LayoutListExerciseInStartWorkoutBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutListExerciseInStartWorkoutBinding

    init {
        this.viewBinding = viewBinding
    }
}