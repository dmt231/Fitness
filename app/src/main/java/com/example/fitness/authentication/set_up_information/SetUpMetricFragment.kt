package com.example.fitness.authentication.set_up_information

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutSetUpMetricBinding
import com.example.fitness.storage.Preferences

class SetUpMetricFragment : Fragment() {
    private lateinit var viewBinding : LayoutSetUpMetricBinding
    private var metric : String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutSetUpMetricBinding.inflate(inflater, container, false)
        setUpAnimation()
        viewBinding.radioKg.setOnClickListener {
            onSelectedMetric()
        }
        viewBinding.radioLb.setOnClickListener {
            onSelectedMetric()
        }
        viewBinding.next.setOnClickListener {
            saveMetric()
            goToNextStep()
        }
        viewBinding.back.setOnClickListener {
            onBack()
        }
        viewBinding.layoutMetric.setOnClickListener {

        }
        return viewBinding.root
    }
    private fun goToNextStep() {
        val setUpBirthday = SetUpBirthDayFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layoutLoginSignUp, setUpBirthday)
        fragmentTrans.addToBackStack(setUpBirthday.tag)
        fragmentTrans.commit()
    }

    private fun saveMetric() {
        val preferences = Preferences(requireContext())
        preferences.putUserMetricValues(metric!!)
    }
    private fun setUpAnimation(){
        viewBinding.txtSelectHeightWeight.alpha = 0F
        viewBinding.txtSelectHeightWeight.animate().alpha(1F).setDuration(800).start()

        viewBinding.txtDescription.alpha = 0F
        viewBinding.txtDescription.animate().alpha(1F).setDuration(800).start()

        viewBinding.status.alpha = 0F
        viewBinding.status.animate().alpha(1F).setDuration(800).start()

        viewBinding.next.alpha = 0F
        viewBinding.next.animate().alpha(1F).setDuration(800).start()

        viewBinding.back.alpha = 0F
        viewBinding.back.animate().alpha(1F).setDuration(800).start()

        viewBinding.imageKG.alpha = 0F
        viewBinding.imageKG.animate().alpha(1F).setDuration(800).start()

        viewBinding.imageLb.alpha = 0F
        viewBinding.imageLb.animate().alpha(1F).setDuration(800).start()


        viewBinding.radioGroup.translationX = -400F
        viewBinding.radioGroup.alpha = 0F
        viewBinding.radioGroup.animate().alpha(1F).translationX(0F).setDuration(800).start()
    }
    private fun onSelectedMetric(){
        if(viewBinding.radioKg.isChecked){
            metric = requireContext().getString(R.string.kg)
            Log.d("metric" , metric!!)
        }
        else if(viewBinding.radioLb.isChecked){
            metric = requireContext().getString(R.string.lb)
            Log.d("metric" , metric!!)
        }
    }
    private fun onBack(){
        requireActivity().supportFragmentManager.popBackStack()
    }
}