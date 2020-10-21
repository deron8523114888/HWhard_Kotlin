package com.example.hwhard_kolin.mvp.manu.fragment.setting

import android.content.Intent
import android.view.MotionEvent
import android.view.View
import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.mvp.login.LoginView
import com.example.hwhard_kolin.util.downAnimator
import com.example.hwhard_kolin.util.upAnimator
import com.facebook.login.Login
import kotlinx.android.synthetic.main.item_setting.*

class SettingFragment : BaseMvpFragment<SettingContract.Presenter>(), SettingContract.View,
    View.OnClickListener, View.OnTouchListener {

    override fun getLayoutResouceId(): Int {
        return R.layout.item_setting
    }

    override fun initPresenter() {
        SettingPresenter(this)
    }


    override fun onClick(v: View?) {
        when (v) {
            btn_logout -> {
                startActivity(Intent(context, LoginView::class.java))
                activity?.finish()
            }
        }
    }

    override fun initEvents() {

        btn_logout.setOnClickListener(this)
        btn_logout.setOnTouchListener(this)
    }

    override fun isActive(): Boolean {
        return mActivity.isFinishing
    }

    override fun setPresenter(p0: SettingContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                v?.downAnimator()
            }
            MotionEvent.ACTION_UP -> {
                v?.upAnimator()
            }
        }

        return false
    }
}