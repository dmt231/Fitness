package com.example.fitness.authentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fitness.MainActivity
import com.example.fitness.databinding.LayoutLoginBinding
import com.example.fitness.repository.UserRepository
import com.example.fitness.storage.Preferences
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var viewBinding : LayoutLoginBinding
    private lateinit var userRepository: UserRepository
    private lateinit var preferences: Preferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutLoginBinding.inflate(inflater, container, false)
        userRepository = UserRepository()
        preferences = Preferences(requireContext())
        viewBinding.btnBack.setOnClickListener {
            onBack()
        }
        viewBinding.layoutMainLogin.setOnClickListener {
            //DO NOTHING.
        }
        viewBinding.buttonLogin.setOnClickListener {
            viewBinding.progressBarSignIn.visibility = View.VISIBLE
            viewBinding.buttonLogin.visibility = View.INVISIBLE
            if(validateInput()){
                userRepository.loginUser(viewBinding.emailLogin.text.toString(), viewBinding.passwordLoginInput.text.toString(), requireActivity(),
                object : UserRepository.OnCompleteListener{
                    override fun onSuccessListener(userId: String) {
                        preferences.putUserIdValues(userId)
                        changedToMainScreen()
                    }

                    override fun onFailedListener() {
                        viewBinding.progressBarSignIn.visibility = View.INVISIBLE
                        viewBinding.buttonLogin.visibility = View.VISIBLE
                    }

                })
            }
        }
        viewBinding.txtForgotPassword.setOnClickListener {
            resetPassword()
        }
        setUpAnimation()
        return viewBinding.root
    }

    private fun resetPassword() {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(viewBinding.emailLogin.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Vui lòng kiểm tra email", Toast.LENGTH_SHORT).show()
                }
            }
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
    private fun validateInput() : Boolean{
        var check = true
        if (viewBinding.emailLogin.text.isEmpty()) {
            viewBinding.emailLogin.error = "Please Enter Your Name"
            check = false
        } else if (viewBinding.passwordLoginInput.text.toString().isEmpty()) {
            viewBinding.passwordLoginInput.error = "Please Enter Your Password"
            check = false
        }
        return check
    }
    private fun changedToMainScreen(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}