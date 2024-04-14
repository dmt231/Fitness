package com.example.fitness.authentication.set_up_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutSetUpGenderBinding
import com.example.fitness.storage.Preferences

class SetUpGenderFragment : Fragment() {
    private lateinit var viewBinding : LayoutSetUpGenderBinding
    private var gender : String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutSetUpGenderBinding.inflate(inflater, container, false)
        setUpAnimation()
        onSelectedGender()
        viewBinding.radioFemale.setOnClickListener {
            viewBinding.imageFemaleGender.visibility = View.VISIBLE
            viewBinding.imageGender.setImageResource(R.drawable.baseline_female_24)
            viewBinding.imageGender.visibility = View.VISIBLE
            viewBinding.imageMenGender.visibility = View.INVISIBLE
        }
        viewBinding.radioMale.setOnClickListener {
            viewBinding.imageMenGender.visibility = View.VISIBLE
            viewBinding.imageFemaleGender.visibility = View.INVISIBLE
            viewBinding.imageGender.setImageResource(R.drawable.baseline_male_24)
            viewBinding.imageGender.visibility = View.VISIBLE
        }
        viewBinding.next.setOnClickListener {
            //saveGender()
            goToNextStep()
        }
        return viewBinding.root
    }

    private fun goToNextStep() {
        val setUpMetricFragment = SetUpMetricFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layoutLoginSignUp, setUpMetricFragment)
        fragmentTrans.commit()
    }

    private fun saveGender() {
        val preferences = Preferences(requireContext())
        preferences.putUserGenderValues(gender!!)
    }
    private fun setUpAnimation(){
        viewBinding.txtSelectGender.alpha = 0F
        viewBinding.txtSelectGender.animate().alpha(1F).setDuration(800).start()

        viewBinding.status.alpha = 0F
        viewBinding.status.animate().alpha(1F).setDuration(800).start()

        viewBinding.next.alpha = 0F
        viewBinding.next.animate().alpha(1F).setDuration(800).start()

        viewBinding.radioGroup.translationY = -200F
        viewBinding.radioGroup.alpha = 0F
        viewBinding.radioGroup.animate().alpha(1F).translationY(0F).setDuration(800).start()
    }
    private fun onSelectedGender(){
        if(viewBinding.radioFemale.isSelected){
            gender = requireContext().getString(R.string.female)
        }
        else if(viewBinding.radioMale.isSelected){
            gender = requireContext().getString(R.string.nam)
        }
    }
}