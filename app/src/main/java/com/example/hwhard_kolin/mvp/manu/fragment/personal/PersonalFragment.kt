package com.example.hwhard_kolin.mvp.manu.fragment.personal


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.bean.personalBean
import com.example.hwhard_kolin.mvp.manu.ManuView
import com.example.hwhard_kolin.mvp.model.sharePreference.SharePreference
import com.example.hwhard_kolin.mvp.rank.RankView
import kotlinx.android.synthetic.main.item_personal.*

class PersonalFragment : BaseMvpFragment<PersonalContract.Presenter>(),
    PersonalContract.View, View.OnTouchListener, View.OnClickListener {

    var personalBean = SharePreference.getPersonalData()

    override fun getLayoutResouceId(): Int {
        return R.layout.item_personal
    }

    override fun initPresenter() {
        Log.v("test_", "PersonalFragment_initPresenter")
        PersonalPresenter(this)
    }

    override fun onClick(v: View?) {

        // Todo fix Fragment 內按鈕失效

        Log.v("test_", "onClick")
        if (v is Button) {
            when (v.id) {
                R.id.btn_rank -> startActivity(Intent(activity, RankView::class.java))
                R.id.btn_wrong_text -> Toast.makeText(
                    SharePreference.context,
                    "尚未有錯誤本功能",
                    Toast.LENGTH_LONG
                ).show()
                R.id.btn_announcement -> Toast.makeText(
                    SharePreference.context,
                    "尚未有公告功能",
                    Toast.LENGTH_LONG
                ).show()
                else -> {
                    Toast.makeText(activity, "未知按鈕", Toast.LENGTH_LONG)
                }
            }
        }
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun setPresenter(p0: PersonalContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

    override fun initView() {
        tv_personal_name.text = personalBean?.name
        tv_personal_school.text = personalBean?.school
        tv_personal_id.text = getString(R.string.id,personalBean?.id)
        tv_personal_rank.text = getString(R.string.rank,personalBean?.rank)
        tv_personal_score.text = getString(R.string.score,personalBean?.score.toString())
        tv_personal_win.text = getString(R.string.win,personalBean?.win.toString())
        tv_personal_lose.text = getString(R.string.lose,personalBean?.lose.toString())


        btn_rank.setOnClickListener(this)
        btn_rank.setOnTouchListener(this)

        btn_wrong_text.setOnClickListener(this)
        btn_wrong_text.setOnTouchListener(this)

        btn_announcement.setOnClickListener(this)
        btn_announcement.setOnTouchListener(this)

    }

    override fun initDatas() {
        super.initDatas()
    }

    override fun initEvents() {
        super.initEvents()
    }

    fun downAnimation(v: View?) {
        val scaleX = ObjectAnimator.ofFloat(v, "ScaleX", 1f, 0.8f)
        val scaleY = ObjectAnimator.ofFloat(v, "ScaleY", 1f, 0.8f)
        val animatorSetScale = AnimatorSet()
        animatorSetScale.playTogether(scaleX, scaleY)
        animatorSetScale.setDuration(200).start()
    }

    fun upAnimation(v: View?) {
        val scaleX = ObjectAnimator.ofFloat(v, "ScaleX", 0.8f, 1f)
        val scaleY = ObjectAnimator.ofFloat(v, "ScaleY", 0.8f, 1f)
        val animatorSetScale = AnimatorSet()
        animatorSetScale.playTogether(scaleX, scaleY)
        animatorSetScale.setDuration(200).start()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {

            //按下
            MotionEvent.ACTION_DOWN -> {
                downAnimation(v)
            }
            // 移出畫面 放開
            MotionEvent.ACTION_HOVER_EXIT -> {
                upAnimation(v)
            }
            // 彈起
            MotionEvent.ACTION_UP -> {
                upAnimation(v)
            }
        }

        // 若為 true 則不會響應其他動作 ( 不會響應 onClick listener )
        return false
    }


}