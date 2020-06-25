package com.example.hwhard_kolin.mvp.login

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class LoginPresenter(val view: LoginContract.view?, val connectivityManager: ConnectivityManager) :
    LoginContract.presenter {

    var mAccount: String? = null
    var mPassword: String? = null

    init {
        view?.setPresenter(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun detectLogin(account: String, password: String) {

        mAccount = account
        mPassword = password

        if (mAccount!!.isNotEmpty() && mPassword!!.isNotEmpty()) {
            startDetect()
        } else {
            view?.showErrorMessage("帳號、密碼不得為空")
        }

    }

    // 偵測有無網路
    @RequiresApi(Build.VERSION_CODES.M)
    fun detectNetWork() {
        val network = connectivityManager.activeNetwork
        if (network != null) {
            val NC = connectivityManager.getNetworkCapabilities(network)
            if (NC != null) {
                if (NC.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    detectServer()
                }
            }
        } else {
            view?.showErrorMessage("請檢查網路")
        }
    }

    fun detectServer() {
        // Todo 判斷是否維修
        detectAP()
    }

    fun detectAP() {

        view?.goManuView()
        // Todo 得取資料庫的帳密比對
    }


    override fun onDestory() {
        Log.d("empty", "onDestory")
    }

    override fun start() {
        Log.d("empty", "start")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun startDetect() {
        detectNetWork()
    }

}