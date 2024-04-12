package com.example.fitness.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.databinding.LayoutLoginBinding

class LoginFragment : Fragment() {
    private lateinit var viewBinding : LayoutLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutLoginBinding.inflate(inflater, container, false)
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
        viewBinding.cardViewLogin.alpha = 0F
        viewBinding.cardViewLogin.animate().alpha(1F).setDuration(600).start()

        viewBinding.emailLogin.alpha = 0F
        viewBinding.emailLogin.translationX = -200F
        viewBinding.emailLogin.animate().translationX(0F).alpha(1F).setDuration(800).start()

        viewBinding.passwordLogin.alpha = 0F
        viewBinding.passwordLogin.translationX = -200F
        viewBinding.passwordLogin.animate().translationX(0F).alpha(1F).setDuration(1000).start()

        viewBinding.buttonLogin.alpha = 0F
        viewBinding.buttonLogin.translationX = -200F
        viewBinding.buttonLogin.animate().translationX(0F).alpha(1F).setDuration(1200).start()

    }
}