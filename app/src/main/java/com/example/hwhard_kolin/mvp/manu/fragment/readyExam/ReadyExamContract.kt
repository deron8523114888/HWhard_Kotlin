package com.example.hwhard_kolin.mvp.manu.fragment.readyExam

import android.os.Bundle
import co.bxvip.ui.tocleanmvp.base.BasePresenter
import co.bxvip.ui.tocleanmvp.base.BaseView

interface ReadyExamContract {

    interface View : BaseView<Presenter> {
        fun goExam(bundle: Bundle)
        fun showErrorMessage(error: String)

    }

    interface Presenter : BasePresenter {
        fun goChapter(mExamModeList: List<Int>, rank: Int)
        fun goVolume(volume: Int, rank: Int)
        fun goGSAT(versionNum: Int, rank: Int)
        fun goRank(versionNum: Int, rank: Int)

    }

}