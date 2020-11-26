package com.example.hwhard_kolin.mvp.manu.fragment.personal


import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.bumptech.glide.Glide
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.dialog.AnnouncementDialog
import com.example.hwhard_kolin.mvp.rank.RankView
import com.example.hwhard_kolin.util.*
import kotlinx.android.synthetic.main.item_personal.*

class PersonalFragment : BaseMvpFragment<PersonalContract.Presenter>(),
    PersonalContract.View, View.OnTouchListener, View.OnClickListener {

    private var personalBean = PersonalBean()

    override fun getLayoutResouceId(): Int {
        return R.layout.item_personal
    }

    override fun initPresenter() {
        PersonalPresenter(this)
    }

    override fun onClick(v: View?) {

        if (v is Button) {
            when (v.id) {
                R.id.btn_rank -> startActivity(Intent(activity, RankView::class.java))
                R.id.btn_wrong_text -> Toast.makeText(context, "尚未有錯誤本功能", Toast.LENGTH_LONG).show()
                R.id.btn_announcement -> AnnouncementDialog().show(childFragmentManager,"")
                else -> {
                    Toast.makeText(activity, "未知按鈕", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun isActive(): Boolean {
        return mActivity.isFinishing
    }

    override fun setPresenter(p0: PersonalContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

    override fun initView() {}

    override fun lazyLoad() {
        context?.let {
            personalBean = SP.getPersonalData(it)
            iv_rank.setImageDrawable(ContextCompat.getDrawable(it,personalBean.rank.toRankDrawable()))
        }

        tv_personal_name.text = personalBean.name
        tv_personal_school.text = personalBean.school
        tv_personal_id.text = getString(R.string.id, "ID位置")
        tv_personal_rank.text = getString(R.string.rank, personalBean.rank.toRank())
        tv_personal_score.text = getString(R.string.score, personalBean.score.toString())
        tv_personal_win.text = getString(R.string.win, personalBean.win.toString())
        tv_personal_lose.text = getString(R.string.lose, personalBean.lose.toString())

        iv_icon.borderWidth = 0
        Glide.with(this)
            .load(personalBean.url)
            .error(R.drawable.empty_icon)
            .into(iv_icon)
    }

    override fun initEvents() {
        btn_rank.setOnClickListener(this)
        btn_rank.setOnTouchListener(this)

        btn_wrong_text.setOnClickListener(this)
        btn_wrong_text.setOnTouchListener(this)

        btn_announcement.setOnClickListener(this)
        btn_announcement.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {
            //按下
            MotionEvent.ACTION_DOWN -> {
                if (v is Button) {
                    v.downAnimator()
                }
            }
            // 移出畫面 放開
            MotionEvent.ACTION_HOVER_EXIT -> {

            }
            // 彈起
            MotionEvent.ACTION_UP -> {
                if (v is Button) {
                    v.upAnimator()
                }
            }
        }

        // 若為 true 則不會響應其他動作 ( 不會響應 onClick listener )
        return false
    }


}