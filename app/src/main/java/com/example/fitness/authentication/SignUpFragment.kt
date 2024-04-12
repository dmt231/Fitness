package com.example.fitness.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.databinding.LayoutSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var viewBinding : LayoutSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutSignUpBinding.inflate(inflater, container, false)
        viewBinding.btnBack.setOnClickListener {
            onBack()
        }
        setUpAnimation()
        return viewBinding.root
    }
    private fun onBack(){
        requireActivity().supportFragmentManager.popBackStack()
    }
    private fun setUpAnimation(){
        viewBinding.cardViewSignUp.alpha = 0F
        viewBinding.cardViewSignUp.animate().alpha(1F).setDuration(500).start()

        viewBinding.firstName.alpha = 0F
        viewBinding.firstName.translationX = -200F
        viewBinding.firstName.animate().translationX(0F).alpha(1F).setDuration(800).start()

        viewBinding.lastName.alpha = 0F
        viewBinding.lastName.translationX = -200F
        viewBinding.lastName.animate().translationX(0F).alpha(1F).setDuration(900).start()

        viewBinding.emailSignUp.alpha = 0F
        viewBinding.emailSignUp.translationX = -200F
        viewBinding.emailSignUp.animate().translationX(0F).alpha(1F).setDuration(1000).start()

        viewBinding.passwordSignUp.alpha = 0F
        viewBinding.passwordSignUp.translationX = -200F
        viewBinding.passwordSignUp.animate().translationX(0F).alpha(1F).setDuration(1100).start()

        viewBinding.buttonSignUp.alpha = 0F
        viewBinding.buttonSignUp.translationX = -200F
        viewBinding.buttonSignUp.animate().translationX(0F).alpha(1F).setDuration(1200).start()
    }
}