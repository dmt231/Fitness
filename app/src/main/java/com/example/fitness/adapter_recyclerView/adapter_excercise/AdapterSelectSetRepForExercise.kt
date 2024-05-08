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

                Log.d("On Text Set Rep", "True")
                val set = holder.viewBinding.editTxtSet.text.toString()
                val rep = holder.viewBinding.editTxtRep.text.toString()
                if (set.isEmpty()) {
                    holder.viewBinding.editTxtSet.error = "Hãy điền số set"
                } else {
                    val setAndRep = "$set Sets x $rep Reps"
                    model.updateSetAndRep(setAndRep)
                }
            }

            if (holder.viewBinding.radioTime.isChecked) {

                Log.d("On Text Minutes", "True")
                val minutes = holder.viewBinding.editTxtMinute.text.toString()
                val second = holder.viewBinding.editTxtSecond.text.toString()
                var setAndRep = "Mặc Định"
                if (minutes.toInt() == 0 && second.isNotEmpty()) {
                    setAndRep = "${second.toInt()} Second"
                } else if (second.toInt() == 0 && minutes.isNotEmpty()) {
                    setAndRep = "$minutes Minutes"
                } else if (minutes.isNotEmpty() && second.isNotEmpty()) {
                    setAndRep = "$minutes Minutes $second Second"
                }
                model.updateSetAndRep(setAndRep)

            }
            showToast.showToast()
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