package com.example.hwhard_kolin.mvp.manu.fragment.readyExam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.example.hwhard_kolin.PRACTICE_MODE
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.RANK_MODE
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.mvp.exam.ExamView
import com.example.hwhard_kolin.util.*
import kotlinx.android.synthetic.main.item_exam.*

class ReadyExamFragment : BaseMvpFragment<ReadyExamContract.Presenter>(),
    ReadyExamContract.View, View.OnClickListener, View.OnTouchListener {

    private var isClicking = false

    private var picker1: MutableList<String>? = null
    private var picker2: MutableList<MutableList<String>>? = null
    private var picker3: MutableList<MutableList<MutableList<String>>>? = null


    /**
     *  picker 對應的 index
     *  藉由此 list 獲取對應題目 & 答案
     */
    private var mExamModeList = listOf(-1, -1, -1)

    private val personalBean by lazy { SP.getPersonalData(context!!) }

    override fun getLayoutResouceId(): Int {
        return R.layout.item_exam
    }

    override fun initPresenter() {
        ReadyExamPresenter(this)
    }

    override fun initView() {
        initPicker()
    }

    override fun onClick(v: View?) {

        if (isClicking) {
            return
        }

        when (v) {
            // 選擇考試模式
            btn_choose -> {
                val pvOptions = OptionsPickerBuilder(
                    context,
                    OnOptionsSelectListener { option1, option2, options3, _ -> //返回的分别是三个级别的选中位置
                        mExamModeList = listOf(option1, option2, options3)
                        // 顯示選擇的文字
                        tv_show_type.text = getString(
                            R.string.picker_string,
                            picker1?.get(option1),
                            picker2?.get(option1)?.get(option2),
                            picker3?.get(option1)?.get(option2)?.get(options3)
                        )
                    })
                    .build<String>()

                pvOptions.setPicker(picker1, picker2, picker3)
                pvOptions.show()
            }
            // 開始考試
            btn_start_exam -> {
                detect()
            }
        }
    }

    /**
     *  判斷是否有選擇
     *  判斷積分模式是否開啟
     */
    private fun detect() {
        mExamModeList.forEach {
            if (it < 0) {
                Toast.makeText(context, "請選擇考試模式", Toast.LENGTH_SHORT).show()
                mExamModeList = listOf(-1, -1, -1)
                tv_show_type.text = ""
                return
            }
        }
        // 取得題目
        getQuestion()
    }

    /**
     *  取得題目 and 開始考試
     */
    private fun getQuestion() {
        // 針對不同模式
        when (mExamModeList[0]) {
            0 -> presenter.goChapter(mExamModeList)
            1 -> presenter.goVolume(mExamModeList)
            2 -> presenter.goGSAT(mExamModeList)
            3 -> {
                CloudFireStore.rankCheck(
                    success = { presenter.goRank(mExamModeList) },
                    fail = {
                        showErrorMessage(it)
                    }
                )
            }
            else -> {
            }
        }
    }

    override fun goExam(bundle: Bundle) {

        val intent = Intent(context, ExamView::class.java)
        intent.putExtra("bundle", bundle)
        startActivity(intent)

        mExamModeList = listOf(-1, -1, -1)
        tv_show_type.text = ""

    }


    override fun lazyLoad() {
        tv_show_type.text = ""
        mExamModeList = listOf(-1, -1, -1)
    }

    override fun initEvents() {
        btn_choose.setOnClickListener(this)
        btn_choose.setOnTouchListener(this)

        btn_start_exam.setOnClickListener(this)
        btn_start_exam.setOnTouchListener(this)
    }

    private fun initPicker() {
        // 第一層
        picker1 = mutableListOf("章節練習", "全冊練習", "學測練習", "個人牌位")

        // 第二層
        picker2 = mutableListOf(
            // 章節練習
            resources.getStringArray(R.array.Type1).toMutableList(),
            // 全冊練習
            resources.getStringArray(R.array.Type1).toMutableList(),
            // 學測練習
            resources.getStringArray(R.array.Type3).toMutableList(),
            // 個人牌位
            resources.getStringArray(R.array.Type4).toMutableList()
        )

        // 第三層
        picker3 = mutableListOf(

            // 章節練習
            mutableListOf(
                // 第一冊
                resources.getStringArray(R.array.book1).toMutableList(),
                // 第二冊
                resources.getStringArray(R.array.book2).toMutableList(),
                // 第三冊(A)
                resources.getStringArray(R.array.book3a).toMutableList(),
                // 第三冊(B)
                resources.getStringArray(R.array.book3b).toMutableList(),
                // 第四冊(A)
                resources.getStringArray(R.array.book4a).toMutableList(),
                // 第四冊(B)
                resources.getStringArray(R.array.book4b).toMutableList()
            ),
            // 全冊練習
            mutableListOf(
                mutableListOf("第一冊測驗"),
                mutableListOf("第二冊測驗"),
                mutableListOf("第三冊(A)測驗"),
                mutableListOf("第三冊(B)測驗"),
                mutableListOf("第四冊(A)測驗"),
                mutableListOf("第四冊(B)測驗")
            ),
            // 學測練習
            mutableListOf(mutableListOf("(A)學測練習"), mutableListOf("(B)學測練習")),
            // 個人牌位
            mutableListOf(mutableListOf("(A)個人牌位"), mutableListOf("(B)個人牌位"))
        )
    }

    override fun getPersonData(): PersonalBean {
        return personalBean
    }

    override fun isActive(): Boolean {
        return mActivity.isFinishing
    }

    override fun setPresenter(p0: ReadyExamContract.Presenter?) {
        presenter = checkNotNull(p0)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {
            //按下
            MotionEvent.ACTION_DOWN -> {
                v?.downAnimator()
            }

            // 彈起
            MotionEvent.ACTION_UP -> {
                v?.upAnimator()
            }
        }

        // 若為 true 則不會響應其他動作 ( 不會響應 onClick listener )
        return false
    }

    override fun showErrorMessage(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

}