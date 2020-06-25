package com.example.hwhard_kolin.mvp.manu.fragment.personal

import com.example.hwhard_kolin.mvp.manu.fragment.personal.PersonalContract

class PersonalPresenter(val view: PersonalContract.View) :
    PersonalContract.Presenter {


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