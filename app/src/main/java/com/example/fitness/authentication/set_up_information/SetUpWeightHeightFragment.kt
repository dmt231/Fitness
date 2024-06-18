package com.example.fitness.authentication.set_up_information

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutSetUpHeightWeightBinding
import com.example.fitness.storage.Preferences
import java.util.*

class SetUpWeightHeightFragment : Fragment() {
    private lateinit var viewBinding : LayoutSetUpHeightWeightBinding
    private var height : String = ""
    private var weight : String = ""
    private var bmi : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutSetUpHeightWeightBinding.inflate(inflater, container, false)
        setUpAnimation()
        onSelectedHeightWeight()
        setUpHeightWeightPicker()
        viewBinding.layoutHeightWeight.setOnClickListener {

        }
        viewBinding.weight.setOnValueChangedListener { numberPicker, i, i2 ->
            weight = i2.toString()
            if(viewBinding.height.value != 0) {
                val resultBmi = i2.toFloat() / (((viewBinding.height.value) / 100F) * ((viewBinding.height.value) / 100F))
                val formattedBmi = String.format(Locale.getDefault(), "%.2f", resultBmi).replace(',','.')
                bmi = formattedBmi
                Log.d("Bmi : ", bmi)
                viewBinding.BmiValue.text = bmi
            }
        }
        viewBinding.height.setOnValueChangedListener { numberPicker, i, i2 ->
            height = i2.toString()
            if(viewBinding.weight.value != 0) {
                val resultBmi = (viewBinding.weight.value) / ((i2.toFloat() / 100F) * (i2.toFloat() / 100F))
                val formattedBmi = String.format(Locale.getDefault(), "%.2f", resultBmi)
                bmi = formattedBmi
                viewBinding.BmiValue.text = bmi
                Log.d("Bmi : ", bmi)
            }
        }
        viewBinding.next.setOnClickListener {
            saveHeightWeight()
            goToNextStep()
        }
        viewBinding.back.setOnClickListener {
            onBack()
        }
        return viewBinding.root
    }

    private fun setUpHeightWeightPicker() {
        viewBinding.height.minValue = 0
        viewBinding.height.maxValue = 220
        viewBinding.weight.minValue = 0
        viewBinding.weight.maxValue = 200
    }

    private fun goToNextStep() {
        val finalSetUpFragment = FinalSetUpFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layoutLoginSignUp, finalSetUpFragment)
        fragmentTrans.addToBackStack(finalSetUpFragment.tag)
        fragmentTrans.commit()
    }

    private fun saveHeightWeight() {
        val preferences = Preferences(requireContext())
        preferences.putHeightValues(height)
        preferences.putWeightValues(weight)
        preferences.putBMIValues(bmi)
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

        viewBinding.linearLayout1.alpha = 0F
        viewBinding.linearLayout1.animate().alpha(1F).setDuration(800).start()
    }



    private fun onSelectedHeightWeight(){
        weight = viewBinding.weight.value.toString()
        height = viewBinding.height.value.toString()
    }

    private fun onBack(){
        requireActivity().supportFragmentManager.popBackStack()
    }
}