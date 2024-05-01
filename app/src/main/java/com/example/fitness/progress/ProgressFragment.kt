package com.example.fitness.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.databinding.LayoutProgressBinding

class ProgressFragment : Fragment() {
    private lateinit var viewBinding : LayoutProgressBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutProgressBinding.inflate(inflater, container, false)
        return viewBinding.root
    }
}