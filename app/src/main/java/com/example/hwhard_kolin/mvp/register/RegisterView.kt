package com.example.hwhard_kolin.mvp.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.mvp.login.LoginView
import kotlinx.android.synthetic.main.activity_register_view.*

class RegisterView : BaseMvpActivity<RegisterContract.presenter>(),RegisterContract.view,View.OnClickListener,View.OnTouchListener {

    var country : MutableList<String> ?= null
    var city : MutableList<MutableList<String>> ?= null
    var school : MutableList<MutableList<MutableList<String>>> ?= null

    override fun initPresenter() {
        RegisterPresenter(this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_register_view
    }

    override fun initView(p0: View?) {

        btn_submit.let {
            it.setOnTouchListener(this)
            it.setOnClickListener(this)
        }

        btn_back_to_login.let {
            it.setOnTouchListener(this)
            it.setOnClickListener(this)
        }

        btn_school.let {
            it.setOnClickListener(this)
        }
    }

    override fun initData() {

        // region PickerVIew

        country = mutableListOf("台灣")
        city = mutableListOf(resources.getStringArray(R.array.city).toMutableList())
        school = mutableListOf(mutableListOf(
            resources.getStringArray(R.array.Keelung).toMutableList(),
            resources.getStringArray(R.array.Taipei).toMutableList(),
            resources.getStringArray(R.array.New_Taipei).toMutableList(),
            resources.getStringArray(R.array.Taoyuan).toMutableList(),
            resources.getStringArray(R.array.Hsinchu_County).toMutableList(),
            resources.getStringArray(R.array.Hsinchu).toMutableList(),
            resources.getStringArray(R.array.Miaoli).toMutableList(),
            resources.getStringArray(R.array.Taichung).toMutableList(),
            resources.getStringArray(R.array.Changhua).toMutableList(),
            resources.getStringArray(R.array.Nantou).toMutableList(),
            resources.getStringArray(R.array.Yunlin).toMutableList(),
            resources.getStringArray(R.array.Chiayi_County).toMutableList(),
            resources.getStringArray(R.array.Chiayi).toMutableList(),
            resources.getStringArray(R.array.Tainan).toMutableList(),
            resources.getStringArray(R.array.Kaohsiung).toMutableList(),
            resources.getStringArray(R.array.Pingtung).toMutableList()))

        // endregion
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun setPresenter(p0: RegisterContract.presenter?) {
        mPresenter = checkNotNull(p0)
    }

    override fun onClick(v: View?) {

        when(v){


            btn_submit -> {

                // Todo 先跟 Model 拿所有帳號
                // Todo 判斷資料庫是否有相同帳號
                // Todo 寫入資料庫

            }

            btn_back_to_login -> {

                startActivity(Intent(this,LoginView::class.java))
                finish()
            }

            btn_school -> {
                val pvOptions = OptionsPickerBuilder(this,
                    OnOptionsSelectListener { option1, option2, options3, v -> //返回的分别是三个级别的选中位置

                        val tx: String = country?.get(option1) + "->" +
                                city?.get(option1)?.get(option2) + "->" +
                                school?.get(option1)?.get(option2)?.get(options3)

                        tv_school.setText(school?.get(option1)?.get(option2)?.get(options3))
                        Toast.makeText(this,"選擇："+tx,Toast.LENGTH_LONG).show()
                    }).build<String>()

                pvOptions.setPicker(country, city, school)
                pvOptions.show()
            }

        }
    }

    fun dwonAnimation(v:View?){
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
                dwonAnimation(v)
            }
            MotionEvent.ACTION_UP -> {
                upAnimation(v)
            }
        }

        return false
    }


}
