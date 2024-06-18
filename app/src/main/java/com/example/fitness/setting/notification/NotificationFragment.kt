package com.example.fitness.setting.notification

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fitness.databinding.LayoutSettingNotificationBinding
import com.example.fitness.setting.broadcast.ReminderBroadcastReceiver
import com.example.fitness.storage.Preferences
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class NotificationFragment : Fragment() {
    private lateinit var viewBinding: LayoutSettingNotificationBinding
    private lateinit var preferences: Preferences
    private val requestPermission = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutSettingNotificationBinding.inflate(inflater, container, false)
        preferences = Preferences(requireContext())
        getStateNotification()
        getValueTime()
        setUpSwitchButton()
        viewBinding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // Kiểm tra quyền cho Android 13 trở lên
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            requestPermission
                        )
                    } else {
                        savePermissionToPreferences(true)
                        
                    }
                } else {
                    // Hiển thị dialog yêu cầu quyền trên các phiên bản Android thấp hơn 13
                    showPermissionDialog()
                }
            } else {
                savePermissionToPreferences(false)
            }
        }
        viewBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewBinding.layoutPickTime.setOnClickListener {
            showTimePickerDialog()
        }

        return viewBinding.root
    }

    private fun getValueTime() {
        viewBinding.valueTimeReminder.text = preferences.getTimeNotification()
    }

    private fun getStateNotification(): Boolean {
        return preferences.getPermissionNotification()
    }

    private fun setUpSwitchButton() {
        viewBinding.switchButton.isChecked = preferences.getPermissionNotification()
    }

    private fun showPermissionDialog() {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Yêu Cầu Quyền Truy Cập")
            .setMessage("Cho phép ứng dụng được quyền gửi thông báo?")
            .setPositiveButton("Cho Phép") { dialogInterface, _ ->
                savePermissionToPreferences(true)
                dialogInterface.cancel()
            }
            .setNegativeButton("Từ Chối") { dialogInterface, _ ->
                savePermissionToPreferences(false)
                viewBinding.switchButton.isChecked = false
                dialogInterface.cancel()
            }
        dialog.show()
    }

    private fun savePermissionToPreferences(state: Boolean) {
        preferences.putPermissionNotification(state)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestPermission) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                savePermissionToPreferences(true)
            }
            else if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED){
                savePermissionToPreferences(false)
                viewBinding.switchButton.isChecked = false
            }
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _: TimePicker?, hourOfDay: Int, minute: Int ->
                var minuteString = minute.toString()
                if(minute == 0){
                    minuteString = "00"
                }else if(minute < 10){
                    minuteString = "0$minute"
                }
                preferences.putTimeNotification(hourOfDay.toString(), minuteString)
                setReminderAlarm(hourOfDay, minute)
                viewBinding.valueTimeReminder.text = preferences.getTimeNotification()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }


    private fun setReminderAlarm(hour: Int, minute: Int) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), ReminderBroadcastReceiver::class.java)
        intent.putExtra("Hour", hour)
        intent.putExtra("Minute",minute)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0)
        )
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
            Log.d("NotificationFragment", "Alarm set successfully")
        } catch (e: Exception) {
            Log.e("NotificationFragment", "Failed to set alarm", e)
        }
    }

}
