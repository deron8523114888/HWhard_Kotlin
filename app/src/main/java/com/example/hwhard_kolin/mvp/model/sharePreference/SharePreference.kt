package com.example.hwhard_kolin.mvp.model.sharePreference

import android.content.Context
import com.example.hwhard_kolin.PersonalBean
import com.google.gson.Gson

object SharePreference {

    var context : Context ?= null

    fun initContext(context: Context){
        this.context = context
    }

    fun storePersonalData (personalBeanArray:PersonalBean){
        var jsonBmi = Gson().toJson(personalBeanArray)
        context?.getSharedPreferences("PersonalWareHouse", Context.MODE_PRIVATE)?.edit()?.putString("PersonalData", jsonBmi)?.apply()
    }

    fun getPersonalData(): PersonalBean? {
        var getstring = context?.getSharedPreferences("PersonalWareHouse", Context.MODE_PRIVATE)?.getString("PersonalData", "")
        return Gson().fromJson(getstring, PersonalBean::class.java)
    }

}