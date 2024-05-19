package com.example.fitness.adapter_recyclerView.adapter_excercise

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutListExerciseInStartWorkoutBinding
import com.example.fitness.model.ExerciseInWorkout

class AdapterExerciseInStartWorkout(
    listExerciseInWorkout: ArrayList<ExerciseInWorkout>,
    openCountDownTimer: OpenCountDownTimer
) :
    RecyclerView.Adapter<ViewHolderExerciseInStartWorkout>() {
    private var listExerciseInWorkout: ArrayList<ExerciseInWorkout>
    private var openCountDownTimer: OpenCountDownTimer

    init {
        this.listExerciseInWorkout = listExerciseInWorkout
        this.openCountDownTimer = openCountDownTimer
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
        if (exerciseModel.getRep() != null) {
            holder.viewBinding.rep.text = exerciseModel.getRep() + " Rep"
        } else {
            val splitResult = exerciseModel.getSetAndRep().toString().split(" ")
            when(splitResult[1]){
                "Minutes" ->{holder.viewBinding.rep.text = "${splitResult[0]} Phút"}
                "Second" ->{holder.viewBinding.rep.text = "${splitResult[0]} Giây"}
                "Hour" ->{holder.viewBinding.rep.text = "${splitResult[0]} Giờ"}
            }
        }
        holder.viewBinding.checkBox.isChecked = exerciseModel.getChecked()
        holder.viewBinding.checkBox.setOnClickListener {
            val isChecked = holder.viewBinding.checkBox.isChecked
            exerciseModel.setChecked(isChecked)
            if (holder.viewBinding.checkBox.isChecked) {
                openCountDownTimer.openCountdownTimer()
            }
        }
    }

    interface OpenCountDownTimer {
        fun openCountdownTimer()
    }
}

class ViewHolderExerciseInStartWorkout(viewBinding: LayoutListExerciseInStartWorkoutBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutListExerciseInStartWorkoutBinding

    init {
        this.viewBinding = viewBinding
    }
}