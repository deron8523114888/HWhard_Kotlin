package com.example.hwhard_kolin.mvp.exam

class ExamPresenter(val mView: ExamContract.View?) : ExamContract.Presenter {

    init {
        mView?.setPresenter(this)
    }

    override fun onDestory() {

    }

    override fun start() {
    }

}