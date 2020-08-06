package com.example.hwhard_kolin.util

import android.content.Context

object obj {

    var context: Context? = null

    fun set_Context(context: Context) {
        this.context = context
    }

    fun get_Context() : Context? {
        return context
    }


}