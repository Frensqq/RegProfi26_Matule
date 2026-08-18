package com.example.regprofi26_matule.Presentation.Activity

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.example.netlibrary.network.NetworkMonitor
import com.example.regprofi26_matule.Domain.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.Navigation
import com.example.regprofi26_matule.Presentation.Notification.NotificationReceiver
import com.example.uikit.UI.MatuleTheme

class MainActivity : ComponentActivity() {

    val isOnline = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkModuleMonitor = NetworkMonitor(this)
        isOnline.value = networkModuleMonitor.isConnected()

        UserRepository.init(applicationContext)  //позже уберу, как переделаю UserRepository
        enableEdgeToEdge()

        requestNotificationPermission()

        setContent {
            MatuleTheme {
                Navigation(isOnline.value)
            }
        }
    }

    private fun requestNotificationPermission(){
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
    }

    private fun scheduleNotification() {

        val alarmManager =
            getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(
            this,
            NotificationReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            100,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 3 * 60 *1000,
            pendingIntent
        )
    }

    override fun onStop() {
        super.onStop()

        if (
            !isChangingConfigurations &&
            UserRepository.Notification
        ) {
            scheduleNotification()
        }
    }

    override fun onStart() {
        super.onStart()

        cancelNotification()
    }

    private fun cancelNotification() {

        val alarmManager =
            getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(
            this,
            NotificationReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            100,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}