package com.example.hwhard_kolin.mvp.manu

import android.graphics.Color
import android.view.View
import androidx.core.view.marginTop
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.ManuPagetAdapter
import com.example.hwhard_kolin.R
import kotlinx.android.synthetic.main.activity_manu.*

class ManuView : BaseMvpActivity<ManuContract.Presenter>(),ManuContract.View {

    override fun initPresenter() {
        ManuPresenter(this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_manu
    }

    override fun initView(p0: View?) {
        vp_manu.adapter = ManuPagetAdapter(supportFragmentManager)

        pst_manu.run {

            // 是否平分螢幕
            setShouldExpand(false)

            // 文字大小
            textSize = 50

            // 文字顏色
            textColor = Color.WHITE

            // title 選中物件的 底線顏色
            indicatorColor = Color.parseColor("#FF9224")

            // 整個 bar 的底線顏色
            underlineColor = Color.parseColor("#FF359A")

            // Tab 綁定 ViewPager
            setViewPager(vp_manu)

        }

    }

    override fun isActive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPresenter(p0: ManuContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }


}
