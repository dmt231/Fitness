package com.example.fitness.explore.workout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutFinishWorkoutBinding
import com.example.fitness.main.MainFragment
import com.example.fitness.model.History

class FinishWorkout : Fragment() {
    private lateinit var viewBinding : LayoutFinishWorkoutBinding
    private var history : History? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutFinishWorkoutBinding.inflate(inflater, container, false)
        viewBinding.btnCancel.setOnClickListener {
            changeToProgressFragment()
        }
        getData()
        return viewBinding.root
    }
    @SuppressLint("SetTextI18n")
    private fun getData(){
        val bundle = arguments
        if(bundle != null){
            history = bundle["History"] as History
            viewBinding.nameWorkout.text = history?.getName()
            viewBinding.date.text = history?.getDate()
            viewBinding.duration.text = "Thời Lượng: ${history?.getDuration()}"
            viewBinding.percentage.text = "Hoàn Thành: ${history?.getPercentage()} %"
        }
    }
    private fun changeToProgressFragment(){
        val mainFragment = MainFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("Menu", "Progress")
        mainFragment.arguments = bundle
        fragmentTrans.replace(R.id.layout_main_activity, mainFragment)
        fragmentTrans.commit()
    }
}