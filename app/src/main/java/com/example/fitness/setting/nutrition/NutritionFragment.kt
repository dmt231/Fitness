package com.example.fitness.setting.nutrition

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.adapter_recyclerView.adapter_nutrition.AdapterNutrition
import com.example.fitness.databinding.LayoutDialogUpdateValueBinding
import com.example.fitness.databinding.LayoutNutritionFragmentBinding
import com.example.fitness.databinding.LayoutUpdateBmiValueBinding
import com.example.fitness.model.Nutrition

class NutritionFragment : Fragment() {
    private lateinit var viewBinding : LayoutNutritionFragmentBinding
    private lateinit var nutritionViewModel : NutritionViewModel
    private lateinit var listNutrition : ArrayList<Nutrition>
    private lateinit var adapter : AdapterNutrition
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutNutritionFragmentBinding.inflate(inflater, container, false)
        nutritionViewModel = ViewModelProvider(this)[NutritionViewModel::class.java]
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewBinding.layoutListNutrition.setOnClickListener {
            //DO NOTHING
        }
        listNutrition = ArrayList()
        getData()
        setUpRecyclerView()
        onSearchNutrition()
        return viewBinding.root
    }

    private fun setUpRecyclerView() {
        viewBinding.recyclerNutrition.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerNutrition.layoutManager = layout
        adapter = AdapterNutrition(listNutrition)
        viewBinding.recyclerNutrition.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        nutritionViewModel.getLiveDataPlan()?.observe(viewLifecycleOwner){
            if(it != null){
                viewBinding.progressBar.visibility = View.GONE
                listNutrition.addAll(it)
                listNutrition.sortWith(Comparator { h1, h2 ->
                    h1.name.compareTo(h2.name)
                })
                adapter.notifyDataSetChanged()
            }
        }
    }
    private fun onSearchNutrition(){
        viewBinding.searchBar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.getFilter().filter(p0.toString())
                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }
        })
    }
}