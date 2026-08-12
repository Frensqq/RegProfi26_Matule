package com.example.regprofi26_matule

import android.Manifest
import android.app.AlarmManager
import android.app.AlarmManager.*
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.netlibrary.network.NetworkMonitor
import com.example.regprofi26_matule.DI.networkModule
import com.example.regprofi26_matule.Domain.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.Navigation
import com.example.regprofi26_matule.Presentation.Notification.NotificationReceiver
import com.example.regprofi26_matule.ui.theme.RegProfi26_MatuleTheme
import com.example.uikit.UI.MatuleTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.jvm.java

class MainActivity : ComponentActivity() {

    val isOnline = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkModuleMonitor = NetworkMonitor(this)
        isOnline.value = networkModuleMonitor.isConnected()

        startKoin {
            androidContext(this@MainActivity)
            modules(networkModule)
        }

        UserRepository.init(this)

        enableEdgeToEdge()

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

        setContent {
            MatuleTheme {
                Navigation(isOnline.value)
            }
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
            RTC_WAKEUP,
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

