package com.example.hwhard_kolin.mvp.rank

import co.bxvip.ui.tocleanmvp.base.BasePresenter

class RankPresenter(val mView : RankContract.View?):RankContract.Presenter{

    init {
        mView?.setPresenter(this)
    }

    override fun onDestory() {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }


}