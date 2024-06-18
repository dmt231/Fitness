package com.example.fitness.create.create_plan

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.R
import com.example.fitness.adapter_recyclerView.adapter_plan.AdapterListPersonalPlan
import com.example.fitness.adapter_recyclerView.adapter_workout.AdapterListPersonalWorkout
import com.example.fitness.create.model.PersonalPlan
import com.example.fitness.create.model.PersonalWorkout
import com.example.fitness.databinding.LayoutDialogDeletePlanBinding
import com.example.fitness.databinding.LayoutDialogDeleteWorkoutBinding
import com.example.fitness.databinding.LayoutPersonalPlanFragmentBinding
import com.example.fitness.model.Plan
import com.example.fitness.repository.PlanRepository
import com.example.fitness.storage.Preferences
import com.example.fitness.swipe.SwipeToDeleteCallBack

class PersonalPlanFragment : Fragment() {
    private lateinit var viewBinding : LayoutPersonalPlanFragmentBinding
    private lateinit var planRepository: PlanRepository
    private lateinit var preferences: Preferences
    private lateinit var listPlanForResult : ArrayList<PersonalPlan>
    private lateinit var adapter : AdapterListPersonalPlan
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutPersonalPlanFragmentBinding.inflate(inflater, container, false)
        planRepository = PlanRepository()
        preferences = Preferences(requireContext())
        getData(preferences.getUserIdValues().toString())
        listPlanForResult = ArrayList()
        viewBinding.btnCreatePlan.setOnClickListener {
            changeToCreateNamePlan()
        }
        viewBinding.btnCreate.setOnClickListener{
            changeToCreateNamePlan()
        }
        setUpRecyclerView()
        swipeToDelete()
        return viewBinding.root
    }

    private fun setUpRecyclerView() {
        viewBinding.recyclerViewMyPlan.setHasFixedSize(false)
        val layout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerViewMyPlan.layoutManager = layout
        adapter = AdapterListPersonalPlan(listPlanForResult, object : AdapterListPersonalPlan.OnDetailPlanClick{
            override fun onClick(plan: PersonalPlan) {
                changeToDetailPersonalPlan(plan)
            }
        })
        viewBinding.recyclerViewMyPlan.adapter = adapter
    }

    private fun changeToDetailPersonalPlan(plan : PersonalPlan) {
        val detailPlan = DetailPersonalPlanFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("personalPlan", plan)
        detailPlan.arguments = bundle
        fragmentTrans.add(R.id.layout_main_activity, detailPlan)
        fragmentTrans.addToBackStack(null)
        fragmentTrans.commit()
    }

    private fun changeToCreateNamePlan() {
        val createNamPlan = CreateNamePlan()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layout_main_activity, createNamPlan)
        fragmentTrans.addToBackStack(createNamPlan.tag)
        fragmentTrans.commit()
    }

    private fun getData(userId: String) {
        planRepository.getPlanByUserId(userId, object : PlanRepository.QueryListPlan{
            override fun onSuccessListener() {

            }

            override fun onNotFoundListener() {
                viewBinding.recyclerViewMyPlan.visibility = View.GONE
                viewBinding.btnCreate.visibility = View.GONE
                viewBinding.linearLayout.visibility = View.VISIBLE
            }

            override fun onFoundListPlanListener(listPlan: ArrayList<PersonalPlan>) {
                viewBinding.recyclerViewMyPlan.visibility = View.VISIBLE
                viewBinding.btnCreate.visibility = View.VISIBLE
                viewBinding.linearLayout.visibility = View.GONE
                for(plan in listPlan){
                    val position = listPlanForResult.size
                    listPlanForResult.add(plan)
                    adapter.notifyItemInserted(position)
                }
            }
        })
    }
    private fun swipeToDelete(){
        lateinit var itemTouchHelper: ItemTouchHelper
        val swipeToDeleteCallBack = object : SwipeToDeleteCallBack(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                showDialogForWorkout(position)
            }

        }
        itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerViewMyPlan)
    }
    private fun showDialogForWorkout(position : Int){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val viewDialogBinding = LayoutDialogDeletePlanBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(viewDialogBinding.root)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
        viewDialogBinding.btnNo.setOnClickListener {
            dialog.cancel()
            adapter.notifyItemChanged(position)
        }
        viewDialogBinding.btnYes.setOnClickListener {
            val planItem = listPlanForResult[position]
            planRepository.deletePlan(planItem.idPlan)
            listPlanForResult.removeAt(position)
            adapter.notifyItemRemoved(position)
            if(listPlanForResult.size == 0){
                viewBinding.recyclerViewMyPlan.visibility = View.GONE
                viewBinding.linearLayout.visibility = View.VISIBLE
            }
            dialog.cancel()
        }
        dialog.show()
    }
}