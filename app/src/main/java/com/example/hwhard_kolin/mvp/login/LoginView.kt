package com.example.hwhard_kolin.mvp.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.mvp.manu.ManuView
import com.example.hwhard_kolin.mvp.register.RegisterView
import kotlinx.android.synthetic.main.activity_login_view.*

class LoginView : BaseMvpActivity<LoginContract.presenter>(),LoginContract.view,View.OnClickListener,View.OnTouchListener {



    override fun onClick(v: View?) {

        if(v is Button){

            when(v.id){
                R.id.bt_login ->{
                    mPresenter.detectLogin(et_account.text.toString(),et_password.text.toString())
                }
                R.id.bt_register ->{
                    startActivity(Intent(this,RegisterView::class.java))
                    finish()
                }
                else ->{
                    Toast.makeText(this,"None",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    override fun initPresenter() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        LoginPresenter(this,connectivityManager)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_login_view
    }

    override fun initView(p0: View?) {

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

    override fun showErrorMessage(error: String) {
        tv_error.setText(error)
    }

    override fun goManuView() {
        startActivity(Intent(this,ManuView::class.java))
        finish()
    }


    override fun isActive(): Boolean {
       return true
    }

    override fun setPresenter(p0: LoginContract.presenter?) {
        mPresenter = checkNotNull(p0)
    }



    fun downAnimation(v:View?){
        val scale_x = ObjectAnimator.ofFloat(v, "ScaleX", 1f, 0.8f)
        val scale_y = ObjectAnimator.ofFloat(v, "ScaleY", 1f, 0.8f)
        val animatorSet_scale = AnimatorSet()
        animatorSet_scale.playTogether(scale_x, scale_y)
        animatorSet_scale.setDuration(200).start()
    }

    fun upAnimation(v:View?){
        val scale_x = ObjectAnimator.ofFloat(v, "ScaleX", 0.8f, 1f)
        val scale_y = ObjectAnimator.ofFloat(v, "ScaleY", 0.8f, 1f)
        val animatorSet_scale = AnimatorSet()
        animatorSet_scale.playTogether(scale_x, scale_y)
        animatorSet_scale.setDuration(200).start()
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
