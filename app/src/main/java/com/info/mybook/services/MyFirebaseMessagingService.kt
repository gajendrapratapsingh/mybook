package com.info.mybook.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.info.mybook.MainActivity
import com.info.mybook.R


const val channelId = "notification_channel"
const val channelName = "com.info.mybook"

class MyFirebaseMessagingService : FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM Token", "Refreshed token: $token")

        // Now you have the FCM token, which can be used to send push notifications to this device.
        // You may want to send this token to your server for further use.
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
       if(remoteMessage.notification != null){
           generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
       }
    }


    fun getRemoteView(title: String, message: String) : RemoteViews{

        val remoteView = RemoteViews("com.info.mybook.services", R.layout.notification)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.desc, message)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.android)

        return remoteView
    }

    fun generateNotification(title : String, message : String){

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val sound = Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.sound)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.android)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setSound(sound)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)

            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }
}