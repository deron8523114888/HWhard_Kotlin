package com.example.hwhard_kolin.mvp.manu.fragment.readyExam

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import co.bxvip.ui.tocleanmvp.base.BaseMvpFragment
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.mvp.exam.ExamView
import com.example.hwhard_kolin.util.*
import kotlinx.android.synthetic.main.dialog_school.*
import kotlinx.android.synthetic.main.item_exam.*

class ReadyExamFragment : BaseMvpFragment<ReadyExamContract.Presenter>(),
    ReadyExamContract.View, View.OnClickListener, View.OnTouchListener {

    var isClicking = false

    var picker1: MutableList<String>? = null
    var picker2: MutableList<MutableList<String>>? = null
    var picker3: MutableList<MutableList<MutableList<String>>>? = null

    /**
     *  picker 對應的 index
     *  藉由此 list 獲取對應題目 & 答案
     */
    var mExamModeList = listOf(-1, -1, -1)

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
                    OnOptionsSelectListener { option1, option2, options3, v -> //返回的分别是三个级别的选中位置
                        mExamModeList = listOf(option1, option2, options3)
                        tv_show_type.text =
                            "【當前選擇】\n\n" + picker1?.get(option1) + "-" + picker2?.get(option1)
                                ?.get(option2) + "-" + picker3?.get(option1)?.get(option2)
                                ?.get(options3)
                        mExamModeList.forEach {
                            Log.v("test__", it.toString())
                        }
                    })
                    .build<String>()

                pvOptions.setPicker(picker1, picker2, picker3)
                pvOptions.show()
            }
            // 開始考試
            btn_start_exam -> {
                getQuestionAndGoExam()
            }
        }

    }

    private fun getQuestionAndGoExam() {
        mExamModeList.forEach {
            if (it < 0) {
                Toast.makeText(context, "請選擇考試模式", Toast.LENGTH_SHORT).show()
                mExamModeList = listOf(-1, -1, -1)
                tv_show_type.text = ""
                return
            }
        }
        CloudFireStore.getRandomQuestionToSP {
            if (it.questionList.size == 5 && context != null) {

                val intent = Intent(context, ExamView::class.java)
                val bundle = Bundle()
                bundle.putSerializable("question", it)
                intent.putExtra("bundle", bundle)
                startActivity(intent)

                mExamModeList = listOf(-1,-1,-1)
                tv_show_type.text = ""
            }
        }
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
        picker1 = mutableListOf("章節練習", "全冊練習", "學測練習", "個人牌位")
        picker2 = mutableListOf(
            resources.getStringArray(R.array.Type1).toMutableList(),
            resources.getStringArray(R.array.Type1).toMutableList(),
            resources.getStringArray(R.array.Type3).toMutableList(),
            resources.getStringArray(R.array.Type4).toMutableList()
        )
        picker3 = mutableListOf(
            mutableListOf(
                resources.getStringArray(R.array.book1).toMutableList(),
                resources.getStringArray(R.array.book2).toMutableList(),
                resources.getStringArray(R.array.book3a).toMutableList(),
                resources.getStringArray(R.array.book3b).toMutableList(),
                resources.getStringArray(R.array.book4a).toMutableList(),
                resources.getStringArray(R.array.book4b).toMutableList()
            ),
            mutableListOf(
                mutableListOf("第一冊測驗"),
                mutableListOf("第二冊測驗"),
                mutableListOf("第三冊(A)測驗"),
                mutableListOf("第三冊(B)測驗"),
                mutableListOf("第四冊(A)測驗"),
                mutableListOf("第四冊(B)測驗")
            ),
            mutableListOf(
                resources.getStringArray(R.array.Type3).toMutableList()
            ),
            mutableListOf(
                resources.getStringArray(R.array.Type4).toMutableList()
            )
        )
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


}