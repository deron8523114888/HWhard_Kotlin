package com.example.hwhard_kolin.util

import android.view.View
import android.widget.Button
import com.example.hwhard_kolin.bean.PersonalBean
import java.util.*


/**
 *  Rank的數字轉為字串
 */
fun Int.toRank(): String {
    val rankList = listOf("新生", "癸級", "壬級", "辛級", "庚級", "己級", "戊級", "丁級", "丙級", "乙級", "甲級", "優級")
    val index = this - 1
    if (index < rankList.size) {
        return rankList[index]
    }
    return "null"
}


/**
 *  Long 轉為時間格式 00:00:00
 */
fun Long.toTimeType(): String {

    val min = this / 1000 / 60
    val sec = this / 1000 % 60


    var minStr = "$min"
    var secStr = "$sec"

    if (min < 10) {
        minStr = "0$min"
    }

    if (sec < 10) {
        secStr = "0$sec"
    }

    return "$minStr:$secStr"
}

/**
 *  分數中間橫線長度設定
 *  依據分子或分母內容長度調整
 */
fun Int.fracLength(): String {
    var result = ""
    for (i in 1..this) {
        result += "--"
    }
    return result
}

/**
 *  鍵盤輸入答案監聽設定
 */
fun Button.insertAnswerSetting(callBack: () -> Unit, objects: View.OnTouchListener) {
    this.setOnClickListener {
        callBack()
    }
    this.setOnTouchListener(objects)
}

fun MutableMap<String, Any>.toPersonalBean(): PersonalBean {

    val personalBean = PersonalBean()
    personalBean.run {
        try {
            name = this@toPersonalBean["name"].toString()
            school = this@toPersonalBean["school"].toString()
            id = this@toPersonalBean["ID"].toString()
            loginType = this@toPersonalBean["loginType"].toString()
            rank = this@toPersonalBean["rank"].toString().toInt()
            score = this@toPersonalBean["score"].toString().toInt()
            win = this@toPersonalBean["win"].toString().toInt()
            lose = this@toPersonalBean["lose"].toString().toInt()
            url = this@toPersonalBean["url"].toString()
        } catch (e: Exception) {
            name = "格式錯誤"
        }
    }

    return personalBean
}