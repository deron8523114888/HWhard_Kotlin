package com.example.hwhard_kolin.util

import android.animation.ValueAnimator
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.bean.PersonalBean
import java.util.*
import kotlin.collections.ArrayList


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
            id = this@toPersonalBean["id"].toString()
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

/**
 *  積分數字動畫
 */
fun TextView.startScoreAnimation(oldScore: Int, newScore: Int) {
    val valueAnimator = ValueAnimator.ofInt(oldScore, newScore)
    valueAnimator.run {
        duration = 2000
        addUpdateListener {
            this@startScoreAnimation.text = "分數：${it.animatedValue}"
        }
        start()
    }
}

/**
 * 根據 picker 選擇取得章節名稱
 * 【請勿修改、該名稱對應資料庫】
 */
fun List<Int>.toChapter(): String {
    return when (this[1]) {
        0 -> {
            // 第一冊
            when (this[2]) {
                0 -> "num_and_formula"
                1 -> "line_circle"
                2 -> "polynomial"
                else -> ""
            }
        }
        1 -> {
            // 第二冊
            when (this[2]) {
                0 -> "trigonometric_ratio"
                1 -> "sequence"
                2 -> "statistics"
                3 -> "permutation_probability"
                else -> ""
            }
        }
        2 -> {
            // 第三冊(A)
            when (this[2]) {
                0 -> "A_trigonometric"
                1 -> "A_ex_log"
                2 -> "A_vector2"
                else -> ""
            }
        }
        3 -> {
            // 第三冊(B)
            when (this[2]) {
                0 -> "B_trigonometric_cycle"
                1 -> "B_ratio_grow_mode"
                2 -> "B_vector2"
                else -> ""
            }
        }
        4 -> {
            // 第四冊(A)
            when (this[2]) {
                0 -> "A_vector3"
                1 -> "A_line_plane_3"
                2 -> "A_condition_probability"
                3 -> "A_matrix"
                else -> ""
            }
        }
        5 -> {
            // 第四冊(B)
            when (this[2]) {
                0 -> ""
                1 -> ""
                2 -> ""
                else -> ""
            }
        }
        else -> ""
    }
}

/**
 *  章節取得對應的題目數量
 *  [E,D,C,B,A]
 */

fun String.getNum(): Map<String, Int> {
    return when (this) {
        "permutation_probability" -> mapOf("E" to 6, "D" to 0, "C" to 0, "B" to 0, "A" to 0)
        else -> mapOf("E" to 0, "D" to 0, "C" to 0, "B" to 0, "A" to 0)
    }
}

/**
 *  取得隨機五個數字
 */
fun Int.getFiveRandom() : ArrayList<Int>{
    if(this < 5){
        return arrayListOf()
    }
    var resultList = arrayListOf<Int>()
    while (resultList.size != 5){
        val random = (1..this).random()
        if(resultList.contains(random)){
            continue
        }
        resultList.add(random)
    }
    return resultList
}
