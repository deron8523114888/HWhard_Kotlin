package com.example.hwhard_kolin.mvp.manu.fragment.readyExam

class ReadyExamPresenter(val view : ReadyExamContract.View?):
    ReadyExamContract.Presenter{

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