package com.example.fitness.authentication.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutTextWelcomeBinding


class FirsTextFragment : Fragment() {
    private lateinit var viewBinding: LayoutTextWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutTextWelcomeBinding.inflate(inflater, container, false)
        setText()
        return viewBinding.root
    }
    private fun setText(){
        val string = requireContext().getString(R.string.firstText)
        viewBinding.textWelcome.text = string
    }
}