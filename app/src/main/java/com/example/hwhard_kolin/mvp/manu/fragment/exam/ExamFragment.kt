package com.example.hwhard_kolin.mvp.manu.fragment.exam

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R
import kotlinx.android.synthetic.main.item_exam.*

class ExamFragment : BaseMvpFragment<ExamContract.Presenter>(),
    ExamContract.View, View.OnClickListener, View.OnTouchListener {


    override fun getLayoutResouceId(): Int {
        return R.layout.item_exam
    }

    override fun initPresenter() {
        ExamPresenter(this)
    }



    override fun onClick(v: View?) {

        if(v is Button){
            when(v.id){
                btn_chapter_practice.id -> {

                }

                btn_whole_book_practice.id -> {

                }

                btn_GSAT.id -> {

                }

                btn_rank.id -> {

                }
            }
        }
    }

    override fun initView() {
        btn_chapter_practice.setOnClickListener(this)
        btn_chapter_practice.setOnTouchListener(this)

        btn_whole_book_practice.setOnClickListener(this)
        btn_whole_book_practice.setOnTouchListener(this)

        btn_GSAT.setOnClickListener(this)
        btn_GSAT.setOnTouchListener(this)

        btn_rank.setOnClickListener(this)
        btn_rank.setOnTouchListener(this)
    }


    override fun isActive(): Boolean {
        return true
    }

    override fun setPresenter(p0: ExamContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

    fun downAnimation(v: View?) {
        val scale_x = ObjectAnimator.ofFloat(v, "ScaleX", 1f, 0.8f)
        val scale_y = ObjectAnimator.ofFloat(v, "ScaleY", 1f, 0.8f)
        val animatorSet_scale = AnimatorSet()
        animatorSet_scale.playTogether(scale_x, scale_y)
        animatorSet_scale.setDuration(200).start()
    }

    fun upAnimation(v: View?) {
        val scale_x = ObjectAnimator.ofFloat(v, "ScaleX", 0.8f, 1f)
        val scale_y = ObjectAnimator.ofFloat(v, "ScaleY", 0.8f, 1f)
        val animatorSet_scale = AnimatorSet()
        animatorSet_scale.playTogether(scale_x, scale_y)
        animatorSet_scale.setDuration(200).start()
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