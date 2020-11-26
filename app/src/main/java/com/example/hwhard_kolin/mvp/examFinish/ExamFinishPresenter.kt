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
         *  90 -> +8
         *  80 -> +6
         *  70 -> +4
         *  60 -> +2
         *  50 -> -2
         *  40 -> -4
         *  30 -> -6
         *  20 -> -8
         *  10 -> -10
         *  0 -> -12
         */
        for (i in 10 downTo 0) {
            if (num >= i * 10) {
                var score = -10 + i * 2
                if (num >= 60) {
                    score += 2
                }
                if (num < 60) {
                    score -= 2
                }
                mView?.showRankScore(score)
                break
            }
        }
    }

    override fun onDestory() {

    }

    override fun start() {

    }

}