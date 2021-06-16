package com.rromanoff.nomoresnore

import android.content.Context
import android.media.AudioRecord
import kotlin.math.abs


class Audio(private val context: Context, private val ui:Ui, private val notification:Notification) {

//    private val myAudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private  lateinit var audioRecord:AudioRecord
    private  val bufferSize: Int = AudioRecord.getMinBufferSize(AudioConsts.RECORDER_SAMPLERATE, AudioConsts.RECORDER_CHANNELS, AudioConsts.RECORDER_AUDIO_ENCODING)
    private  val BufferLinearFloat:FloatArray=FloatArray(bufferSize)
    private  lateinit var recordingThread:Thread
    private  lateinit var handlingThread:Thread
    private  var isRecording = false

    fun startAudioMicInterface(){
        //setSilentMode()
        audioRecord = AudioRecord(AudioConsts.SOURCE,AudioConsts.RECORDER_SAMPLERATE,AudioConsts.RECORDER_CHANNELS,AudioConsts.RECORDER_AUDIO_ENCODING,bufferSize)
        audioRecord.startRecording()
        isRecording = true
        recordingThread = Thread(Runnable { run { audioGettingProcess() } },"AudioRecorder Thread")
        recordingThread.start()
        handlingThread = Thread(Runnable { run { audioHandlingProcess() } },"AudioHandling Thread")
        handlingThread.start()
    }

    private fun audioGettingProcess(){
        while (isRecording) {
            audioRecord.read(BufferLinearFloat,0, bufferSize,AudioConsts.readMode) //считали АЦП в буффер
        }
    }

    private fun audioHandlingProcess(){
        while (isRecording) {
            BufferLinearFloat.forEachIndexed{index: Int, element: Float ->
                BufferLinearFloat[index] = abs(element)
            }   // выпрямили. теперь каждое значение буфера взято по модулю
            //val avg = BufferLinearFloat.average()  // взяли среднее от всех значений массива
            val max = BufferLinearFloat.maxOrNull()!!.toDouble()  // взяли mаксимальное из всех значений массива
            val log =  100.0-100.0/( Math.pow(100.0, max) )  // привели линейную шкалу 0..+1 к логарифмической 1..+100
            ui.showAmplitude(log.toInt())
            if (log > ui.getTrashHold()) {
                notification.send() //here we send the notify
            }
        }
    }

    fun stopAudioMicInterface(){
        isRecording = false
        handlingThread.join()
        recordingThread.join()
        BufferLinearFloat.fill(0.0f, 0,bufferSize-1) //clean buf
        audioRecord.stop()
        audioRecord.release()
        //setNormalMode()
    }

/*    fun setSilentMode(){
        myAudioManager.ringerMode=AudioManager.RINGER_MODE_SILENT
    }

    fun setNormalMode(){
        myAudioManager.ringerMode=AudioManager.RINGER_MODE_NORMAL
    }*/
}