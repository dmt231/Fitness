package com.example.fitness.authentication.set_up_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutSetUpHeightWeightBinding
import com.example.fitness.storage.Preferences

class SetUpWeightHeightFragment : Fragment() {
    private lateinit var viewBinding : LayoutSetUpHeightWeightBinding
    private var height : String = ""
    private var weight : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutSetUpHeightWeightBinding.inflate(inflater, container, false)
        setUpAnimation()
        onSelectedHeightWeight()
        setUpHeightWeightPicker()
        viewBinding.next.setOnClickListener {
            //saveHeightWeight()
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
        fragmentTrans.commit()
    }

    private fun saveHeightWeight() {
        val preferences = Preferences(requireContext())
        preferences.putHeightValues(height)
        preferences.putWeightValues(weight)
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