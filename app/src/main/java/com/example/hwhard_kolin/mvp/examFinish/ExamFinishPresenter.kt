package com.example.hwhard_kolin.mvp.examFinish

class ExamFinishPresenter(val mView: ExamFinishContract.View?) : ExamFinishContract.Presenter {

    init {
        mView?.setPresenter(this)
    }

    override fun onDestory() {

    }

    override fun start() {

    }

}