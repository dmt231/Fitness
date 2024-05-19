package com.example.fitness.setting.broadcast

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fitness.R
import com.example.fitness.splash.SplashScreen
import com.example.fitness.storage.Preferences
import java.util.*
import kotlin.random.Random


class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val preferences = Preferences(context)
        if (preferences.getPermissionNotification()) {
            val hour = intent?.getIntExtra("Hour", 17)
            val minute = intent?.getIntExtra("Minute", 0)
            Log.d("Hour", hour.toString())
            Log.d("Minute", minute.toString())
            //Reset Alarm

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val newIntent = Intent(context, ReminderBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                newIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0)
            )
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour!!)
                set(Calendar.MINUTE, minute!!)
                set(Calendar.SECOND, 0)
                add(Calendar.DAY_OF_MONTH, 1)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
            sendNotification(context)
        }
    }
    private fun sendNotification(context: Context){
        val notificationIntent = Intent(context, SplashScreen::class.java)
        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            TODO("VERSION.SDK_INT < M")
        }


        val notificationId = Random.nextInt()
        val channelId = "fitness_notification"
        val channelDescription = "Notification For Practice Workout"

        //Create Layout For Notification
        val notificationLayout = RemoteViews(context.packageName, R.layout.layout_notification)
        notificationLayout.setTextViewText(R.id.NameNotification, "Đến Giờ Tập Luyện Rồi !")
        notificationLayout.setTextViewText(
            R.id.NotificationMessage,
            "Hãy Bắt Đầu Buổi Tập Của Bạn Ngay Bây Giờ"
        )

        val builder = NotificationCompat.Builder(context, channelId)
        builder.setSmallIcon(R.drawable.notification)
        builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
        builder.setCustomContentView(notificationLayout)
        builder.setContentIntent(pendingIntent)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setAutoCancel(false)

        //Create Channel For Notification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelDescription,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManagerCompat.notify(notificationId, builder.build())
    }
}
