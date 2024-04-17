package com.example.fitness.adapter_recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.R
import com.example.fitness.databinding.LayoutCustomItemBodyPartWithImagesBinding
import com.example.fitness.databinding.LayoutCustomItemTypeWithImagesBinding

class AdapterForBodyPartWorkout(listBodyPart: ArrayList<String>) :
    RecyclerView.Adapter<ViewHolderBodyPartWorkout>() {
    private var listOfBodyPart: ArrayList<String>

    init {
        this.listOfBodyPart = listBodyPart
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBodyPartWorkout {
        val layoutBinding = LayoutCustomItemBodyPartWithImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderBodyPartWorkout(layoutBinding)
    }

    override fun getItemCount(): Int {
        return listOfBodyPart.size
    }

    override fun onBindViewHolder(holder: ViewHolderBodyPartWorkout, position: Int) {
        val type = listOfBodyPart[position]
        holder.viewBinding.txtBodyPart.text = type
    }

}

class ViewHolderBodyPartWorkout(viewBinding: LayoutCustomItemBodyPartWithImagesBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemBodyPartWithImagesBinding

    init {
        this.viewBinding = viewBinding
    }
}