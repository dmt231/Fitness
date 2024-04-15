package com.example.fitness.authentication

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.authentication.set_up_information.SetUpGenderFragment
import com.example.fitness.databinding.LayoutSignUpBinding
import com.example.fitness.repository.UserRepository
import com.example.fitness.storage.Preferences

class SignUpFragment : Fragment() {
    private lateinit var viewBinding: LayoutSignUpBinding
    private lateinit var userRepository: UserRepository
    private var userIdAuth: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutSignUpBinding.inflate(inflater, container, false)
        userRepository = UserRepository()
        viewBinding.btnBack.setOnClickListener {
            onBack()
        }
        viewBinding.buttonSignUp.setOnClickListener {
            if (validateInput()) {
                viewBinding.progressBarSignUp.visibility = View.VISIBLE
                viewBinding.buttonSignUp.visibility = View.INVISIBLE
                userRepository.createAuthUser(
                    viewBinding.emailSignUp.text.toString(),
                    viewBinding.passwordLoginInput.text.toString(),
                    viewBinding.firstName.text.toString(),
                    viewBinding.lastName.text.toString(),
                    requireActivity(),
                    object : UserRepository.OnCompleteListener{
                        override fun onSuccessListener(userId: String) {
                            viewBinding.progressBarSignUp.visibility = View.GONE
                            userIdAuth = userId
                            saveUserId()
                            onChangedToSetUpGender()
                        }

                        override fun onFailedListener() {
                            viewBinding.progressBarSignUp.visibility = View.GONE
                            viewBinding.buttonSignUp.visibility = View.VISIBLE
                        }
                    }
                )
            }
        }
        setUpAnimation()
        return viewBinding.root
    }
    private fun saveUserId() {
        val preferences = Preferences(requireContext())
        preferences.putUserIdValues(userIdAuth)
    }
    private fun onBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun setUpAnimation() {
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

    private fun validateInput(): Boolean {
        var check = true
        if (viewBinding.firstName.text.isEmpty()) {
            viewBinding.firstName.error = "Please Enter Your Name"
            check = false
        } else if (viewBinding.passwordLoginInput.text.toString().isEmpty()) {
            viewBinding.passwordLoginInput.error = "Please Enter Your Password"
            check = false
        } else if (viewBinding.passwordLoginInput.text.toString().length < 8 || !viewBinding.passwordLoginInput.text.toString()
                .matches(".*[A-Z].*".toRegex())
        ) {
            viewBinding.passwordLoginInput.error =
                "your password must be at least 8 characters long and contain an uppercase letter"
            check = false
        } else if (viewBinding.emailSignUp.text.toString().isEmpty()) {
            viewBinding.emailSignUp.error = "Please Enter Your Email"
            check = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(viewBinding.emailSignUp.text).matches()) {
            viewBinding.emailSignUp.error = "Your email is invalid"
            check = false
        }
        return check
    }
    private fun onChangedToSetUpGender(){
        val setUpGender = SetUpGenderFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layoutLoginSignUp, setUpGender)
        fragmentTrans.addToBackStack(setUpGender.tag)
        fragmentTrans.commit()
    }
}