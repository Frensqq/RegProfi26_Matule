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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.netlibrary.network.NetworkMonitor
import com.example.regprofi26_matule.Domain.Repository.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.Navigation
import com.example.regprofi26_matule.Data.Notification.NotificationReceiver
import com.example.regprofi26_matule.Presentation.ViewModels.AppViewModel
import com.example.uikit.UI.MatuleTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()

        requestNotificationPermission()

        setContent {
            MatuleTheme {

                val isOnline by
                viewModel.isOnline.collectAsState()

                Navigation(isOnline)
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



    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            viewModel.onAppStopped()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.onAppStarted()

    }

}