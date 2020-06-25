package com.example.hwhard_kolin.mvp.manu.fragment.exam

import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R

class ExamFragment : BaseMvpFragment<ExamContract.Presenter>(),
    ExamContract.View {


    override fun getLayoutResouceId(): Int {
        return R.layout.item_exam
    }

    override fun initPresenter() {
        ExamPresenter(this)
    }

    override fun isActive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPresenter(p0: ExamContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

}