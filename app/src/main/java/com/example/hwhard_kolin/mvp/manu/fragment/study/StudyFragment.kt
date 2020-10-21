package com.example.hwhard_kolin.mvp.manu.fragment.study

import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R

class StudyFragment : BaseMvpFragment<StudyContract.Presenter>(),
    StudyContract.View {



    override fun getLayoutResouceId(): Int {
        return R.layout.item_study
    }

    override fun initPresenter() {
        StudyPresenter(this)
    }

    override fun isActive(): Boolean {
        return mActivity.isFinishing
    }

    override fun setPresenter(p0: StudyContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

}