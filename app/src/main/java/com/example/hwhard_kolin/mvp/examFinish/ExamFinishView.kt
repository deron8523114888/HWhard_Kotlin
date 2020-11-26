package com.example.hwhard_kolin.mvp.examFinish

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.PRACTICE_MODE
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.RANK_MODE
import com.example.hwhard_kolin.bean.QuestionList
import com.example.hwhard_kolin.dialog.AnswerReportDialog
import com.example.hwhard_kolin.util.*
import kotlinx.android.synthetic.main.actitvity_exam_finish.*

class ExamFinishView : BaseMvpActivity<ExamFinishContract.Presenter>(), ExamFinishContract.View,
    View.OnClickListener, View.OnTouchListener {

    private val questionList by lazy {
        (intent.getBundleExtra("bundle")?.getSerializable("question") as QuestionList).questionList
    }

    private val examType by lazy { intent.getBundleExtra("bundle")?.getString("type") ?: PRACTICE_MODE }

    private val personalData by lazy { SP.getPersonalData(this) }

    // 【回報】-> 每個 Radio 的 Boolean 值
    private val reportList by lazy {
        arrayListOf(
            arrayListOf(false, false, false, false),
            arrayListOf(false, false, false, false),
            arrayListOf(false, false, false, false),
            arrayListOf(false, false, false, false),
            arrayListOf(false, false, false, false)
        )
    }

    // 【回報】-> 【其他】的內容
    var reportStringList = arrayListOf("", "", "", "", "")

    // 回報按鈕
    private val reportButtonList by lazy {
        listOf(
            btn_error_1,
            btn_error_2,
            btn_error_3,
            btn_error_4,
            btn_error_5
        )
    }

    // 圖片按鈕
    private val photoButtonList by lazy {
        listOf(
            btn_question_photo_1,
            btn_question_photo_2,
            btn_question_photo_3,
            btn_question_photo_4,
            btn_question_photo_5
        )
    }

    // 正確答案 TextView
    private val correctTextView by lazy {
        listOf(
            tv_correct_answer_1,
            tv_correct_answer_2,
            tv_correct_answer_3,
            tv_correct_answer_4,
            tv_correct_answer_5
        )
    }

    // 我的答案 TextView
    private val myTextView by lazy {
        listOf(
            tv_my_answer_1,
            tv_my_answer_2,
            tv_my_answer_3,
            tv_my_answer_4,
            tv_my_answer_5
        )
    }

    override fun initPresenter() {
        ExamFinishPresenter(this)
        mPresenter.updateExamScore(questionList)
    }

    override fun bindLayout(): Int {
        return R.layout.actitvity_exam_finish
    }

    override fun initView(p0: View?) {

        questionList.forEachIndexed { index, questionBean ->
            when (questionBean.type) {
                "Int" -> {
                    correctTextView[index].text = questionBean.answerTop
                    myTextView[index].text = questionBean.myAnswerTop
                }
                "Frac" -> {
                    correctTextView[index].text = getString(
                        R.string.frac_answer,
                        questionBean.answerTop,
                        questionBean.answerBottom
                    )
                    myTextView[index].text = getString(
                        R.string.frac_answer,
                        questionBean.myAnswerTop,
                        questionBean.myAnswerBottom
                    )
                }
            }
        }

        loadRankIcon()

    }

    override fun onClick(v: View?) {

        /**
         *  題目回報 Dialog
         */
        reportButtonList.forEachIndexed { index, button ->
            if (v == button) {
                AnswerReportDialog(index, this, reportList[index], reportStringList[index],
                    finish = { newList, newString ->
                        reportList[index] = newList
                        reportStringList[index] = newString
                    }).show()
            }
        }

        /**
         *  題目圖片 Dialog
         */
        photoButtonList.forEachIndexed { index, button ->
            if (v == button) {
                // Todo 從 questionList 裡面的 degree 跟 num 取得本地圖片檔案，並以 Dialog 顯示
                Toast.makeText(this, "尚未開放", Toast.LENGTH_SHORT).show()
            }
        }


        when (v) {

            btn_finish -> finish()

            btn_notes -> Toast.makeText(this, "尚未開放", Toast.LENGTH_SHORT).show()

        }


    }

    override fun showExamScore(score: Int) {
        tv_exam_score.text = score.toString()

        // 若為積分模式才走這裡
        if(examType == RANK_MODE) {
            mPresenter.updateRankScore(score)
        }
    }

    override fun showRankScore(score: Int) {

        val resultScore = personalData.score + score
        // 分數的動畫 + 最後分數呈現
        when {
            resultScore >= 100 -> {
                if (personalData.score == 100 && personalData.rank != 12) {
                    personalData.rank = personalData.rank + 1
                    tv_rank_score_animation.text = "0"
                    personalData.score = 0
                    loadRankIcon()
                    // 預設為【升階】
                    tv_level_state.visibility = View.VISIBLE
                } else {
                    tv_rank_score_animation.startScoreAnimation(personalData.score, 100)
                    personalData.score = 100
                }
            }
            resultScore <= 0 -> {
                if (personalData.score == 0 && personalData.rank != 1) {
                    personalData.rank = personalData.rank - 1
                    tv_rank_score_animation.text = "75"
                    personalData.score = 75
                    loadRankIcon()
                    // 改為 【降階】
                    tv_level_state.run {
                        visibility = View.VISIBLE
                        text = "降  階"
                        setTextColor(ContextCompat.getColor(context,R.color.levelDown))
                    }
                } else {
                    tv_rank_score_animation.startScoreAnimation(personalData.score, 0)
                    personalData.score = 0
                }
            }
            else -> {
                tv_rank_score_animation.startScoreAnimation(personalData.score, resultScore)
                personalData.score = resultScore
            }
        }

        // 本場成績影響分數數值
        tv_rank_score_change.run {
            when {
                score > 0 -> {
                    setTextColor(Color.GREEN)
                    text = "(+$score)"
                    personalData.win = personalData.win + 1
                }
                score < 0 -> {
                    setTextColor(Color.RED)
                    text = "($score)"
                    personalData.lose = personalData.lose + 1
                }
                // 基本上不會有 0 分的狀況
                score == 0 -> {
                    setTextColor(Color.GRAY)
                    text = "(0)"
                }
            }
        }

        CloudFireStore.updatePersonalData(personalData)
        SP.setPersonalData(this, personalData)


    }

    private fun loadRankIcon() {
        iv_rank.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                personalData.rank.toRankDrawable()
            )
        )
    }

    override fun initEvent() {
        btn_finish.setOnClickListener(this)
        btn_finish.setOnTouchListener(this)

        btn_notes.setOnClickListener(this)
        btn_notes.setOnTouchListener(this)

        reportButtonList.forEach {
            it.setOnClickListener(this)
        }

        photoButtonList.forEach {
            it.setOnClickListener(this)
        }

    }

    override fun isActive(): Boolean {
        return isFinishing
    }

    override fun setPresenter(p0: ExamFinishContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                v?.downAnimator()
            }
            MotionEvent.ACTION_UP -> {
                v?.upAnimator()
            }
        }

        return super.onTouchEvent(event)
    }

}