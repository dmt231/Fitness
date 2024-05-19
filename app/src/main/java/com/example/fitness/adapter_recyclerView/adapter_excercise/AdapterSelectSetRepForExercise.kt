package com.example.fitness.adapter_recyclerView.adapter_excercise

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomSetRepInExerciseBinding
import com.example.fitness.model.ExerciseInWorkout

class AdapterSelectSetRepForExercise(
    listExerciseForWorkout: ArrayList<ExerciseInWorkout>,
    showToast: ShowToast
) :
    RecyclerView.Adapter<ViewHolderSelectSetRep>() {
    private var listExercise: ArrayList<ExerciseInWorkout>
    private var showToast: ShowToast

    init {
        this.listExercise = listExerciseForWorkout
        this.showToast = showToast
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSelectSetRep {
        val viewBinding = LayoutCustomSetRepInExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderSelectSetRep(viewBinding)
    }

    override fun getItemCount(): Int {
        return listExercise.size
    }

    override fun onBindViewHolder(holder: ViewHolderSelectSetRep, position: Int) {
        val model = listExercise[position]
        Glide.with(holder.viewBinding.imagesCustomList)
            .load(model.getImage())
            .into(holder.viewBinding.imagesCustomList)
        holder.viewBinding.txtCustomList.text = model.getName()
        holder.viewBinding.radioSetRep.setOnClickListener {
            holder.viewBinding.layoutPickSetRep.visibility = View.VISIBLE
            holder.viewBinding.layoutPickTime.visibility = View.GONE
        }
        holder.viewBinding.radioTime.setOnClickListener {
            holder.viewBinding.layoutPickSetRep.visibility = View.GONE
            holder.viewBinding.layoutPickTime.visibility = View.VISIBLE
        }
        holder.viewBinding.buttonSave.setOnClickListener {
            if (holder.viewBinding.radioSetRep.isChecked) {

                val set = holder.viewBinding.editTxtSet.text.toString()
                val rep = holder.viewBinding.editTxtRep.text.toString()
                if (set.isEmpty()) {
                    holder.viewBinding.editTxtSet.error = "Hãy điền số set"
                }else if(rep.isEmpty()){
                    holder.viewBinding.editTxtRep.error = "Hãy điền số rep"
                }
                else {
                    val setAndRep = "$set Sets x $rep Reps"
                    model.updateSetAndRep(setAndRep)
                    showToast.showToast()
                }
            }

            if (holder.viewBinding.radioTime.isChecked) {

                val minutes = holder.viewBinding.editTxtMinute.text.toString()
                val second = holder.viewBinding.editTxtSecond.text.toString()
                var setAndRep = "Mặc Định"
                if (minutes == "0" && second != "") {
                    setAndRep = "${second.toInt()} Second"
                    model.updateSetAndRep(setAndRep)
                    showToast.showToast()
                } else if (second == "0" && minutes != "") {
                    setAndRep = "$minutes Minutes"
                    model.updateSetAndRep(setAndRep)
                    showToast.showToast()
                } else if (minutes != "" && second != "") {
                    setAndRep = "$minutes Minutes $second Second"
                    model.updateSetAndRep(setAndRep)
                    showToast.showToast()
                }else if(minutes == "" && second == ""){
                    holder.viewBinding.editTxtMinute.error = "Hãy điền số phút"
                    holder.viewBinding.editTxtSecond.error = "Hãy điền số giây"
                }else if(second == "") {
                    holder.viewBinding.editTxtSecond.error = "Hãy điền số giây"
                }else{
                    holder.viewBinding.editTxtMinute.error = "Hãy điền số phút"
                }
            }

        }
    }

    interface ShowToast {
        fun showToast()
    }
}

class ViewHolderSelectSetRep(viewBinding: LayoutCustomSetRepInExerciseBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomSetRepInExerciseBinding

    init {
        this.viewBinding = viewBinding
    }
}