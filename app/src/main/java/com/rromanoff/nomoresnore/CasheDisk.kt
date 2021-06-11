package com.rromanoff.nomoresnore

import android.content.Context
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class CasheDisk(private val context: Context) {

    //val fileName = "seekBarValue"

    fun save(fileName:String, seekBarValue: String) {
        var outputStream: FileOutputStream? = null

        try {
            //открываем выходной поток для записи файла
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            //записываем в файл содержимое файла
            outputStream.write(seekBarValue.toByteArray())
        } catch (exc: Exception) {
            exc.printStackTrace()
        } finally {
            //закрываем наш выходной поток
            outputStream?.close()
        }
    }

    fun read(fileName:String):String {
        var inputStream: FileInputStream? = null
        try {
        //открываем входящий поток для получения нашего файла
            inputStream = context.openFileInput(fileName)

        } catch (exc: Exception) {
            //exc.printStackTrace()
            save(fileName,"80")
        }
        //перадаем поток в наш буффер, который считывает информацию из файла
        val buffer = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?

        while (true) {
            line = buffer.readLine() ?: break
            stringBuilder.append(line)
        }

        //закрываем наш буффер
        buffer.close()
        return if (stringBuilder.isBlank()) "0" else stringBuilder.toString()
    }

}