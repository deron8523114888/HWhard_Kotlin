package com.example.hwhard_kolin.mvp.manu.fragment.setting

class SettingPresenter (view:SettingContract.View?) : SettingContract.Presenter{

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