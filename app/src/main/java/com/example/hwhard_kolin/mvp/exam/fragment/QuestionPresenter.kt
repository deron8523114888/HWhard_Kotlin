package com.example.hwhard_kolin.mvp.exam.fragment

class QuestionPresenter(val mView: QuestionContract.View?) :QuestionContract.Presenter{

    init {
        mView?.setPresenter(this)
    }

    override fun onDestory() {

    }

    override fun start() {
    }

}