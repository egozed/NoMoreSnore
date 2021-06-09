package com.rromanoff.nomoresnore

import android.app.Activity
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

class Vibration(private val context: Activity) {

    private val vibrator:Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun vibrate(ms:Long=25){
        if (ms>0)
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
    }

}