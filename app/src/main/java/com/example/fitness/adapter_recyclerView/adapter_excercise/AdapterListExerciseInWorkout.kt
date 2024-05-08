package com.example.fitness.adapter_recyclerView.adapter_excercise

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutListExerciseInWorkoutBinding
import com.example.fitness.model.ExerciseInWorkout

class AdapterListExerciseInWorkout(listExercise: ArrayList<ExerciseInWorkout> , onClick : OnClickListenerExerciseInWorkout) : RecyclerView.Adapter<ViewHolderListExerciseInWorkout>() {
    private var listExercise: ArrayList<ExerciseInWorkout>
    private var onClick : OnClickListenerExerciseInWorkout
    init {
        this.listExercise = listExercise
        this.onClick = onClick
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListExerciseInWorkout {
        val viewBinding = LayoutListExerciseInWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListExerciseInWorkout(viewBinding)
    }

    override fun getItemCount(): Int {
        return listExercise.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderListExerciseInWorkout, position: Int) {
        val exerciseModel = listExercise[position]
        if(exerciseModel.getImage() != null){
            Glide.with(holder.viewBinding.imagesCustomList)
                .load(exerciseModel.getImage())
                .into(holder.viewBinding.imagesCustomList)
        }
        holder.viewBinding.nameExercise.text = exerciseModel.getName()
        holder.viewBinding.setRep.text = exerciseModel.getSetAndRep()
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onClick.onClickListener(exerciseModel.getIdExercise().toString())
        }
    }
    interface OnClickListenerExerciseInWorkout{
        fun onClickListener(idExercise: String)
    }
}
class ViewHolderListExerciseInWorkout(viewBinding: LayoutListExerciseInWorkoutBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutListExerciseInWorkoutBinding

    init {
        this.viewBinding = viewBinding
    }
}
