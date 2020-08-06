package com.example.hwhard_kolin.mvp.login

import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.hwhard_kolin.mvp.model.sharePreference.SharePreference
import com.example.hwhard_kolin.util.CloudFireStore
import com.example.hwhard_kolin.util.NetWork
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class LoginPresenter(val mView: LoginContract.view?) :
    LoginContract.presenter {

    var mAccount: String? = null
    var mPassword: String? = null
    val db = FirebaseFirestore.getInstance()

    init {
        mView?.setPresenter(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun detectLogin(account: String, password: String) {

        mAccount = account
        mPassword = password

        mView?.startLoadingView()
        if (mAccount!!.isNotEmpty() && mPassword!!.isNotEmpty()) {
            startDetect()
        } else {
            mView?.finishLoadingView()
            mView?.showErrorMessage("帳號、密碼不得為空")
        }

    }

    // 偵測有無網路
    @RequiresApi(Build.VERSION_CODES.M)
    fun detectNetWork() {
        val manager = NetWork.getManager()
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)

        if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            detectServer()
        } else {
            mView?.finishLoadingView()
            mView?.showErrorMessage("請檢查網路")
            return
        }
    }

    fun detectServer() {
        // Todo 判斷是否維修
        detectAP()
    }


    /**
     *  登入驗證
     */
    fun detectAP() {

        db.collection("personalData")
            .whereEqualTo("account", mAccount)
            .whereEqualTo("password", mPassword).limit(1).get()

            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    Log.v("db_log", it.documents[0]?.data.toString())
                    Gson().toJson(it.documents[0]?.data)
                    SharePreference.storePersonalData(Gson().toJson(it.documents[0]?.data))
                    mView?.goManuView()
                } else {
                    mView?.finishLoadingView()
                    mView?.showErrorMessage("帳號或密碼錯誤")
                }
            }

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