package com.example.hwhard_kolin.mvp.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.bean.personalBean
import com.example.hwhard_kolin.mvp.manu.ManuView
import com.example.hwhard_kolin.mvp.model.sharePreference.SharePreference
import com.example.hwhard_kolin.mvp.register.RegisterView
import com.example.hwhard_kolin.util.CloudFireStore
import com.example.hwhard_kolin.util.NetWork
import kotlinx.android.synthetic.main.activity_login_view.*

class LoginView : BaseMvpActivity<LoginContract.presenter>(), LoginContract.view,
    View.OnClickListener, View.OnTouchListener {


    override fun initPresenter() {
        Log.v("test__", "initPresenter")
        LoginPresenter(this)
    }

    override fun bindLayout(): Int {
        Log.v("test__", "bindLayout")
        return R.layout.activity_login_view
    }


    override fun onClick(v: View?) {

        if (v is Button) {

            when (v.id) {
                R.id.bt_login -> {
                    mPresenter.detectLogin(et_account.text.toString(), et_password.text.toString())
                }
                R.id.bt_register -> {
                    startActivity(Intent(this, RegisterView::class.java))
                }
                else -> {
                    Toast.makeText(this, "None", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun initView(p0: View?) {


        // 初始化 SharePreference
        SharePreference.initContext(this)
//        SharePreference.storePersonalData(personalBean)


        bt_login.let {
            it.setOnClickListener(this)
            it.setOnTouchListener(this)
        }

        bt_register.let {
            it.setOnClickListener(this)
            it.setOnTouchListener(this)
        }
        iv_hard.setOnClickListener(this)
    }

    override fun initEvent() {

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        NetWork.setManager(connectivityManager)

    }

    override fun showErrorMessage(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun goManuView() {

        // Todo 從 DB拿 personalData 寫進 SharePreference

        startActivity(Intent(this, ManuView::class.java))
        finish()
    }

    override fun startLoadingView() {
        pb_loding.visibility = View.VISIBLE
    }

    override fun finishLoadingView() {
        pb_loding.visibility = View.INVISIBLE
    }


    override fun isActive(): Boolean {
        return true
    }

    override fun setPresenter(p0: LoginContract.presenter?) {
        mPresenter = checkNotNull(p0)
    }


    private fun downAnimation(v: View?) {
        val scaleX = ObjectAnimator.ofFloat(v, "ScaleX", 1f, 0.8f)
        val scaleY = ObjectAnimator.ofFloat(v, "ScaleY", 1f, 0.8f)
        val animatorSetScale = AnimatorSet()
        animatorSetScale.playTogether(scaleX, scaleY)
        animatorSetScale.setDuration(200).start()
    }

    private fun upAnimation(v: View?) {
        val scaleX = ObjectAnimator.ofFloat(v, "ScaleX", 0.8f, 1f)
        val scaleY = ObjectAnimator.ofFloat(v, "ScaleY", 0.8f, 1f)
        val animatorSetScale = AnimatorSet()
        animatorSetScale.playTogether(scaleX, scaleY)
        animatorSetScale.setDuration(200).start()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {


        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downAnimation(v)
            }
            MotionEvent.ACTION_UP -> {
                upAnimation(v)
            }
        }

        return super.onTouchEvent(event)
    }
}
