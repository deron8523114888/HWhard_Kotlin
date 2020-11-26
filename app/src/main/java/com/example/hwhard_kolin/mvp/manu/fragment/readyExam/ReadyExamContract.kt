package com.example.hwhard_kolin.mvp.manu.fragment.readyExam

import android.os.Bundle
import co.bxvip.ui.tocleanmvp.base.BasePresenter
import co.bxvip.ui.tocleanmvp.base.BaseView
import com.example.hwhard_kolin.bean.PersonalBean

interface ReadyExamContract {

    interface View : BaseView<Presenter> {

        fun getPersonData(): PersonalBean

        fun goExam(bundle: Bundle)
        fun showErrorMessage(error: String)

    }

    interface Presenter : BasePresenter {
        fun goChapter(mExamModeList: List<Int>)
        fun goVolume(mExamModeList: List<Int>)
        fun goGSAT(mExamModeList: List<Int>)
        fun goRank(mExamModeList: List<Int>)

    }

}