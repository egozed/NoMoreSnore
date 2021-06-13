package com.rromanoff.nomoresnore

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions(private val context: Activity) {

    fun getPermissions(){


        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.RECORD_AUDIO),1)

/*
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.INTERNET),0)

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.VIBRATE),2)


        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.BROADCAST_STICKY) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.BROADCAST_STICKY),3)

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.KILL_BACKGROUND_PROCESSES) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.KILL_BACKGROUND_PROCESSES),4)


        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.FOREGROUND_SERVICE),5)
*/

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WAKE_LOCK),6)

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),7)

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.MODIFY_AUDIO_SETTINGS),8)

    }

}