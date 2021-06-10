package com.rromanoff.nomoresnore

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class Notification(private val context: Context) {
    //создаем идентификатор для нашего уведомления
    private val NOTIFY_ID: Int = 100
    //создаем идентификатор для нашего канала
    private val CHANNEL_ID = "TestChannelId"
    //создаем имя для нашего канала
    private val CHANNEL_NAME = "TestChannelName"
    //заголовок уведомления
    private val CONTENT_TITLE = "NoMoreSnore"
    //содержание уведомления
    private val CONTENT_TEXT = "Тихо,БЛЯ!!!"
    private val pattern = longArrayOf( 0, 100)

    //создаем канал уведомлений
    private val notificationChannel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    )
    //создаем Intent который должен открыть наш MainActivity
    private val notificationIntent = Intent(context, MainActivity::class.java)
    //получаем объект класса NotificationManager
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var contentIntent:PendingIntent
    private lateinit var builder:NotificationCompat.Builder

    fun init() {
        //создаем описание канала
        notificationChannel.description = "Test"

        //создаем PendingIntent который откроет наше активити, когда пользователь кликнет по уведомлению
        contentIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        //Создаем объект класса NotificationCompat.Builder()
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
        //передаем в него необходимые параметры
        builder.setContentIntent(contentIntent)
            .setSilent(true)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_background) //значек для уведомления
            .setContentTitle(CONTENT_TITLE) //заголовок уведомления
            .setContentText(CONTENT_TEXT) //содержание уведомления

        //создаем канал для уведомлений
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun send(){
        //создаем уведомление
        notificationManager.notify(NOTIFY_ID, builder.build())
    }

}