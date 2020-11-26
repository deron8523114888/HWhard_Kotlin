package com.example.hwhard_kolin.util

import android.content.Context
import com.example.hwhard_kolin.R


/**
 *  【牌位的字串】
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
 *  【牌位的圖片】
 */
fun Int.toRankDrawable(): Int {
    return when (this) {
        1 -> R.drawable.rank_1
        2 -> R.drawable.rank_2
        3 -> R.drawable.rank_3
        4 -> R.drawable.rank_4
        5 -> R.drawable.rank_5
        6 -> R.drawable.rank_6
        7 -> R.drawable.rank_7
        8 -> R.drawable.rank_8
        9 -> R.drawable.rank_9
        10 -> R.drawable.rank_10
        11 -> R.drawable.rank_11
        12 -> R.drawable.rank_12
        else -> R.drawable.empty_icon
    }
}

class QuestionInfo(
    val degree1: String = "",
    val num1: Int = 0,
    val degree2: String = "",
    val num2: Int = 0
) {

}


/**
 *  五題【題目的難度】
 */
fun Int.getDegreeAndNum(): QuestionInfo {
    return when (this) {
        // 新生
        1 -> QuestionInfo("E", 5)
        // 壬 癸 -> E 為主
        2 -> QuestionInfo("E", 5)
        3 -> QuestionInfo("E", 4,"D",1)
        // 庚 辛 -> D 為主
        4 -> QuestionInfo("D", 5)
        5 -> QuestionInfo("D", 4,"C",1)
        // 戊 己 -> C 為主
        6 -> QuestionInfo("C", 5)
        7 -> QuestionInfo("C", 4,"B",1)
        // 丙 丁 -> B 為主 無A
        8 -> QuestionInfo("C", 2,"B",3)
        9 -> QuestionInfo("C", 1,"B",4)
        // 甲 乙 -> B 為主 有A
        10 -> QuestionInfo("B", 4,"A",1)
        11 -> QuestionInfo("B", 3,"A",2)
        // 優級 -> A 為主
        12 -> QuestionInfo("B", 1,"A",4)
        else -> QuestionInfo()
    }
}


