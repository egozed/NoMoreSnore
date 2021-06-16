package com.rromanoff.nomoresnore

import android.annotation.SuppressLint
import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout


@SuppressLint("UseSwitchCompatOrMaterialCode")
class Ui(private  val context: Activity) {

    private lateinit var my_progressBar: ProgressBar
    lateinit var my_seekBar: SeekBar
    //var my_seekBar_currentValue:Int=80
    lateinit var my_switch: Switch
    lateinit var gateVal_textView: TextView
    lateinit var my_ConstraintLayout: ConstraintLayout
    lateinit var editText_Delay_in_mSec:EditText

    fun create(){
        my_progressBar = context.findViewById(R.id.my_progressBar)
        my_ConstraintLayout = context.findViewById(R.id.my_ConstraintLayout)
        my_seekBar = context.findViewById(R.id.my_seekBar)
        my_switch = context.findViewById(R.id.my_switch)
        gateVal_textView = context.findViewById(R.id.gateVal_textView)
        editText_Delay_in_mSec = context.findViewById(R.id.editText_Delay_in_mSec)

    }

    fun editTextDelayHandler(notification:Notification) {
         editText_Delay_in_mSec.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // perform action on key press
                    if (!editText_Delay_in_mSec.text.equals("")){
                        notification.DELAY = editText_Delay_in_mSec.text.toString().toLong()
                    }

                    // clear focus and hide cursor from edit text
                    editText_Delay_in_mSec.clearFocus()
                    //editText_Delay_in_mSec.isCursorVisible = false
                    editText_Delay_in_mSec.isEnabled=false
                    editText_Delay_in_mSec.isEnabled=true

                    return true
                }
                return false
            }
        })
    }

    fun startSwitchHandler(audio:Audio, toast:Toast, notification:Notification) {
        my_switch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {   /*User turn ON the SWITCH*/
                    toast.show(R.string.on)
                    my_ConstraintLayout.keepScreenOn = true
                    notification.init()
                    audio.startAudioMicInterface()
                    //audio.setSilentMode()
                }
                false -> {  /*User turn OFF the SWITCH*/
                    toast.show(R.string.off)
                    notification.killNotification()
                    audio.stopAudioMicInterface()
                    showAmplitude(0)
                    my_ConstraintLayout.keepScreenOn = false
                    //audio.setNormalMode()
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