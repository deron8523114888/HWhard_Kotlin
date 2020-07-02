package com.example.hwhard_kolin.mvp.login

import android.app.Activity
import co.bxvip.ui.tocleanmvp.base.BasePresenter
import co.bxvip.ui.tocleanmvp.base.BaseView

interface LoginContract{

    interface view:BaseView<presenter>{

        fun showErrorMessage(error:String)

        fun goManuView()

        fun startLoadingView()

        fun finishLoadingView()
    }

    interface presenter : BasePresenter{

        fun detectLogin(account:String, password:String)

    }

}