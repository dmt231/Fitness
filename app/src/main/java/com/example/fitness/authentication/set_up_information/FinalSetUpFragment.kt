package com.example.fitness.authentication.set_up_information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.fitness.MainActivity
import com.example.fitness.R
import com.example.fitness.databinding.LayoutFinishSetUpBinding
import com.example.fitness.databinding.LayoutSetUpMetricBinding
import com.example.fitness.repository.UserRepository
import com.example.fitness.storage.Preferences

class FinalSetUpFragment : Fragment() {
    private lateinit var viewBinding : LayoutFinishSetUpBinding
    private lateinit var preferences: Preferences
    private lateinit var userRepository: UserRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutFinishSetUpBinding.inflate(inflater, container, false)
        preferences = Preferences(requireContext())
        userRepository = UserRepository()
        setUpAnimation()
        viewBinding.btnGetStarted.setOnClickListener {
            saveData()
            changedToMainScreen()
        }
        viewBinding.layoutFinish.setOnClickListener {

        }
        return viewBinding.root
    }
    private fun saveData(){
        preferences = Preferences(requireContext())
        val userId = preferences.getUserIdValues()
        val gender = preferences.getGenderValues()
        val metric = preferences.getMetricValues()
        val birthday = preferences.getBirthdayValues()
        val weight = preferences.getWeightValues()
        val height = preferences.getHeightValues()
        val bmi = preferences.getBMIValues()
        userRepository.updateDetailInfoForUser(gender!!,birthday!!, metric!!,weight!!,height!!,bmi!!,userId!!,"","","")

    }
    private fun changedToMainScreen(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun setUpAnimation(){
        viewBinding.txtSelectFinish.alpha = 0F
        viewBinding.txtSelectFinish.animate().alpha(1F).setDuration(800).start()

        viewBinding.txtDescription.alpha = 0F
        viewBinding.txtDescription.animate().alpha(1F).setDuration(800).start()

        viewBinding.btnGetStarted.translationY = 400F
        viewBinding.btnGetStarted.alpha = 0F
        viewBinding.btnGetStarted.animate().alpha(1F).translationY(0F).translationX(0F).setDuration(800).start()
    }
    private fun onBack(){
        requireActivity().supportFragmentManager.popBackStack()
    }
}