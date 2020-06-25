package com.example.hwhard_kolin.mvp.manu.fragment.setting

import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R

class SettingFragment : BaseMvpFragment<SettingContract.Presenter>(),SettingContract.View {

    override fun getLayoutResouceId(): Int {
        return R.layout.item_setting
    }

    override fun initPresenter() {
        SettingPresenter(this)
    }

    override fun isActive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPresenter(p0: SettingContract.Presenter?) {
        presenter = checkNotNull(p0)
    }
}