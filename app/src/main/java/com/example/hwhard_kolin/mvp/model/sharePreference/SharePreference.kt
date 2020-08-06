package com.example.hwhard_kolin.mvp.model.sharePreference

import android.content.Context
import com.example.hwhard_kolin.bean.personalBean
import com.google.gson.Gson

object SharePreference {

    var context : Context ?= null

    fun initContext(context: Context){
        this.context = context
    }

    fun storePersonalData (json:String){
        context?.getSharedPreferences("PersonalWareHouse", Context.MODE_PRIVATE)?.edit()?.putString("PersonalData", json)?.apply()
    }

    fun getPersonalData(): personalBean? {
        var getstring = context?.getSharedPreferences("PersonalWareHouse", Context.MODE_PRIVATE)?.getString("PersonalData", "")
        return Gson().fromJson(getstring, personalBean::class.java)
    }

}