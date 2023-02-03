package com.ssafy.smile.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.smile.Application
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import kotlin.random.Random

private const val TAG = "FirebaseService_싸피"
class FirebaseService  : FirebaseMessagingService() {
    private val channelId = "SMILE"
    private var token : String? = Application.fcmToken
    lateinit var messageTitle: String
    lateinit var messageContent: String

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.notification !=null){
            messageTitle = message.notification!!.title.toString()
            messageContent = message.notification!!.body.toString()
        } else{
            messageTitle = message.data["title"].toString()
            messageContent = message.data["body"].toString()
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { createNotificationChannel(notificationManager) }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(messageTitle)
            .setContentText(messageContent)
            .setSmallIcon(R.drawable.ic_smile)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        Log.d(TAG, "onMessageReceived: ${messageTitle}, ${messageContent}")

        notificationManager.notify(notificationID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "Client"
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "FCM"
            enableLights(true)
            lightColor = Color.BLUE
        }
        notificationManager.createNotificationChannel(channel)
    }
}