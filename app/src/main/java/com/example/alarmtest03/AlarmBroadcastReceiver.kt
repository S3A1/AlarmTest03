package com.example.alarmtest03

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.app.Notification
import android.app.NotificationChannel
import androidx.core.app.NotificationCompat
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.content.contentValuesOf


class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        //通知がクリックされた時に発行されるIntentの生成
        //val sendIntent = Intent(context, MainActivity::class.java)
        //val sender = PendingIntent.getActivity(context, 0, sendIntent, 0)


        val CHANNEL_ID = "channel_id"
        val channel_name = "channel_name"
        val channel_description = "channel_description "


        ///APIレベルに応じてチャネルを作成
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channel_name
            val descriptionText = channel_description
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            /// チャネルを登録
            val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


        var title="Title"
        var text="Answer"

        //通知オブジェクトの生成
        val builder=NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()


        var notificationId=0
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, builder)
        notificationId+=1


    }
}
