package com.example.fitness.adapter_recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.R
import com.example.fitness.databinding.LayoutCustomItemBodyPartWithImagesBinding
import com.example.fitness.databinding.LayoutCustomItemTypeWithImagesBinding

class AdapterForBodyPartWorkout(listBodyPart: ArrayList<String>, onClickListener: OnClickListener) :
    RecyclerView.Adapter<ViewHolderBodyPartWorkout>() {
    private var listOfBodyPart: ArrayList<String>
    private var onClickListener: OnClickListener

    init {
        this.listOfBodyPart = listBodyPart
        this.onClickListener = onClickListener
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
        holder.viewBinding.txtBodyPart.setOnClickListener {
            when (type) {
                "Tay" -> {
                    onClickListener.onClickListener("Arm", type)
                }
                "Ngực" -> {
                    onClickListener.onClickListener("Chest", type)
                }
                "Vai" -> {
                    onClickListener.onClickListener("Shoulder", type)
                }
                "Lưng" -> {
                    onClickListener.onClickListener("Back", type)
                }
                "Chân" -> {
                    onClickListener.onClickListener("Leg", type)
                }
                "Mông" -> {
                    onClickListener.onClickListener("Glutes", type)
                }
                "Abs & Core" -> {
                    onClickListener.onClickListener("Abs & Core", type)
                }
                "Thân Trên" -> {
                    onClickListener.onClickListener("Upper Body", type)
                }
                "Thân Dưới" -> {
                    onClickListener.onClickListener("Lower Body", type)
                }
                "Toàn Thân" -> {
                    onClickListener.onClickListener("Full Body", type)
                }
            }
        }
    }
    interface OnClickListener {
        fun onClickListener(query: String, title: String)
    }
}

class ViewHolderBodyPartWorkout(viewBinding: LayoutCustomItemBodyPartWithImagesBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemBodyPartWithImagesBinding

    init {
        this.viewBinding = viewBinding
    }
}