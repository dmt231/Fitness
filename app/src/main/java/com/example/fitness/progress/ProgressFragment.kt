package com.example.fitness.progress

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.adapter_recyclerView.adapter_progress.AdapterProgress
import com.example.fitness.databinding.LayoutDialogEndWorkoutBinding
import com.example.fitness.databinding.LayoutDialogUpdateValueBinding
import com.example.fitness.databinding.LayoutProgressBinding
import com.example.fitness.model.History
import com.example.fitness.repository.UserRepository
import com.example.fitness.storage.Preferences
import java.util.*
import kotlin.collections.ArrayList

class ProgressFragment : Fragment() {
    private lateinit var viewBinding : LayoutProgressBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var listHistory : ArrayList<History>
    private lateinit var userRepository: UserRepository
    private var adapter : AdapterProgress?=null
    private var userId : String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutProgressBinding.inflate(inflater, container, false)
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        listHistory = ArrayList()
        userRepository = UserRepository()
        onSetUpRecyclerView()
        userId = Preferences(requireContext()).getUserIdValues()
        getRecordValue(userId)
        getDataHistory(userId)
        viewBinding.layoutDeadlift.setOnClickListener {
            openDialogValue(userId, "deadlift")
        }
        viewBinding.layoutSquat.setOnClickListener {
            openDialogValue(userId, "squat")
        }
        viewBinding.layoutBenchPress.setOnClickListener {
            openDialogValue(userId, "benchPress")
        }
        return viewBinding.root
    }
    private fun onSetUpRecyclerView() {
        viewBinding.recyclerViewHistory.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewHistory.layoutManager = layout
        adapter = AdapterProgress(listHistory)
        viewBinding.recyclerViewHistory.adapter = adapter
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getDataHistory(userId : String?){
        historyViewModel.getAllLiveDataExercise(userId)?.observe(viewLifecycleOwner){
            if(it != null){
                for(history in it){
                    listHistory.add(history)
                }
                listHistory.sortWith(Comparator { h1, h2 ->
                    // Sử dụng phương thức compareTo của String để so sánh ngày
                    h2.getDate().toString().compareTo(h1.getDate().toString())
                })
                adapter?.notifyDataSetChanged()
            }
        }
    }
    private fun getRecordValue(userId: String?){
        userRepository.getRecordForUser(userId!!, object : UserRepository.GetRecord{
            override fun onReturnValue(deadlift: String, squat: String, benchPress: String) {
               if(deadlift != ""){
                   viewBinding.valueDeadlift.text = deadlift
               }
                if(squat != ""){
                    viewBinding.valueSquat.text = squat
                }
                if(benchPress != ""){
                    viewBinding.valueBenchPress.text = benchPress
                }
            }
        })
    }
    private fun openDialogValue(userId: String?, type : String){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = LayoutDialogUpdateValueBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.txtForUpdate.text = type
        binding.btnYes.setOnClickListener {
            when(type){
                "deadlift" -> {viewBinding.valueDeadlift.text = binding.valueUpdate.text}
                "squat" ->{viewBinding.valueSquat.text = binding.valueUpdate.text}
                "benchPress"->{viewBinding.valueBenchPress.text = binding.valueUpdate.text}
            }
            userRepository.updateRecordValue(userId!!, binding.valueUpdate.text.toString(), type)
            dialog.cancel()
        }
        binding.btnNo.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }
}