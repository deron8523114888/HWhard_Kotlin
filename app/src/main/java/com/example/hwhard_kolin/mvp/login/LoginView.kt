package com.example.hwhard_kolin.mvp.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.bean.AnswerBean
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.dialog.SchoolDialog
import com.example.hwhard_kolin.mvp.manu.ManuView
import com.example.hwhard_kolin.util.*
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.linecorp.linesdk.LineApiResponseCode
import com.linecorp.linesdk.auth.LineLoginApi
import kotlinx.android.synthetic.main.activity_login_view.*


class LoginView : BaseMvpActivity<LoginContract.Presenter>(), LoginContract.View,
    View.OnClickListener, View.OnTouchListener {

    var personalBean = PersonalBean()

    // fb callback
    private var callBack = CallbackManager.Factory.create()
    val context = this
    override fun initPresenter() {
        LoginPresenter(this, this)

    }

    override fun bindLayout(): Int {
        return R.layout.activity_login_view
    }

    override fun initView(p0: View?) {
        /**
         *  更新答案到資料庫使用 -> 須從答案庫撈檔案過來再 run
         */
//        newQ("line_circle", "E", mapOf())
    }

    private fun newQ(chapter: String, degree: String, map: Map<String, Map<String, String>>) {
        CloudFireStore.newQuestion(chapter = chapter, degree = degree, answer = map)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_line -> {
                if (!NetWork.detectNetWork()) {
                    showErrorMessage("網路不穩，請檢查網路")
                    return
                }
                showLoadingView()
                // 從 SP 判斷，是否曾經用 line 登入過
                val lineID = SP.getLastLineID(context)
                if (lineID.isNotEmpty()) {
                    mPresenter.detectIsAccountExsit(ID = lineID, loginType = "line")
                } else {
                    mPresenter.loginToLine()
                }
            }

            btn_fb -> {
                if (!NetWork.detectNetWork()) {
                    showErrorMessage("網路不穩，請檢查網路")
                    return
                }
                showLoadingView()

                val facebookID = SP.getLastFacebookID(context)
                if (facebookID.isNotEmpty()) {
                    mPresenter.detectIsAccountExsit(facebookID, "facebook")
                } else {
                    mPresenter.loginToFB(callBack)
                }
            }
            btn_gmail -> {
                if (!NetWork.detectNetWork()) {
                    showErrorMessage("網路不穩，請檢查網路")
                    return
                }
                showLoadingView()
                val gmailID = SP.getLastGmailID(context)
                if (gmailID.isNotEmpty()) {
                    mPresenter.detectIsAccountExsit(gmailID, "gmail")
                } else {
                    mPresenter.loginToGmail()
                }
            }

            btn_join_game -> {
                startActivity(Intent(this, ManuView::class.java))
                finish()
            }

            tv_back_to_login -> {
                UiState(false)
            }

            // 未設定的 Button
            else -> {
                Toast.makeText(this, "尚未開啟功能", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun initEvent() {

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        NetWork.setManager(connectivityManager)

        btn_line.setOnClickListener(this)
        btn_line.setOnTouchListener(this)

        btn_fb.setOnClickListener(this)
        btn_fb.setOnTouchListener(this)

        btn_gmail.setOnClickListener(this)
        btn_gmail.setOnTouchListener(this)

        btn_join_game.setOnClickListener(this)
        btn_join_game.setOnTouchListener(this)

        tv_back_to_login.setOnClickListener(this)

    }

    override fun showSchoolDialog() {
        // 選擇完學校的 CallBack function
        SchoolDialog { school ->

            showLoadingView()

            // 設置學校
            personalBean.school = school

            // 寫入 DB
            mPresenter.writeToDB(personalBean)

        }.show(supportFragmentManager, "")
    }


    // SP 沒紀錄，才有可能跑到這
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        finishLoadingView()
        when (requestCode) {

            /**
             *  LINE 回調
             */
            Constants.LINE_REQUEST_CODE -> {
                val result = LineLoginApi.getLoginResultFromIntent(data)
                when (result.responseCode) {
                    LineApiResponseCode.SUCCESS -> {

                        val accessToken = result.lineCredential!!.accessToken.tokenString
                        val profile = result.lineProfile

                        // 將資料暫存
                        personalBean = PersonalBean(
                            name = profile?.displayName.toString(),
                            id = profile?.userId.toString(),
                            url = profile?.pictureUrl.toString(),
                            loginType = "line"
                        )

                        mPresenter.detectIsAccountExsit(
                            ID = profile?.userId.toString(),
                            loginType = "line"
                        )

                    }
                    LineApiResponseCode.CANCEL -> {
                        showErrorMessage("無權限登入")
                    }
                    else -> {
                        Log.v("test__", result.responseCode.toString());
                        showErrorMessage("LINE登入 - 未知錯誤")
                    }
                }
            }

            /**
             *  Facebook
             */
            Constants.FB_REQUEST_CODE -> {
                callBack?.onActivityResult(requestCode, resultCode, data)
                object : ProfileTracker() {
                    override fun onCurrentProfileChanged(
                        oldProfile: Profile?,
                        currentProfile: Profile?
                    ) {
                        personalBean = PersonalBean(
                            name = currentProfile?.name.toString(),
                            id = currentProfile?.id.toString(),
                            url = currentProfile?.getProfilePictureUri(50, 50).toString(),
                            loginType = "facebook"
                        )

                        mPresenter.detectIsAccountExsit(
                            ID = currentProfile?.id.toString(),
                            loginType = "facebook"
                        )
                    }
                }
            }

            Constants.GMAIL_REQUEST_CODE -> {

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val result = task.getResult(ApiException::class.java)

                    personalBean = PersonalBean(
                        name = result?.displayName.toString(),
                        id = result?.id.toString(),
                        url = result?.photoUrl.toString(),
                        loginType = "gmail"
                    )

                    mPresenter.detectIsAccountExsit(
                        ID = result?.id.toString(),
                        loginType = "gmail"
                    )

                } catch (e: ApiException) {
                    Toast.makeText(context, "GMAIL 回傳格式錯誤", Toast.LENGTH_LONG).show()
                }

            }

            else -> {
                showErrorMessage("未知的 Request code")
            }

        }

    }


    override fun showErrorMessage(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun UiState(canJoinGame: Boolean) {
        if (!canJoinGame) {
            ll_regist_UI.visibility = View.VISIBLE
            btn_join_game.visibility = View.GONE
            tv_back_to_login.visibility = View.GONE
            return
        }

        ll_regist_UI.visibility = View.GONE
        btn_join_game.visibility = View.VISIBLE
        tv_back_to_login.visibility = View.VISIBLE

    }

    override fun goToManu() {
        startActivity(Intent(this, ManuView::class.java))
        finish()
    }

    override fun showLoadingView() {
        pb_loding.visibility = View.VISIBLE
    }

    override fun finishLoadingView() {
        pb_loding.visibility = View.INVISIBLE
    }

    override fun getAct(): LoginView {
        return this
    }


    override fun isActive(): Boolean {
        return true
    }

    override fun setPresenter(p0: LoginContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {
            // 按下
            MotionEvent.ACTION_DOWN -> {
                v?.downAnimator()
            }
            // 彈起
            MotionEvent.ACTION_UP -> {
                v?.upAnimator()
            }
        }

        return super.onTouchEvent(event)
    }
}
