package com.example.fitness.explore.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.AdapterForBodyPartWorkout
import com.example.fitness.adapter_recyclerView.AdapterForDurationWorkout
import com.example.fitness.adapter_recyclerView.AdapterForEquipmentWorkout
import com.example.fitness.adapter_recyclerView.AdapterForTypeWorkout
import com.example.fitness.databinding.LayoutMenuWorkoutBinding
import com.example.fitness.explore.workout.excercise.MenuBodyPartExercise

class MenuWorkoutFragment : Fragment() {
    private lateinit var viewBinding : LayoutMenuWorkoutBinding
    private lateinit var adapterType : AdapterForTypeWorkout
    private lateinit var listType : ArrayList<String>

    private lateinit var adapterTime : AdapterForDurationWorkout
    private lateinit var listTime : ArrayList<String>

    private lateinit var adapterEquipment : AdapterForEquipmentWorkout
    private lateinit var listEquipment : ArrayList<String>

    private lateinit var adapterBodyPart : AdapterForBodyPartWorkout
    private lateinit var listBodyPart : ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutMenuWorkoutBinding.inflate(inflater, container, false)
        initListType()
        initListTime()
        initListEquipment()
        initListBodyPart()
        setUpTypeRecyclerView()
        setUpTimeRecyclerView()
        setUpEquipmentRecyclerView()
        setUpBodyPartRecyclerView()
        viewBinding.libraryExercise.setOnClickListener {
            onChangedToMenuExercise()
        }
        return viewBinding.root
    }
    private fun initListType(){
        listType = ArrayList()
        listType.add("Trọng lượng cơ thể")
        listType.add("Cardio")
        listType.add("Kháng Lực")
        listType.add("Giãn Cơ")
    }
    private fun initListTime(){
        listTime = ArrayList()
        listTime.add("< 30")
        listTime.add("30")
        listTime.add("40")
        listTime.add("45")
        listTime.add("60")
        listTime.add("90")
    }
    private fun initListEquipment(){
        listEquipment = ArrayList()
        listEquipment.add("Tạ Đơn")
        listEquipment.add("Tạ Đòn")
        listEquipment.add("Máy Và Dây Cáp")
        listEquipment.add("Dây Kháng Lực")
        listEquipment.add("Không Có")
    }
    private fun initListBodyPart(){
        listBodyPart = ArrayList()
        listBodyPart.add("Tay")
        listBodyPart.add("Ngực")
        listBodyPart.add("Vai")
        listBodyPart.add("Lưng")
        listBodyPart.add("Chân")
        listBodyPart.add("Mông")
        listBodyPart.add("Abs & Core")
        listBodyPart.add("Thân Trên")
        listBodyPart.add("Thân Dưới")
        listBodyPart.add("Toàn Thân")
    }
    private fun setUpTypeRecyclerView(){
        viewBinding.recyclerViewType.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.recyclerViewType.layoutManager = layout
        adapterType = AdapterForTypeWorkout(listType)
        viewBinding.recyclerViewType.adapter = adapterType
    }
    private fun setUpTimeRecyclerView(){
        viewBinding.recyclerViewDuration.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.recyclerViewDuration.layoutManager = layout
        adapterTime = AdapterForDurationWorkout(listTime)
        viewBinding.recyclerViewDuration.adapter = adapterTime
    }
    private fun setUpEquipmentRecyclerView(){
        viewBinding.recyclerViewEquipment.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.recyclerViewEquipment.layoutManager = layout
        adapterEquipment = AdapterForEquipmentWorkout(listEquipment)
        viewBinding.recyclerViewEquipment.adapter = adapterEquipment
    }
    private fun setUpBodyPartRecyclerView(){
        viewBinding.recyclerViewBodyPart.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.recyclerViewBodyPart.layoutManager = layout
        adapterBodyPart = AdapterForBodyPartWorkout(listBodyPart)
        viewBinding.recyclerViewBodyPart.adapter = adapterBodyPart
    }
    private fun onChangedToMenuExercise(){
        val menuExercise = MenuBodyPartExercise()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.layout_main_activity, menuExercise)
        fragmentTrans.addToBackStack(menuExercise.tag)
        fragmentTrans.commit()
    }
}