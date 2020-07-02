package com.example.hwhard_kolin.mvp.rank

import android.view.View
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.R

class RankView : BaseMvpActivity<RankContract.Presenter>(),RankContract.View{

    override fun initPresenter() {
        RankPresenter(this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_rank
    }

    override fun initView(p0: View?) {
        //TODO("Not yet implemented")
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun setPresenter(p0: RankContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }


}
