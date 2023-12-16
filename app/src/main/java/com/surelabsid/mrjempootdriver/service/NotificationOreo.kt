package com.surelabsid.mrjempootdriver.service

import android.app.PendingIntent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.app.NotificationChannel
import android.annotation.TargetApi
import android.app.Notification
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import com.surelabsid.mrjempootdriver.R


class NotificationOreo(base: Context?) : ContextWrapper(base) {
    private var notificationManager: NotificationManager? = null

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.enableLights(false)
        channel.enableVibration(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        manager!!.createNotificationChannel(channel)
    }

    val manager: NotificationManager?
        get() {
            if (notificationManager == null) {
                notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return notificationManager
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getOreoNotification(
        title: String?,
        body: String?,
        pendingIntent: PendingIntent?,
        sound: Uri?,
        icon: String?
    ): Notification.Builder {
        return Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setContentText(body)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_arrow_down)
            .setSound(sound)
            .setAutoCancel(true)
    }

    companion object {
        private const val CHANNEL_ID = "com.riderunner.firebaseseaa"
        private const val CHANNEL_NAME = "firebaseseaa"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }
}

