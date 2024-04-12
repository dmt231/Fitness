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
        return viewBinding.root
    }
    private fun onBack(){
        requireActivity().supportFragmentManager.popBackStack()
    }
}