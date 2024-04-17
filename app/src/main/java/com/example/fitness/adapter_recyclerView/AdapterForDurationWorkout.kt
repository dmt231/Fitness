package com.example.fitness.adapter_recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.R
import com.example.fitness.databinding.LayoutCustomItemDurationWithImagesBinding
import com.example.fitness.databinding.LayoutCustomItemTypeWithImagesBinding

class AdapterForDurationWorkout(listTime: ArrayList<String>) :
    RecyclerView.Adapter<ViewHolderTimeWorkout>() {
    private var listOfTime: ArrayList<String>

    init {
        this.listOfTime = listTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTimeWorkout {
        val layoutBinding = LayoutCustomItemDurationWithImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderTimeWorkout(layoutBinding)
    }

    override fun getItemCount(): Int {
        return listOfTime.size
    }

    override fun onBindViewHolder(holder: ViewHolderTimeWorkout, position: Int) {
        val type = listOfTime[position]
        holder.viewBinding.txtForMinutes.text = type
    }

}

class ViewHolderTimeWorkout(viewBinding: LayoutCustomItemDurationWithImagesBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemDurationWithImagesBinding

    init {
        this.viewBinding = viewBinding
    }
}