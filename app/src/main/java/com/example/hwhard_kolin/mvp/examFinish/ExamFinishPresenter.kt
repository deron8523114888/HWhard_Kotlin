package com.example.hwhard_kolin.mvp.examFinish

import com.example.hwhard_kolin.bean.QuestionBean

class ExamFinishPresenter(private val mView: ExamFinishContract.View?) :
    ExamFinishContract.Presenter {

    init {
        mView?.setPresenter(this)
    }

    override fun updateExamScore(questionList: MutableList<QuestionBean>) {

        /**
         *  考卷分數
         */

        var num = 0
        questionList.forEach {
            when (it.type) {

                "Int" -> {
                    if (it.answerTop == it.myAnswerTop) {
                        num += 20
                    }
                }

                "Frac" -> {
                    if (it.answerTop == it.myAnswerTop && it.answerBottom == it.myAnswerBottom) {
                        num += 20
                    }
                }
            }
        }

        mView?.showExamScore(num)

    }

    override fun updateRankScore(num: Int) {
        /** 積分分數
         *  100 -> +10
         *  95~99 -> +9
         *  90~94 -> +8
         *  85~89 -> +7
         *  80~84 -> +6
         *  75~79 -> +5
         *  70~74 -> +4
         *  65~69 -> +3
         *  60~64 -> +2
         *  55~59 -> -1
         *  50~54 -> -2
         *  45~49 -> -3
         *  40~44 -> -4
         *  35~39 -> -5
         *  30~34 -> -6
         *  25~29 -> -7
         *  20~24 -> -8
         *  15~19 -> -9
         *  10~14 -> -10
         *  5~9 -> -11
         *  0~4 -> -12
         */
        if (num >= 60) {
            val score = 2 + (num - 60) / 5
            mView?.showRankScore(score)
        } else {
            val score = 0 + (num - 60) / 5
            mView?.showRankScore(score)
        }

    }

    override fun onDestory() {

    }

    override fun start() {

    }

}