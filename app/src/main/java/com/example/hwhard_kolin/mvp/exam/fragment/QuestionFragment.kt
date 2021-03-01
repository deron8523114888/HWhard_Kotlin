package com.example.hwhard_kolin.mvp.exam.fragment

import android.graphics.drawable.Drawable
import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.bean.QuestionBean
import kotlinx.android.synthetic.main.item_question.*
import java.util.*


class QuestionFragment(private val bean: QuestionBean) :
    BaseMvpFragment<QuestionContract.Presenter>(),
    QuestionContract.View {

    override fun initView() {
        /// 上次進度：排列組合答案到 E 第十題
        val num = bean.num.toInt()
        val path = "question/${bean.chapter}/${bean.degree}/${num}.png"
        try {
            iv_question.setImageDrawable(Drawable.createFromStream(Objects.requireNonNull(activity?.assets?.open(path)), ""))
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    override fun getLayoutResouceId(): Int {
        return R.layout.item_question
    }

    override fun initPresenter() {
        QuestionPresenter(this)
    }

    override fun isActive(): Boolean {
        return mActivity.isFinishing
    }

    override fun setPresenter(p0: QuestionContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

}