package com.example.fitness.adapter_recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.R
import com.example.fitness.databinding.LayoutCustomItemTypeWithImagesBinding

class AdapterForTypeWorkout(listType: ArrayList<String>, onClickListener : OnClickListener) :
    RecyclerView.Adapter<ViewHolderTypeWorkout>() {
    private var listOfType: ArrayList<String>
    private var onClick: OnClickListener
    init {
        this.listOfType = listType
        this.onClick = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTypeWorkout {
        val layoutBinding = LayoutCustomItemTypeWithImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderTypeWorkout(layoutBinding)
    }

    override fun getItemCount(): Int {
        return listOfType.size
    }

    override fun onBindViewHolder(holder: ViewHolderTypeWorkout, position: Int) {
        val type = listOfType[position]
        holder.viewBinding.txtForType.text = type
        when (type) {
            "Trọng Lượng Cơ Thể" -> {
                holder.viewBinding.imageForType.setImageResource(R.drawable.pull_up_bar)
            }
            "Cardio" -> {
                holder.viewBinding.imageForType.setImageResource(R.drawable.runner)
            }
            "Kháng Lực" -> {
                holder.viewBinding.imageForType.setImageResource(R.drawable.fitness)
            }
            "Giãn Cơ" -> {
                holder.viewBinding.imageForType.setImageResource(R.drawable.stretch)
            }
            "Không Dụng Cụ" ->{
                holder.viewBinding.imageForType.setImageResource(R.drawable.chest)
            }
        }
        holder.viewBinding.cardView.setOnClickListener {
            when (type) {
                "Trọng Lượng Cơ Thể" -> {
                    onClick.onClickListener("BodyWeight", type)
                }
                "Cardio" -> {
                    onClick.onClickListener("Cardio", type)
                }
                "Kháng Lực" -> {
                    onClick.onClickListener("Resistance", type)
                }
                "Giãn Cơ" -> {
                    onClick.onClickListener("Stretching", type)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickListener(query : String, title : String)
    }
}

class ViewHolderTypeWorkout(viewBinding: LayoutCustomItemTypeWithImagesBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemTypeWithImagesBinding

    init {
        this.viewBinding = viewBinding
    }
}