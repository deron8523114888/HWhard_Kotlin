package com.example.hwhard_kolin.mvp.login

import android.app.Activity
import android.app.Person
import co.bxvip.ui.tocleanmvp.base.BasePresenter
import co.bxvip.ui.tocleanmvp.base.BaseView
import com.example.hwhard_kolin.bean.PersonalBean
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import javax.security.auth.callback.Callback

interface LoginContract {

    interface View : BaseView<Presenter> {

        fun showErrorMessage(error: String)

        fun UiState(canJoinGame: Boolean)

        fun goToManu()

        fun showLoadingView()

        fun finishLoadingView()

        fun showSchoolDialog()

        fun getAct(): Activity
    }

    interface Presenter : BasePresenter {

        fun loginToLine()

        fun loginToFB()

        fun loginToGmail()

        fun detectIsAccountExsit(ID: String, loginType: String)

        fun writeToDB(personalBean: PersonalBean)
    }

}