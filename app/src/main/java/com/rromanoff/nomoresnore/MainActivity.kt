package com.rromanoff.nomoresnore

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var ui : Ui
    private lateinit var permissions : Permissions
    private lateinit var toast : Toast
    private lateinit var audio : Audio
    private lateinit var cacheDisk:CasheDisk
    private lateinit var notification:Notification
/*
    private lateinit var vibrator : Vibration
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        ui=Ui(this)
        ui.create() //создаем визуалку
        permissions=Permissions(this)
        permissions.getPermissions() //запрашиваем разрешения
        cacheDisk=CasheDisk(this)
        ui.setTrashHold(cacheDisk.read("TrashHoldValue.txt",).toInt()) //считываем значение трэшхолда из кэша. показываем на "перемотчике"
        toast=Toast(this)
        notification=Notification(this)
        audio=Audio(ui, notification /*vibrator*/)
        ui.startSwitchHandler(audio, toast, notification) //запускаем обработчик выключателя
        ui.startAudioGateHandler() //запускаем обработчик звукового порога
        ui.editTextDelayHandler(notification) //запускаем обработчик задержки между пушами
/*
        vibrator=Vibration(this)
*/
    }

    override fun onStart() {
        super.onStart()
        ui.setTrashHold(cacheDisk.read("TrashHoldValue.txt").toInt())
    }
    override fun onRestart() {
        super.onRestart()
        ui.setTrashHold(cacheDisk.read("TrashHoldValue.txt").toInt())
        //notification.killAll()
    }
    override fun onResume() {
        super.onResume()
        ui.setTrashHold(cacheDisk.read("TrashHoldValue.txt").toInt())
        //notification.killAll()
    }
    override fun onPause() {
        super.onPause()
        cacheDisk.save("TrashHoldValue.txt",ui.getTrashHold().toString())
        //notification.killAll()
//        ui.my_switch.isChecked=false;         ui.showAmplitude(0)
    }
    override fun onStop() {
        super.onStop()
        cacheDisk.save("TrashHoldValue.txt",ui.getTrashHold().toString())
        //notification.killAll()
//        ui.my_switch.isChecked=false;         ui.showAmplitude(0)
    }
    override fun onDestroy() {
        super.onDestroy()
        cacheDisk.save("TrashHoldValue.txt",ui.getTrashHold().toString())
        audio.stopAudioMicInterface()
        if( notification.NOTIFY_ID != 0 ) notification.killNotification() //значит запускали хоть разок
    }

}
//use Just for debug,like: log("wtf is ${shit}")
/*
fun log(x:Any) { Log.d("My",x.toString()) }
*/
