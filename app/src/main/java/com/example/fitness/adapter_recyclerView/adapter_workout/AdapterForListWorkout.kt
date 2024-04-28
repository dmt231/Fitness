package com.example.fitness.adapter_recyclerView.adapter_workout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomItemListExerciseBinding
import com.example.fitness.databinding.LayoutCustomItemListWorkoutBinding
import com.example.fitness.model.Exercise
import com.example.fitness.model.Workout
import java.util.*
import kotlin.collections.ArrayList


class AdapterForListWorkout(listWorkout: ArrayList<Workout>,onClick : OnClickListener) : RecyclerView.Adapter<ViewHolderListWorkout>() {
    private var listWorkout: ArrayList<Workout>
    private var onClick : OnClickListener
    private var listWorkoutBackUp: ArrayList<Workout>? = null
    init {
        this.listWorkout = listWorkout
        this.onClick = onClick
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListWorkout {
       val viewBinding = LayoutCustomItemListWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListWorkout(viewBinding)
    }

    override fun getItemCount(): Int {
        return listWorkout.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderListWorkout, position: Int) {
        val model = listWorkout[position]
        Glide.with(holder.viewBinding.imagesCustomList)
            .load(model.imgCovered)
            .into(holder.viewBinding.imagesCustomList)
        holder.viewBinding.txtCustomList.text = model.name
        holder.viewBinding.timeTxt.text = model.time.toString() + " Phút"
        when(model.difficulty){
            "Beginner" ->{holder.viewBinding.levelTxt.text = "Dễ"}
            "Advanced"->{holder.viewBinding.levelTxt.text = "Trung Bình"}
            "Hard"->{holder.viewBinding.levelTxt.text = "Khó"}
        }
        holder.viewBinding.linearLayoutCustomItem.setOnClickListener {
            onClick.onClickListener(model)
        }
    }
    interface OnClickListener{
        fun onClickListener(workout: Workout)
    }
    fun getFilter() : Filter {
        val f = object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val fr = FilterResults()
                if(listWorkoutBackUp == null) {
                    listWorkoutBackUp = ArrayList(listWorkout)
                }
                if(p0 == null || p0.isEmpty()){
                    fr.count = listWorkoutBackUp!!.size
                    fr.values = listWorkoutBackUp
                }
                else{
                    val newData = ArrayList<Workout>()
                    for(workout in listWorkoutBackUp!!){
                        if(workout.name?.lowercase(Locale.ROOT)!!.contains(p0.toString()
                                .lowercase(Locale.ROOT)))
                        {
                            newData.add(workout)
                        }
                    }
                    fr.count = newData.size
                    fr.values = newData
                }
                return fr
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listWorkout = ArrayList()
                val temp : ArrayList<Workout> = p1!!.values as ArrayList<Workout>
                for(workout in temp){
                    listWorkout.add(workout)
                    notifyDataSetChanged()
                }

            }

        }
        return f
    }
}
class ViewHolderListWorkout(viewBinding: LayoutCustomItemListWorkoutBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    var viewBinding: LayoutCustomItemListWorkoutBinding

    init {
        this.viewBinding = viewBinding
    }
}