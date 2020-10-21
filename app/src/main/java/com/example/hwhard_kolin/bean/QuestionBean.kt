package com.example.hwhard_kolin.bean

import java.io.Serializable

data class QuestionList(
    var questionList: MutableList<QuestionBean>
) : Serializable

data class QuestionBean(
    var chapter: String = "",
    var num: String = "",
    var type: String = "",
    var answerTop: String = "",
    var answerBottom: String = "",
    var myAnswerTop: String = "",
    var myAnswerBottom: String = ""
) : Serializable