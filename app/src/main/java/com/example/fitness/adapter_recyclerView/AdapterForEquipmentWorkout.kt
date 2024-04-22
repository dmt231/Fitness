package com.example.fitness.adapter_recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.R
import com.example.fitness.databinding.LayoutCustomItemEquipementWithImagesBinding
import com.example.fitness.databinding.LayoutCustomItemTypeWithImagesBinding

class AdapterForEquipmentWorkout(listEquipment: ArrayList<String>, onClickListener: OnClickListener) :
    RecyclerView.Adapter<ViewHolderEquipmentWorkout>() {
    private var listOfEquipment: ArrayList<String>
    private var onClickListener : OnClickListener
    init {
        this.listOfEquipment = listEquipment
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEquipmentWorkout {
        val layoutBinding = LayoutCustomItemEquipementWithImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderEquipmentWorkout(layoutBinding)
    }

    override fun getItemCount(): Int {
        return listOfEquipment.size
    }

    override fun onBindViewHolder(holder: ViewHolderEquipmentWorkout, position: Int) {
        val type = listOfEquipment[position]
        holder.viewBinding.txtForEquipment.text = type
        when (type) {
            "Tạ Đơn" -> {
                holder.viewBinding.imageForEquipment.setImageResource(R.drawable.weight)
            }
            "Tạ Đòn" -> {
                holder.viewBinding.imageForEquipment.setImageResource(R.drawable.barbell)
            }
            "Máy Và Dây Cáp" -> {
                holder.viewBinding.imageForEquipment.setImageResource(R.drawable.machine)
            }
            "Dây Kháng Lực" -> {
                holder.viewBinding.imageForEquipment.setImageResource(R.drawable.resistance_band)
            }
            "Không Có" ->{
                holder.viewBinding.imageForEquipment.setImageResource(R.drawable.abs)
            }
        }
        holder.viewBinding.txtForEquipment.setOnClickListener {
            when (type) {
                "Tạ Đơn" -> {
                    onClickListener.onClickListener("Dumbbell", "Tạ Đơn")
                }
                "Tạ Đòn" -> {
                    onClickListener.onClickListener("Barbell", "Tạ Đòn")
                }
                "Máy Và Dây Cáp" -> {
                    onClickListener.onClickListener("Machine & Cable", "Máy Và Dây Cáp")
                }
                "Dây Kháng Lực" -> {
                    onClickListener.onClickListener("Resistance Band", "Dây Kháng Lực")
                }
                "Không Dụng Cụ" ->{
                    onClickListener.onClickListener("None", "Không Dụng Cụ")
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickListener(equipment : String, title : String)
    }
}

class ViewHolderEquipmentWorkout(viewBinding: LayoutCustomItemEquipementWithImagesBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemEquipementWithImagesBinding

    init {
        this.viewBinding = viewBinding
    }
}