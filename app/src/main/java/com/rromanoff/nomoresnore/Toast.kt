package com.rromanoff.nomoresnore

import android.content.Context
import android.widget.Toast

class Toast(private val context: Context) {

    fun show(msg:Int){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun show(msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}