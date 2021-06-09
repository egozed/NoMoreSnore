package com.rromanoff.nomoresnore

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var ui : Ui
    private lateinit var permissions : Permissions
    private lateinit var toast : Toast
    private lateinit var vibrator : Vibration
    private lateinit var audio : Audio
    private lateinit var cacheDisk:CasheDisk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        ui=Ui(this)
        ui.create() //создаем визуалку
        permissions=Permissions(this)
        permissions.getPermissions() //запрашиваем разрешения
        cacheDisk=CasheDisk(this)
        ui.setTrashHold(cacheDisk.read().toInt()) //считываем значение трэшхолда из кэша. показываем на "перемотчике"
        vibrator=Vibration(this)
        toast=Toast(this)
        audio=Audio(ui, vibrator)

        ui.startSwitchHandler(audio, toast) //запускаем обработчик выключателя
        ui.startAudioGateHandler() //
    }

    override fun onResume() {
        super.onResume()
        ui.setTrashHold(cacheDisk.read().toInt())
    }

    override fun onPause() {
        super.onPause()
        ui.my_switch.isChecked=false
        ui.showAmplitude(0)
        cacheDisk.save(ui.getTrashHold().toString())
    }

    override fun onStop() {
        super.onStop()
        ui.my_switch.isChecked=false
        ui.showAmplitude(0)
        cacheDisk.save(ui.getTrashHold().toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        cacheDisk.save(ui.getTrashHold().toString())
        audio.stopAudioMicInterface()
    }

}

fun log(x:Any) { Log.d("My",x.toString()) }