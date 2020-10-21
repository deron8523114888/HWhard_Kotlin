package com.example.hwhard_kolin.util

import android.content.Context
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.bean.QuestionBean
import com.google.gson.Gson

object SP {


    /**
     *  個人資料
     */
    fun setPersonalData(context: Context, personalData: PersonalBean) {
        val sp = context.getSharedPreferences("personalData", Context.MODE_PRIVATE)
        sp.edit().putString("personalData", Gson().toJson(personalData)).apply()
    }

    fun getPersonalData(context: Context): PersonalBean {
        val sp = context.getSharedPreferences("personalData", Context.MODE_PRIVATE)
        return Gson().fromJson(sp.getString("personalData", ""), PersonalBean::class.java) ?: PersonalBean()
    }


    /**
     *  題目
     */
    fun setQuestion(context: Context, questionList: MutableList<QuestionBean>) {
        val sp = context.getSharedPreferences("question", Context.MODE_PRIVATE)
        sp.edit().putString("question", questionList.toString()).apply()
    }

    fun getQuestion(context: Context): MutableList<*> {
        val sp = context.getSharedPreferences("question", Context.MODE_PRIVATE)
        val str = sp.getString("question", "")
        return str as MutableList<*>
    }

    /**
     *  line ID
     */
    fun setLastLineID(context: Context, lineID: String) {
        val sp = context.getSharedPreferences("lineID", Context.MODE_PRIVATE)
        sp.edit().putString("lineID", lineID).apply()
    }

    fun getLastLineID(context: Context): String {
        val sp = context.getSharedPreferences("lineID", Context.MODE_PRIVATE)
        return sp.getString("lineID", "") ?: ""
    }

    /**
     *  facebook ID
     */
    fun setLastFacebookID(context: Context,facebookID: String){
        val sp = context.getSharedPreferences("facebookID", Context.MODE_PRIVATE)
        sp.edit().putString("facebookID", facebookID).apply()
    }

    fun getLastFacebookID(context: Context): String {
        val sp = context.getSharedPreferences("facebookID", Context.MODE_PRIVATE)
        return sp.getString("facebookID", "") ?: ""
    }

    /**
     *  gmail ID
     */
    fun setLastGmailID(context: Context,facebookID: String){
        val sp = context.getSharedPreferences("gmailID", Context.MODE_PRIVATE)
        sp.edit().putString("gmailID", facebookID).apply()
    }

    fun getLastGmailID(context: Context): String {
        val sp = context.getSharedPreferences("gmailID", Context.MODE_PRIVATE)
        return sp.getString("gmailID", "") ?: ""
    }

}