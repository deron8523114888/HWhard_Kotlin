package com.example.hwhard_kolin.mvp.rank

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.adapter.RankAdapter
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.util.CloudFireStore
import com.example.hwhard_kolin.util.SP
import com.example.hwhard_kolin.util.toRank
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_rank.*

class RankView : BaseMvpActivity<RankContract.Presenter>(), RankContract.View {

    var personalData = PersonalBean()

    override fun initPresenter() {
        RankPresenter(this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_rank
    }

    override fun initView(p0: View?) {
        rv_rank.layoutManager = LinearLayoutManager(this)
        rv_rank.adapter = RankAdapter()

        personalData = SP.getPersonalData(this)
        tv_my_name.text = personalData.name
        tv_my_school.text = personalData.school
        tv_my_rank.text = personalData.rank.toRank()

        getRank()

    }

    fun getRank() {
        showLoadingView()
        CloudFireStore.getRankData { rankList ->
            finishLoadingView()
            rv_rank.adapter = RankAdapter(rankList)
        }
    }

    override fun initEvent() {
        iv_back.setOnClickListener { finish() }
    }

    override fun showLoadingView() {
        pb_loding.visibility = View.VISIBLE
    }

    override fun finishLoadingView() {
        pb_loding.visibility = View.GONE
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun setPresenter(p0: RankContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }


}
