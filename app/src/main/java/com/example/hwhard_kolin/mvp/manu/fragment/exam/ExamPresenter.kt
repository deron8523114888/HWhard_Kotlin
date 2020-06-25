package com.example.hwhard_kolin.mvp.manu.fragment.exam

class ExamPresenter(val view : ExamContract.View?):
    ExamContract.Presenter{

    init {
        view?.setPresenter(this)
    }

    override fun onDestory() {
        // TODO("Not yet implemented")
    }

    override fun start() {
        // TODO("Not yet implemented")
    }

}