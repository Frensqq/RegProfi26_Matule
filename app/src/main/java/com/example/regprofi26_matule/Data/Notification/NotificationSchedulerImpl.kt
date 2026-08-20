package com.example.regprofi26_matule.Data.Notification


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.regprofi26_matule.Domain.Notification.NotificationScheduler

class NotificationSchedulerImpl(
    context: Context
) : NotificationScheduler {

    private val appContext = context.applicationContext

    private val alarmManager =
        appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun getPendingIntent(): PendingIntent {

        val intent = Intent(
            appContext,
            NotificationReceiver::class.java
        )

        return PendingIntent.getBroadcast(
            appContext,
            100,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun schedule() {

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 3 * 60 * 1000L,
            getPendingIntent()
        )
    }

    override fun cancel() {
        alarmManager.cancel(
            getPendingIntent()
        )
    }
}