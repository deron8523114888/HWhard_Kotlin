package com.example.hwhard_kolin.mvp.manu

class ManuPresenter(val view: ManuView) :ManuContract.Presenter{

    init {
        view?.setPresenter(this)
    }

    override fun onDestory() {
        //TODO("Not yet implemented")
    }

    override fun start() {
        // TODO("Not yet implemented")
    }

}