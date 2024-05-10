package com.example.fitness.adapter_recyclerView.adapter_workout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.databinding.LayoutCustomItemListWorkoutInPersonalPlanBinding
import com.example.fitness.databinding.LayoutCustomItemPersonalListWorkoutBinding
import com.example.fitness.model.Workout

class AdapterListWorkoutInPersonalPlan(
    listWorkout: ArrayList<PersonalWorkout>,
    onClickListener: OnWorkoutClickListener
) : RecyclerView.Adapter<ViewHolderListWorkoutInPersonalPlan>() {
    private var listWorkout: ArrayList<PersonalWorkout>
    private var onClick: OnWorkoutClickListener

    init {
        this.listWorkout = listWorkout
        this.onClick = onClickListener
    }


    interface OnWorkoutClickListener {
        fun onWorkoutClickListener(workout: PersonalWorkout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderListWorkoutInPersonalPlan {
        val viewBinding = LayoutCustomItemListWorkoutInPersonalPlanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderListWorkoutInPersonalPlan(viewBinding)
    }

    override fun getItemCount(): Int {
        return listWorkout.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderListWorkoutInPersonalPlan, position: Int) {
        val model = listWorkout[position]
        holder.viewBinding.txtDay.text = "Ngày ${position + 1}"
        holder.viewBinding.txtNameWorkout.text = model.nameWorkout
        holder.viewBinding.numberExercise.text = "${model.listExercise.size.toString()} Bài Tập"
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onClick.onWorkoutClickListener(model)
        }
    }
}

class ViewHolderListWorkoutInPersonalPlan(viewBinding: LayoutCustomItemListWorkoutInPersonalPlanBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemListWorkoutInPersonalPlanBinding

    init {
        this.viewBinding = viewBinding
    }
}