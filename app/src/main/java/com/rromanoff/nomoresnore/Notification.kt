package com.rromanoff.nomoresnore

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class Notification(private val context: Context) {
    //создаем идентификатор для нашего уведомления
    var NOTIFY_ID: Int = 0
    //создаем идентификатор для нашего канала
    private var CHANNEL_ID = "TestChannelId"
    //создаем имя для нашего канала
    private var CHANNEL_NAME = "TestChannelName"
    //заголовок уведомления
    private var CONTENT_TITLE = "NoMoreSnore"
    //содержание уведомления
    private var CONTENT_TEXT = "Тccc!"
    private var GROUP_KEY = "GroupKey"

    private lateinit var notificationChannel: NotificationChannel
    //private lateinit var notificationChannelGroup: NotificationChannelGroup
    private var notificationManager: NotificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var contentIntent: PendingIntent
    private lateinit var builder: NotificationCompat.Builder
    private  var notificationIntent:Intent=Intent(context, MainActivity::class.java)
    private lateinit var notificationThread:Thread
    private var SIGNAL_SEND_NOTIFY:Boolean = false
    private var SIGNAL_KILL_NOTIFY:Boolean = false
    private val noMoreSnore= arrayOf("No","More","Snore")
    private val categoryMsg= arrayOf(NotificationCompat.CATEGORY_ALARM,NotificationCompat.CATEGORY_CALL,NotificationCompat.CATEGORY_MESSAGE)
    var DELAY = 1000L

    private suspend fun makeNotification() {
        for (count in 0..2) {
            if (!SIGNAL_SEND_NOTIFY or SIGNAL_KILL_NOTIFY) return
            NOTIFY_ID = count
            //создаем идентификатор для нашего канала
            CHANNEL_ID = "TestChannelId$count"
            //создаем имя для нашего канала
            CHANNEL_NAME = "TestChannelName$count"
            //заголовок уведомления
            CONTENT_TITLE = noMoreSnore[count]
            //содержание уведомления
            CONTENT_TEXT = "\nТc"
            repeat(count){CONTENT_TEXT+="c"}
            CONTENT_TEXT+="!"

            GROUP_KEY = "GroupKey$count"

            //создаем канал уведомлений
            notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            //создаем Intent который должен открыть наш MainActivity
//            notificationIntent = Intent(context, MainActivity::class.java)
            //получаем объект класса NotificationManager
//            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //создаем описание канала
            notificationChannel.description = "Test$count"
            //создаем PendingIntent который откроет наше активити, когда пользователь кликнет по уведомлению
            contentIntent = PendingIntent.getActivity(
                context,
                count, // 0
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            //Создаем объект класса NotificationCompat.Builder()

            builder = NotificationCompat.Builder(context, CHANNEL_ID)
            //передаем в него необходимые параметры
            builder.setContentIntent(contentIntent)
                .setSilent(false)
                .setGroup(GROUP_KEY)
                //.setOngoing(true)
                .setAutoCancel(false)
                .setCategory(categoryMsg[count])
                .setShowWhen(false)
                .setSmallIcon(R.drawable.snorking_smile) //значек для уведомления
                .setContentTitle(CONTENT_TITLE) //заголовок уведомления
                .setContentText(CONTENT_TEXT) //содержание уведомления
            //создаем канал для уведомлений
            notificationManager.createNotificationChannel(notificationChannel)
            //notificationManager.createNotificationChannelGroup(notificationChannelGroup)

            //создаем уведомление
            notificationManager.notify(NOTIFY_ID, builder.build())

            repeat(10){if (SIGNAL_SEND_NOTIFY and !SIGNAL_KILL_NOTIFY) delay(DELAY/10L)} //!КОСТЫЛЬ!
//            delay(DELAY)
//            Thread.sleep(DELAY) //SystemClock


            //notificationManager.cancel(NOTIFY_ID) //убиваем уведомление
        }
        notificationManager.cancelAll() //убиваем уведомление
    }

    fun init(){
        SIGNAL_KILL_NOTIFY = false
        notificationThread = Thread(Runnable { run {
            while(!SIGNAL_KILL_NOTIFY){
                if (SIGNAL_SEND_NOTIFY) {
                    send()
                    SIGNAL_SEND_NOTIFY = false
                }
            }
        } } ,"Notification Thread")
        notificationThread.start()
    }

    fun send(){
        SIGNAL_SEND_NOTIFY = true
        runBlocking {
            async {
                makeNotification()
            }
        }
        SIGNAL_SEND_NOTIFY = false
    }

    fun killNotification() {
        SIGNAL_SEND_NOTIFY = false
        SIGNAL_KILL_NOTIFY = true
        notificationThread.join()
        notificationManager.cancelAll()
    }
}