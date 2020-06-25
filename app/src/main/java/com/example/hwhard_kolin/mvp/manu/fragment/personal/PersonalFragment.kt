package com.example.hwhard_kolin.mvp.manu.fragment.personal


import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.example.hwhard_kolin.R

class PersonalFragment : BaseMvpFragment<PersonalContract.Presenter>(),
    PersonalContract.View{


    override fun getLayoutResouceId(): Int {
        return R.layout.item_personal
    }

    override fun initPresenter() {
        PersonalPresenter(
            this
        )
    }


    override fun isActive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPresenter(p0: PersonalContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

    override fun initView() {
        super.initView()
    }

    override fun initDatas() {
        super.initDatas()
    }

    override fun initEvents() {
        super.initEvents()
    }




}