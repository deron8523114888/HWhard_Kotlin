package com.example.hwhard_kolin.mvp.register

import android.os.Message
import co.bxvip.ui.tocleanmvp.base.BasePresenter
import co.bxvip.ui.tocleanmvp.base.BaseView
import com.example.hwhard_kolin.bean.personalBean

interface RegisterContract {

    interface view : BaseView<presenter> {
        fun showToastMessage(message: String)
    }

    interface presenter : BasePresenter {
        fun regist(mAccount: String, mPassword: String, mName: String, mSchool: String)
    }


}