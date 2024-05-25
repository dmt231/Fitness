package com.example.fitness.setting


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.authentication.LoginSignUpActivity
import com.example.fitness.databinding.LayoutSettingBinding
import com.example.fitness.databinding.LayoutUpdateBmiValueBinding
import com.example.fitness.model.User
import com.example.fitness.repository.UserRepository
import com.example.fitness.setting.notification.NotificationFragment
import com.example.fitness.setting.nutrition.NutritionFragment
import com.example.fitness.storage.Preferences
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class SettingFragment : Fragment() {
    private lateinit var viewBinding : LayoutSettingBinding
    private lateinit var preferences: Preferences
    private lateinit var userRepository: UserRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutSettingBinding.inflate(inflater, container, false)
        userRepository = UserRepository()
        preferences = Preferences(requireContext())
        viewBinding.layoutLogOut.setOnClickListener {
            logOut()
        }
        viewBinding.layoutNutrition.setOnClickListener {
            changeToNutritionFragment()
        }
        viewBinding.btnEditHeight.setOnClickListener {
            openDialogValue(preferences.getUserIdValues()!!, "height")
        }
        viewBinding.btnEditWeight.setOnClickListener {
            openDialogValue(preferences.getUserIdValues()!!, "weight")
        }
        viewBinding.btnDetailNotification.setOnClickListener {
            moveToDetailNotificationFragment()
        }
        getData(preferences.getUserIdValues()!!)
        return viewBinding.root
    }
    private fun moveToDetailNotificationFragment() {
        val notificationFragment = NotificationFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.mainLayout, notificationFragment)
        fragmentTrans.addToBackStack(notificationFragment.tag)
        fragmentTrans.commit()
    }



    private fun changeToNutritionFragment() {
        val nutritionFragment = NutritionFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layout_main_activity, nutritionFragment)
        fragmentTrans.addToBackStack(nutritionFragment.tag)
        fragmentTrans.commit()
    }

    private fun getData(userId : String) {
        userRepository.getInformationForUser(userId, object : UserRepository.OnQuerySuccessUser{
            override fun onQueryInfoUSerSuccess(user: User) {
                bindingData(user)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindingData(user : User) {
        viewBinding.nameUser.text = user.firstName + user.lastName
        viewBinding.emailUser.text = user.email
        viewBinding.valueGender.text = user.gender
        viewBinding.valueBirthday.text = user.birthday
        viewBinding.valueHeight.text = user.height
        viewBinding.valueWeight.text = user.weight
        viewBinding.valueBmi.text = user.bmi
        viewBinding.valueMeasure.text = user.measure
        handleBmiData(user.bmi.toFloat())
    }
    @SuppressLint("SetTextI18n")
    private fun handleBmiData(bmiValue : Float){
        if(bmiValue < 18.5){
            viewBinding.stateValue.text = "Gầy"
            viewBinding.stateValue.setTextColor(Color.BLUE)
        }else if(bmiValue in 18.5..24.9){
            viewBinding.stateValue.text = "Bình Thường"
            viewBinding.stateValue.setTextColor(Color.GREEN)
        }else if(bmiValue in 25.0..29.9){
            viewBinding.stateValue.text = "Thừa Cân"
            viewBinding.stateValue.setTextColor(Color.YELLOW)
        } else {
            viewBinding.stateValue.text = "Béo Phì"
            viewBinding.stateValue.setTextColor(Color.RED)
        }
    }
    private fun openDialogValue(userId: String, type : String){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = LayoutUpdateBmiValueBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.txtForUpdate.text = type
        binding.btnYes.setOnClickListener {
            when(type){
                "height" -> {
                    viewBinding.valueHeight.text = binding.valueUpdate.text.toString()
                    val resultBmi = viewBinding.valueWeight.text.toString().toFloat() / (((binding.valueUpdate.text.toString().toInt()) / 100F) * ((binding.valueUpdate.text.toString().toInt() / 100F)))
                    val formattedBmi = String.format(Locale.getDefault(), "%.2f", resultBmi)
                    viewBinding.valueBmi.text = formattedBmi
                    userRepository.updateHeightValueForUser(viewBinding.valueHeight.text.toString(), viewBinding.valueBmi.text.toString(), userId)
                }
                "weight" ->{
                    viewBinding.valueWeight.text = binding.valueUpdate.text.toString()
                    val resultBmi = viewBinding.valueWeight.text.toString().toFloat() / (((viewBinding.valueHeight.text.toString().toInt()) / 100F) * (( (viewBinding.valueHeight.text.toString().toInt()) / 100F)))
                    val formattedBmi = String.format(Locale.getDefault(), "%.2f", resultBmi)
                    viewBinding.valueBmi.text = formattedBmi
                    userRepository.updateWeightValueForUser(viewBinding.valueWeight.text.toString(), viewBinding.valueBmi.text.toString(),userId)
                }
            }
            dialog.cancel()
        }
        binding.btnNo.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }
    private fun logOut() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        preferences.deleteAllInformation()
        val intent = Intent(requireContext(), LoginSignUpActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}