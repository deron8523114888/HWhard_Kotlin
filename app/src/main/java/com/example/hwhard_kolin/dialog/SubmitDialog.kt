package com.example.hwhard_kolin.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.net.Network
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.LogPrinter
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.util.Mobile
import com.example.hwhard_kolin.util.NetWork
import com.example.hwhard_kolin.util.downAnimator
import com.example.hwhard_kolin.util.upAnimator
import kotlinx.android.synthetic.main.dialog_submit.*

class SubmitDialog(
    private val ctx: Context,
    private val confirm: () -> Unit,
    private val isTimeOut: Boolean = false
) : AlertDialog(ctx, R.style.dialog_fragment), View.OnTouchListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_submit)
        val param = window?.attributes
        param?.width = (Mobile.getScreenWidth() * .8f).toInt()
        param?.height = (Mobile.getScreenHeight(context) * .5f).toInt()
        window?.attributes = param

        btn_confirm.setOnTouchListener(this)
        btn_confirm.setOnClickListener {

            if (!NetWork.detectNetWork()) {
                Toast.makeText(context,"網路不穩",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            confirm.invoke()
            dismiss()
            pb_loding.visibility = View.VISIBLE
        }

        btn_cancel.setOnTouchListener(this)
        btn_cancel.setOnClickListener {
            dismiss()
        }

        if (isTimeOut) {

            tv_content.text = "時間到!!\n\n請確認您的網路是否保持良好連線狀態\n\n按下確定後將提交考卷! "

            btn_cancel.visibility = View.GONE

            setCanceledOnTouchOutside(false)
        }

    }



    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (v is Button) {
                    v.downAnimator()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (v is Button) {
                    v.upAnimator()
                }
            }
        }
        return if(event != null) super.onTouchEvent(event) else false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false
        }

        return super.onKeyDown(keyCode, event)
    }
}