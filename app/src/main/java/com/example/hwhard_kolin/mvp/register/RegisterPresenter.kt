package com.example.hwhard_kolin.mvp.register

import co.bxvip.ui.tocleanmvp.base.BasePresenter

class RegisterPresenter(val view : RegisterContract.view?) : BasePresenter,RegisterContract.presenter {

    init {
        view?.setPresenter(this)
    }

    override fun onDestory() {
        //TODO("Not yet implemented")
    }

    override fun start() {
        //TODO("Not yet implemented")
    }
}