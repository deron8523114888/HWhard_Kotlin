package com.example.hwhard_kolin.mvp.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.util.CloudFireStore
import com.example.hwhard_kolin.util.Constants
import com.example.hwhard_kolin.util.SP
import com.example.hwhard_kolin.util.toPersonalBean
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi


class LoginPresenter(val context: Context, val mView: LoginContract.View?) :
    LoginContract.Presenter {

    private val db by lazy { FirebaseFirestore.getInstance() }

    private var callBack = CallbackManager.Factory.create()

    init {
        mView?.setPresenter(this)
    }


    override fun loginToLine() {
        try {
            // App-to-app login
            val loginIntent: Intent = LineLoginApi.getLoginIntent(
                context,
                Constants.LINE_CHANNEL_ID,
                LineAuthenticationParams.Builder()
                    .scopes(listOf(Scope.PROFILE)) // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                    .build()
            )
            startActivityForResult(
                mView?.getAct()!!,
                loginIntent,
                Constants.LINE_REQUEST_CODE,
                Bundle()
            )
        } catch (e: Exception) {
            Log.e("test__", e.toString())
        }
    }

    override fun loginToFB() {

        LoginManager.getInstance().run {
            registerCallback(callBack, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    mView?.showErrorMessage("onSuccess")
                }

                override fun onCancel() {
                    mView?.showErrorMessage("onCancel")
                }

                override fun onError(error: FacebookException?) {
                    mView?.showErrorMessage("onError")
                }

            })
            logInWithPublishPermissions(mView?.getAct(), listOf())
        }
    }

    override fun loginToGmail() {

        val mGoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(context, mGoogleSignInOptions)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            mView?.getAct()!!,
            signInIntent,
            Constants.GMAIL_REQUEST_CODE,
            Bundle()
        )
    }

    /**
     * Scan 資料庫查詢是否已有此帳號
     * 有 -> 寫入 SP
     * 無 -> 寫入 DB
     */
    override fun detectIsAccountExsit(ID: String, loginType: String) {
        db.collection("personalData")
            .whereEqualTo("loginType", loginType)
            .whereEqualTo("id", ID)
            .limit(1).get()
            .addOnSuccessListener {
                mView?.finishLoadingView()
                if (it.documents.isNotEmpty()) {
                    // 曾經註冊過，刪除遊戲後重裝，SP紀錄已經被刪除
                    mView?.showErrorMessage("登入成功")
                    mView?.UiState(true)
                    SP.setPersonalData(context, it.documents[0].data?.toPersonalBean()!!)
                    setID(loginType, ID)
                } else {
                    // 從未註冊過，需先設置學校
                    mView?.showSchoolDialog()
                }
            }
    }


    override fun writeToDB(personalBean: PersonalBean) {
        db.collection("personalData").document(personalBean.id).set(personalBean)
            .addOnSuccessListener {
                // 存入SP
                setID(personalBean.loginType, personalBean.id)
                SP.setPersonalData(context, personalBean)

                mView?.finishLoadingView()
                mView?.showErrorMessage("註冊成功")
                mView?.UiState(true)
            }
            .addOnFailureListener {
                mView?.finishLoadingView()
                mView?.showErrorMessage("註冊失敗，請聯繫客服")
            }
    }

    private fun detectServer() {
        CloudFireStore.systemCheck(
            success = {

            },
            fail = { msg: String ->
                mView?.finishLoadingView()
                mView?.showErrorMessage(msg)
            })
    }

    private fun setID(loginType: String, id: String) {
        when (loginType) {
            "line" -> SP.setLastLineID(context, id)
            "facebook" -> SP.setLastFacebookID(context, id)
            "gmail" -> SP.setLastGmailID(context, id)
            else -> {
                mView?.showErrorMessage("錯誤的 Login Type")
            }
        }
    }


    override fun onDestory() {
        Log.d("empty", "onDestory")
    }

    override fun start() {
        Log.d("empty", "start")
    }


}