package com.example.hwhard_kolin.util

import android.content.Context
import android.net.ConnectivityManager

object NetWork {


    var connectivityManager : ConnectivityManager ?= null


    fun setManager(connectivityManager: ConnectivityManager) {
        this.connectivityManager = connectivityManager
    }

    fun getManager() : ConnectivityManager{
        return connectivityManager!!
    }




}