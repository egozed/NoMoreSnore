package com.rromanoff.nomoresnore

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

object AudioConsts {
    const val RECORDER_SAMPLERATE:Int = 44100 // 4000..192000 8000 hz = 8000 per sec => updates every 125us(0.000125s)
    const val SOURCE:Int = MediaRecorder.AudioSource.MIC
    const val RECORDER_CHANNELS:Int = AudioFormat.CHANNEL_IN_MONO
    const val RECORDER_AUDIO_ENCODING:Int = AudioFormat.ENCODING_PCM_FLOAT // unsigned 8bit => 0..255, where (128) is 0(i.e. no sound) and (0 or 255) is amplitude
    const val readMode:Int = AudioRecord.READ_NON_BLOCKING //With READ_BLOCKING, the read will block until all the requested data is read. (READ_NON_BLOCKING)
}