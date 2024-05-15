package com.example.fitness.adapter_recyclerView.adapter_nutrition

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitness.databinding.LayoutCustomItemNutritionBinding
import com.example.fitness.model.Exercise
import com.example.fitness.model.Nutrition
import java.util.*
import kotlin.collections.ArrayList

class AdapterNutrition(listNutrition : ArrayList<Nutrition>) : RecyclerView.Adapter<ViewHolderNutrition>() {
    private var listNutrition : ArrayList<Nutrition>
    private var listNutritionBackup : ArrayList<Nutrition>
    init {
        this.listNutrition = listNutrition
        this.listNutritionBackup = listNutrition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNutrition {
        val viewBinding = LayoutCustomItemNutritionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderNutrition(viewBinding)
    }

    override fun getItemCount(): Int {
        return listNutrition.size
    }

    override fun onBindViewHolder(holder: ViewHolderNutrition, position: Int) {
        val model = listNutrition[position]
        Glide.with(holder.viewBinding.imagesNutrition)
            .load(model.image)
            .into(holder.viewBinding.imagesNutrition)
        holder.viewBinding.apply {
            nameNutrition.text = model.name
            valueKcal.text = model.kcal
            valueProtein.text = model.protein
            valueFat.text = model.fat
            valueCab.text = model.cab
        }
    }
    fun getFilter() : Filter {
        val f = object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val fr = FilterResults()
                if(p0 == null || p0.isEmpty()){
                    fr.count = listNutritionBackup.size
                    fr.values = listNutritionBackup
                }
                else{
                    val newData = ArrayList<Nutrition>()
                    for(nutrition in listNutritionBackup){
                        if(nutrition.name.lowercase(Locale.ROOT).contains(p0.toString()
                                .lowercase(Locale.ROOT)))
                        {
                            newData.add(nutrition)
                        }
                    }
                    fr.count = newData.size
                    fr.values = newData
                }
                return fr
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listNutrition = ArrayList()
                val temp : ArrayList<Nutrition> = p1!!.values as ArrayList<Nutrition>
                for(nutrition in temp){
                    listNutrition.add(nutrition)
                    notifyDataSetChanged()
                }

            }

        }
        return f
    }
}
class ViewHolderNutrition(viewBinding : LayoutCustomItemNutritionBinding) : RecyclerView.ViewHolder(viewBinding.root){
    var viewBinding : LayoutCustomItemNutritionBinding
    init {
        this.viewBinding = viewBinding
    }
}