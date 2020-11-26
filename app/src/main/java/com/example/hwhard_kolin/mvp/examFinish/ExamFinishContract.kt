package com.example.hwhard_kolin.mvp.examFinish

import co.bxvip.ui.tocleanmvp.base.BasePresenter
import co.bxvip.ui.tocleanmvp.base.BaseView
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.bean.QuestionBean
import com.example.hwhard_kolin.bean.QuestionList

interface ExamFinishContract {

    interface View : BaseView<Presenter> {
        fun showExamScore(score: Int)
        fun showRankScore(score: Int)
    }

    interface Presenter : BasePresenter {
        fun updateExamScore(questionList: MutableList<QuestionBean>)
        fun updateRankScore(num: Int)
    }

}