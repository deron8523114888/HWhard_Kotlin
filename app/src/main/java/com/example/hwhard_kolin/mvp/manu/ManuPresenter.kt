package com.example.hwhard_kolin.mvp.manu

class ManuPresenter(view:ManuContract.View?) :ManuContract.Presenter{

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