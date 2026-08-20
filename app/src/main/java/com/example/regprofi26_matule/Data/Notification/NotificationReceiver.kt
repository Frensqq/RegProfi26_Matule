package com.example.regprofi26_matule.Data.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.uikit.R

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        val channel = NotificationChannel(
            "return_channel",
            "Напоминания",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(
            context,
            "return_channel"
        )
            .setSmallIcon(R.drawable.shopping_cart)
            .setContentTitle("Matule")
            .setContentText("Возвращайтесь скорее!")
            .setAutoCancel(true)
            .build()

        manager.notify(1, notification)
    }
}