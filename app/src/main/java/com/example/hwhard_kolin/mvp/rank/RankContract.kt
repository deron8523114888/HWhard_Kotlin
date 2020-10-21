package com.example.hwhard_kolin.mvp.rank

import co.bxvip.ui.tocleanmvp.base.BasePresenter
import co.bxvip.ui.tocleanmvp.base.BaseView

interface RankContract {

    interface View : BaseView<Presenter> {
        fun showLoadingView()

        fun finishLoadingView()
    }

    interface Presenter : BasePresenter {


    }


}