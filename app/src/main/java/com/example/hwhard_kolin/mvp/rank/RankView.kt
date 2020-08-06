package com.example.hwhard_kolin.mvp.rank

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.adapter.RankAdapter
import kotlinx.android.synthetic.main.activity_rank.*

class RankView : BaseMvpActivity<RankContract.Presenter>(),RankContract.View{

    override fun initPresenter() {
        RankPresenter(this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_rank
    }

    override fun initView(p0: View?) {
        rv_rank.layoutManager = LinearLayoutManager(this)
        rv_rank.adapter = RankAdapter()
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun setPresenter(p0: RankContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }


}
