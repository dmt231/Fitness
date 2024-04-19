package com.example.fitness.adapter_recyclerView.adapter_excercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.adapter_recyclerView.ViewHolderTypeWorkout
import com.example.fitness.databinding.LayoutCustomItemListExerciseBinding
import com.example.fitness.model.Exercise


class AdapterForListExercise(listExercise: ArrayList<Exercise>) : RecyclerView.Adapter<ViewHolderListExercise>() {
    private var listExercise: ArrayList<Exercise>
    init {
        this.listExercise = listExercise
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListExercise {
       val viewBinding = LayoutCustomItemListExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListExercise(viewBinding)
    }

    override fun getItemCount(): Int {
        return listExercise.size
    }

    override fun onBindViewHolder(holder: ViewHolderListExercise, position: Int) {
        val model = listExercise[position]
        Glide.with(holder.viewBinding.imagesCustomList)
            .load(model.getImage())
            .into(holder.viewBinding.imagesCustomList)
        holder.viewBinding.txtCustomList.text = model.getName()
    }
}
class ViewHolderListExercise(viewBinding: LayoutCustomItemListExerciseBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemListExerciseBinding

    init {
        this.viewBinding = viewBinding
    }
}