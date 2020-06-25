package com.example.hwhard_kolin.mvp.manu.fragment.study

import com.example.hwhard_kolin.mvp.manu.fragment.study.StudyContract

class StudyPresenter(val view : StudyContract.View?) : StudyContract.Presenter{


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