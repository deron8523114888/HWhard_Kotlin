package com.example.hwhard_kolin.mvp.exam

import co.bxvip.ui.tocleanmvp.base.BasePresenter
import co.bxvip.ui.tocleanmvp.base.BaseView

interface ExamContract {
    interface View : BaseView<Presenter> {

        fun showErrorMessage(error:String)

        fun showLoadingView()

        fun finishLoadingView()
    }

    interface Presenter : BasePresenter {

    }
}