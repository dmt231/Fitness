package com.example.fitness.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.authentication.LoginSignUpActivity
import com.example.fitness.databinding.LayoutSettingBinding
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : Fragment() {
    private lateinit var viewBinding : LayoutSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutSettingBinding.inflate(inflater, container, false)
        viewBinding.layoutLogOut.setOnClickListener {
            logOut()
        }
        return viewBinding.root
    }

    private fun logOut() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(requireContext(), LoginSignUpActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}