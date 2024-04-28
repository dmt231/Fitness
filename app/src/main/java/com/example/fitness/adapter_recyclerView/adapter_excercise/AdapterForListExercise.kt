package com.example.fitness.adapter_recyclerView.adapter_excercise

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomItemListExerciseBinding
import com.example.fitness.model.Exercise
import java.util.*
import kotlin.collections.ArrayList


class AdapterForListExercise(listExercise: ArrayList<Exercise>,onClick : OnClickListener) : RecyclerView.Adapter<ViewHolderListExercise>() {
    private var listExercise: ArrayList<Exercise>
    private var onClick : OnClickListener
    private var listExerciseBackUp : ArrayList<Exercise>? = null
    init {
        this.listExercise = listExercise
        this.onClick = onClick
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
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onClick.onClickListener(model)
        }
    }
    interface OnClickListener{
        fun onClickListener(exercise: Exercise)
    }
    fun getFilter() : Filter {
        val f = object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val fr = FilterResults()
                if(listExerciseBackUp == null) {
                    listExerciseBackUp = ArrayList(listExercise)
                }
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
class ViewHolderListExercise(viewBinding: LayoutCustomItemListExerciseBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemListExerciseBinding

    init {
        this.viewBinding = viewBinding
    }
}