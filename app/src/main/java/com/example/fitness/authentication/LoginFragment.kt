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
        return viewBinding.root
    }
    private fun onBack(){
        requireActivity().supportFragmentManager.popBackStack()
    }
}