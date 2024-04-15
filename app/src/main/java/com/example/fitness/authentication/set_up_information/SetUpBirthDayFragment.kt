package com.example.fitness.authentication.set_up_information

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.fitness.R
import com.example.fitness.databinding.LayoutDialogPickDateBinding
import com.example.fitness.databinding.LayoutSetUpBirthdayBinding
import com.example.fitness.databinding.LayoutSetUpMetricBinding
import com.example.fitness.storage.Preferences
import java.util.*

class SetUpBirthDayFragment : Fragment() {
    private lateinit var viewBinding: LayoutSetUpBirthdayBinding
    private var birthDay: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutSetUpBirthdayBinding.inflate(inflater, container, false)
        setUpAnimation()
        viewBinding.next.setOnClickListener {
            saveBirthday()
            goToNextStep()
        }
        viewBinding.back.setOnClickListener {
            onBack()
        }
        viewBinding.day.setOnClickListener {
            onSelectedBirthday()
        }
        viewBinding.layoutBirthDay.setOnClickListener {

        }
        return viewBinding.root
    }

    private fun goToNextStep() {
        val setUpWeightHeightFragment = SetUpWeightHeightFragment()
        val fragmentTrans = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTrans.add(R.id.layoutLoginSignUp, setUpWeightHeightFragment)
        fragmentTrans.addToBackStack(setUpWeightHeightFragment.tag)
        fragmentTrans.commit()
    }

    private fun saveBirthday() {
        val preferences = Preferences(requireContext())
        preferences.putBirthdayValues(birthDay!!)
    }

    private fun setUpAnimation() {
        viewBinding.txtSelectBirthday.alpha = 0F
        viewBinding.txtSelectBirthday.animate().alpha(1F).setDuration(800).start()

        viewBinding.txtDescription.alpha = 0F
        viewBinding.txtDescription.animate().alpha(1F).setDuration(800).start()

        viewBinding.linearLayout.alpha = 0F
        viewBinding.linearLayout.animate().alpha(1F).setDuration(800).start()

        viewBinding.status.alpha = 0F
        viewBinding.status.animate().alpha(1F).setDuration(800).start()

        viewBinding.next.alpha = 0F
        viewBinding.next.animate().alpha(1F).setDuration(800).start()

        viewBinding.back.alpha = 0F
        viewBinding.back.animate().alpha(1F).setDuration(800).start()


    }

    private fun onSelectedBirthday() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = LayoutDialogPickDateBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        //Xác định vị trí cho dialog
        val window = dialog.window

        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        val windowAttributes = window.attributes
        windowAttributes.gravity = Gravity.CENTER
        window.attributes = windowAttributes
        binding.btnSubmit.setOnClickListener {
            val day = binding.spinnerPickDate.dayOfMonth.toString()
            val month = binding.spinnerPickDate.month + 1
            val year = binding.spinnerPickDate.year.toString()
            viewBinding.day.setText(day)
            viewBinding.month.setText(month.toString())
            viewBinding.year.setText(year)
            val calendarBirthday = Calendar.getInstance()
            calendarBirthday.set(binding.spinnerPickDate.year, binding.spinnerPickDate.month, binding.spinnerPickDate.dayOfMonth)


            val calendarToday = Calendar.getInstance()

            var age = calendarToday.get(Calendar.YEAR) - calendarBirthday.get(Calendar.YEAR)
            if (calendarToday.get(Calendar.DAY_OF_YEAR) < calendarBirthday.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
            viewBinding.txtYearOld.text = age.toString()
            birthDay = "$day/$month/$year"
            dialog.cancel()
        }
        dialog.show()
    }

    private fun onBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }
}