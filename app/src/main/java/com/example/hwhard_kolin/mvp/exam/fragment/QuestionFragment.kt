package com.example.hwhard_kolin.mvp.exam.fragment

import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R
import kotlinx.android.synthetic.main.item_question.*


class QuestionFragment(private val position: Int) : BaseMvpFragment<QuestionContract.Presenter>(),
    QuestionContract.View {

    override fun initView() {
        val num = position + 1
        tv_num.text = num.toString()
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