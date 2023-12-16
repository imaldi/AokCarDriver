package com.surelabsid.mrjempootdriver.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager

import android.R
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Notification

import android.app.PendingIntent
import android.content.Context

import android.media.RingtoneManager

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.MultiAutoCompleteTextView
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.JsonObject
import com.surelabsid.mrjempootdriver.service.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.service.modelresponse.ResponseNotification
import com.surelabsid.mrjempootdriver.ui.MainActivity
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.ChatCSActivity
import com.surelabsid.mrjempootdriver.ui.ewallet.RiwayatPenarikanActivity
import com.surelabsid.mrjempootdriver.ui.topup.TopUpActivity
import org.json.JSONArray
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("onNewToken Driver: ", token)
    }

    //    fU8z1mN0RJG7GGPBh-gkZt:APA91bEpG4taB06W2j-DL5u_IV-t7PAgCEIiW9JfxPfRarq-u03UJMSdKei-wHFoDgUnCZCBiHAYuE71IYAQBBqi5J1gkIYYqxDNbw7m6qcpBH0FlF9Xx0To5JMscmfYt9GDASTJ1pV-
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

         val responseNotif = buildResponseNotif(p0)

        val message = p0.toString()
        Log.d("TAG", "onMessageReceived: $message")

//        val bundle = Bundle().apply {
//            putSerializable("item_notification", p0.data.toString())
//        }

        if (isAppInForeground(this)) {
            val bundle = Bundle().apply {
                putSerializable("item_notification", p0.data.toString())
            }

            val intent = Intent("myFunction")
            intent.putExtra("data", responseNotif)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

        } else {
            if (responseNotif.type == "chatcs") {
                val intent = Intent(this, ChatCSActivity::class.java)
                intent.putExtra("data", responseNotif)
                ringingNotification(
                    responseNotif.title ?: "Title",
                    responseNotif.body ?: "Body",
                    intent
                )
            }
        }

        if (responseNotif.type == "transaksi") {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("data", responseNotif)
            ringingNotification(responseNotif.title ?: "Title", responseNotif.body ?: "Body" , intent)
        } else if (responseNotif.type == "withdraw") {
            val intent = Intent(this, RiwayatPenarikanActivity::class.java)
            intent.putExtra("data", responseNotif)
            ringingNotification(responseNotif.title ?: "Title", responseNotif.body ?: "Body" , intent)
        } else if (responseNotif.type == "topup") {
            val intent = Intent(this, TopUpActivity::class.java)
            intent.putExtra("data", responseNotif)
            ringingNotification(responseNotif.title ?: "Title", responseNotif.body ?: "Body" , intent)
        }
//        else if (responseNotif.type == "chatcs") {
//            val intent = Intent(this, ChatCSActivity::class.java)
//            intent.putExtra("data", responseNotif)
//            ringingNotification(responseNotif.title ?: "Title", responseNotif.body ?: "Body" , intent)
//        }
//        else {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("data", responseNotif)
//            ringingNotification(responseNotif.title ?: "Title", responseNotif.body ?: "Body" , intent)
//        }

    }

    private fun buildResponseNotif(remoteMessage: RemoteMessage): ResponseNotification {
        val responseNotif = ResponseNotification()

        var jsonArray = JSONArray()
        var jsonObject = JSONObject()

//        if (remoteMessage.data.get("data") != null) {
//            jsonArray = JSONArray(remoteMessage.data.get("data"))
//            jsonObject = jsonArray.getJSONObject(0)
//        }

        with(responseNotif) {
            title = remoteMessage.data.get("title")
            body = remoteMessage.data.get("body")
            type = remoteMessage.data.get("type")
//            if (remoteMessage.data.get("data") != null) {
//                if (type == "transaksi") {
////                    serviceId = jsonObject.getString("service_id")
//                }
//            }
        }

        return responseNotif
    }

    @SuppressLint("WrongConstant")
    private fun ringingNotification(title: String, messageBody: String, intent: Intent) {
        val sound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val pendingintent = PendingIntent.getActivity(this, 0,
            intent, PendingIntent.FLAG_IMMUTABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationOreo = NotificationOreo(this)
            val builder: Notification.Builder =
                notificationOreo.getOreoNotification(title, messageBody, pendingintent, sound, "")
            notificationOreo.manager?.notify(0, builder.build())
        } else {
            val notif = Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.ic_menu_month)
                .setSound(sound)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingintent)
            val i = 0
            val notif12 = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notif12.notify(i, notif.build())
        }
    }

    fun isAppInForeground(context: Context): Boolean {
        val task = (context.getSystemService(ACTIVITY_SERVICE) as ActivityManager)
            .getRunningTasks(1)
        return if (task.isEmpty()) {
            // app is in background
            false
        } else task[0].topActivity
            ?.packageName
            .equals(context.packageName, ignoreCase = true)
    }


}