package com.example.hwhard_kolin.mvp.register

import android.util.Log
import co.bxvip.ui.tocleanmvp.base.BasePresenter
import com.example.hwhard_kolin.bean.personalBean
import com.example.hwhard_kolin.mvp.model.sharePreference.SharePreference
import com.example.hwhard_kolin.util.CloudFireStore
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson

class RegisterPresenter(val mView: RegisterContract.view?) : BasePresenter,
    RegisterContract.presenter {

    val db by lazy { FirebaseFirestore.getInstance() }
    var mAccount: String? = null
    var personalBean: personalBean? = null

    init {
        mView?.setPresenter(this)
    }

    override fun regist(mAccount: String, mPassword: String, mName: String, mSchool: String) {

        if (mAccount.isEmpty() || mPassword.isEmpty() || mName.isEmpty() || mSchool.isEmpty()) {
            mView?.showToastMessage("不為為空")
            return
        }

        // 將資料丟進 bean
        personalBean =
            personalBean(account = mAccount, password = mPassword, name = mName, school = mSchool)

        /**
         * Scan 資料庫查詢是否已有此帳號
         * 若沒有則寫入 DB
         */
        detectIsAccountExsit()
    }

    private fun detectIsAccountExsit() {
        db.collection("personalData")
            .whereEqualTo("account", personalBean?.account)
            .limit(1).get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    mView?.showToastMessage("此帳號已被註冊")
                } else {
                    // 寫入 DB
                    regist()
                    mView?.showToastMessage("已成功註冊")
                }
            }
    }

    private fun regist() {
        personalBean?.run {
            db.collection("personalData").add(this)
                .addOnSuccessListener {
                    db.collection("personalData").document(it.id).update("id",it.id.subSequence(0,8))

                    Log.v("db_log", "---------------")
                    Log.v("db_log", "Regist_Success")
                    Log.v("db_log", it.toString())
                    Log.v("db_log", "---------------")
                }
        }
    }


    override fun onDestory() {
        //TODO("Not yet implemented")
    }

    override fun start() {
        //TODO("Not yet implemented")
    }

}