package com.example.fitness.adapter_recyclerView.adapter_excercise

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomItemSelectExerciseBinding
import com.example.fitness.model.Exercise
import java.util.*
import kotlin.collections.ArrayList


class AdapterSelectedExercise(listExercise: ArrayList<Exercise>, onClick : OnClickListenerExercise ) : RecyclerView.Adapter<ViewHolderListExerciseForSelect>() {
    private var listExercise: ArrayList<Exercise>
    private var onClick : OnClickListenerExercise
    private var listExerciseBackUp : ArrayList<Exercise>? = null
    init {
        this.listExercise = listExercise
        this.onClick = onClick
        this.listExerciseBackUp = listExercise
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderListExerciseForSelect {
        val viewBinding = LayoutCustomItemSelectExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListExerciseForSelect(viewBinding)
    }

    override fun getItemCount(): Int {
       return listExercise.size
    }

    override fun onBindViewHolder(holder: ViewHolderListExerciseForSelect, position: Int) {
        val model = listExercise[position]
        Glide.with(holder.viewBinding.imagesCustomList)
            .load(model.getImage())
            .into(holder.viewBinding.imagesCustomList)
        holder.viewBinding.txtCustomList.text = model.getName()
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onClick.onClickListener(model)
        }
        holder.viewBinding.checkBox.isChecked = model.getStatusChecked()
        holder.viewBinding.checkBox.setOnClickListener {
            val isChecked = holder.viewBinding.checkBox.isChecked
            model.setChecked(isChecked)
            for(exercise in listExerciseBackUp!!){
                if(exercise == model){
                    exercise.setChecked(isChecked)
                }
            }
            var number = 0
            for(exercise in listExerciseBackUp!!){
                if(exercise.getStatusChecked()){
                    number++
                }
            }
            onClick.countNumberExerciseSelected(number)
        }
    }
    interface OnClickListenerExercise{
        fun onClickListener(exercise: Exercise)
        fun countNumberExerciseSelected(number: Int)
    }
    fun getFilter() : Filter {
        val f = object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val fr = FilterResults()
                if(p0 == null || p0.isEmpty()){
                    fr.count = listExerciseBackUp!!.size
                    fr.values = listExerciseBackUp
                }
                else{
                    val newData = ArrayList<Exercise>()
                    for(exercise in listExerciseBackUp!!){
                        if(exercise.getName()?.lowercase(Locale.ROOT)!!.contains(p0.toString()
                                .lowercase(Locale.ROOT)))
                        {
                            newData.add(exercise)
                        }
                    }
                    fr.count = newData.size
                    fr.values = newData
                }
                return fr
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listExercise = ArrayList()
                val temp : ArrayList<Exercise> = p1!!.values as ArrayList<Exercise>
                for(chat in temp){
                    listExercise.add(chat)
                    notifyDataSetChanged()
                }

            }

        }
        return f
    }
}
class ViewHolderListExerciseForSelect(viewBinding: LayoutCustomItemSelectExerciseBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemSelectExerciseBinding

    init {
        this.viewBinding = viewBinding
    }
}