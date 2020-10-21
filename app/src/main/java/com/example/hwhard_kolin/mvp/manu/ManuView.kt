package com.example.hwhard_kolin.mvp.manu

import android.graphics.Color
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.adapter.ManuPagerAdapter
import com.example.hwhard_kolin.R
import kotlinx.android.synthetic.main.activity_manu.*

class ManuView : BaseMvpActivity<ManuContract.Presenter>(), ManuContract.View {



    override fun initPresenter() {
        ManuPresenter(this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_manu
    }

    override fun initView(p0: View?) {

        // 開發用
//        startActivity(Intent(this,ExamView::class.java))

        vp_manu.adapter = ManuPagerAdapter(supportFragmentManager)

        pst_manu.run {

            // 是否平分螢幕
            shouldExpand = false

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

    override fun initData() {

    }

    override fun isActive(): Boolean {
        return isFinishing
    }

    override fun setPresenter(p0: ManuContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }

    /**
     *  監聽倒退鍵
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Todo 跳出 Dialog 詢問是否關閉 APP
            Toast.makeText(this, "倒退鍵", Toast.LENGTH_SHORT).show()
            return false
        }

        return super.onKeyDown(keyCode, event)
    }
}
