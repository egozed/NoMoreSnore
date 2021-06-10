package com.rromanoff.nomoresnore

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

@SuppressLint("UseSwitchCompatOrMaterialCode")
class Ui(private  val context: Activity) {

    private lateinit var my_progressBar: ProgressBar
    lateinit var my_seekBar: SeekBar
    //var my_seekBar_currentValue:Int=80
    lateinit var my_switch: Switch
    lateinit var gateVal_textView: TextView
    lateinit var my_ConstraintLayout: ConstraintLayout

    fun create(){
        my_progressBar = context.findViewById(R.id.my_progressBar)
        my_ConstraintLayout = context.findViewById(R.id.my_ConstraintLayout)
        my_seekBar = context.findViewById(R.id.my_seekBar)
        my_switch = context.findViewById(R.id.my_switch)
        gateVal_textView = context.findViewById(R.id.gateVal_textView)
    }

    fun startSwitchHandler(audio:Audio, toast:Toast) {
        my_switch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {   /*User turn ON the SWITCH*/
                    toast.show(R.string.on)
                    my_ConstraintLayout.keepScreenOn = true
                    audio.startAudioMicInterface()
                }
                false -> {  /*User turn OFF the SWITCH*/
                    toast.show(R.string.off)
                    audio.stopAudioMicInterface()
                    showAmplitude(0)
                    my_ConstraintLayout.keepScreenOn = false
                }
            }
        }
    }

    fun startAudioGateHandler(){
        my_seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, currentValue: Int, p2: Boolean) {
                setTrashHold(currentValue)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    fun showAmplitude(nowAmpVal:Int=0){
        my_progressBar.post { my_progressBar.progress=nowAmpVal }
    }

    fun setTrashHold(maxAmpVal:Int=0){
        //my_seekBar.progress=maxAmpVal
        my_seekBar.post { my_seekBar.progress=maxAmpVal }
        //gateVal_textView.text = maxAmpVal.toString()
        gateVal_textView.post { gateVal_textView.text=maxAmpVal.toString() }
    }

    fun getTrashHold():Int{
        return my_seekBar.progress
    }
}