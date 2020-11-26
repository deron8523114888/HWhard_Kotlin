package com.example.hwhard_kolin.bean

import java.io.Serializable

data class QuestionList(
    var questionList: MutableList<QuestionBean>
) : Serializable

data class QuestionBean(
    var chapter: String = "",
    var degree: String = "",
    var questionNumber: String = "",
    var num: String = "",
    var type: String = "",
    var answerTop: String = "",
    var answerBottom: String = "",
    var myAnswerTop: String = "",
    var myAnswerBottom: String = ""
) : Serializable

data class AnswerBean(
    var type: String = "",
    var answerTop: String = "",
    var answerBottom: String = ""
) : Serializable

data class Permutation(
    var E:Int = 6
)