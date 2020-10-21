package com.example.hwhard_kolin.mvp.examFinish

import android.view.View
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.R

class ExamFinishView :BaseMvpActivity<ExamFinishContract.Presenter>(),ExamFinishContract.View{

    override fun initPresenter() {
        ExamFinishPresenter(this)
    }

    override fun bindLayout(): Int {
        return R.layout.actitvity_exam_finish
    }

    override fun initView(p0: View?) {


    }

    override fun isActive(): Boolean {
        return isFinishing
    }

    override fun setPresenter(p0: ExamFinishContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }

}