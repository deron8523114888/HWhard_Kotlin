package com.example.hwhard_kolin.mvp.exam

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import co.bxvip.ui.tocleanmvp.base.BaseMvpActivity
import com.example.hwhard_kolin.PRACTICE_MODE
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.adapter.QuestionPagerAdapter
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.bean.QuestionList
import com.example.hwhard_kolin.dialog.SubmitDialog
import com.example.hwhard_kolin.mvp.examFinish.ExamFinishView
import com.example.hwhard_kolin.util.*
import kotlinx.android.synthetic.main.activity_exam.*

class ExamView : BaseMvpActivity<ExamContract.Presenter>(), ExamContract.View ,View.OnTouchListener{

    // 個人資料
    private var personalData: PersonalBean? = null

    val context = this

    var submitDialog:SubmitDialog ?= null

    /**
     *  時間設定 30 分鐘
     */
    private var countDownTimer = object : CountDownTimer(1800000, 1000) {
        override fun onFinish() {
            if(submitDialog?.isShowing!!) {
                submitDialog?.dismiss()
            }
            SubmitDialog(context,
                confirm = {
                    val intent = Intent(context,ExamFinishView::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("question",QuestionList(questionList))
                    bundle.putString("type",examType)
                    intent.putExtra("bundle",bundle)
                    startActivity(intent)
                    finish()
                },
                isTimeOut = true
            ).show()
        }

        override fun onTick(millisUntilFinished: Long) {
            tv_time.text = millisUntilFinished.toTimeType()
        }
    }

    private val botList by lazy { listOf(bot_1, bot_2, bot_3, bot_4, bot_5) }

    /**
     *  題目、答案
     */
    private val questionList by lazy {
        (intent.getBundleExtra("bundle")?.getSerializable("question") as QuestionList).questionList
    }

    private val examType by lazy { intent.getBundleExtra("bundle")?.getString("type") ?: PRACTICE_MODE }

    private var currentPosition = 0

    private val initAnswerList = listOf("⓵", "⓶", "⓷", "⓸", "⓹", "⓺", "⓻", "⓼", "⓽", "⓾")

    override fun initPresenter() {
        ExamPresenter(this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_exam
    }

    override fun initView(p0: View?) {

        /**
         *  按下提交顯示的 Dialog
         */
        submitDialog = SubmitDialog(context,
            confirm = {
                countDownTimer.cancel()
                val intent = Intent(this,ExamFinishView::class.java)
                val bundle = Bundle()
                bundle.putSerializable("question",QuestionList(questionList))
                bundle.putString("type",examType)
                intent.putExtra("bundle",bundle)
                startActivity(intent)
                finish()
            }
        )

        personalData = SP.getPersonalData(this)
        tv_name.text = personalData?.name

        initAnswer(0)


        vp_question.run {
            adapter = QuestionPagerAdapter(supportFragmentManager)
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    val num = position + 1
                    tv_question_num.text = "$num/5"
                    botState(position)

                    if (position == 4) {
                        btn_submit.visibility = View.VISIBLE
                    } else {
                        btn_submit.visibility = View.GONE
                    }

                    // 設置當前 position
                    currentPosition = position

                    // 設置答案格式
                    initAnswer(position)

                    // 將曾經輸入的答案放上
                    insertCacheAnswer()

                }
            })
        }

        countDownTimer.start()

    }

    override fun initEvent() {
        /**
         *  鍵盤初始化
         */
        initKeyboardClick()

        // 橡皮擦 -> 清空答案
        btn_clear.setOnClickListener {
            initClearClick()
        }

        // 提早交卷
        btn_submit.setOnClickListener {
            submitDialog?.show()
        }
    }

    override fun showErrorMessage(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoadingView() {
        pb_loding.visibility = View.VISIBLE
    }

    override fun finishLoadingView() {
        pb_loding.visibility = View.INVISIBLE
    }


    override fun isActive(): Boolean {
        return isFinishing
    }

    override fun setPresenter(p0: ExamContract.Presenter?) {
        mPresenter = checkNotNull(p0)
    }

    fun botState(position: Int) {
        botList.forEachIndexed { index, textView ->
            if (position == index) {
                textView.background = ContextCompat.getDrawable(this, R.drawable.vp_bot_select)
            } else {
                textView.background = ContextCompat.getDrawable(this, R.drawable.vp_bot_unselect)
            }
        }
    }

    /**
     *  依據正確答案初始化格式
     */
    fun initAnswer(position: Int) {

        // 清空
        tv_answer_top.text = ""
        tv_answer_mid.text = ""
        tv_answer_bottom.text = ""

        var top = ""
        var bottom = ""

        when (questionList[position].type) {
            // 整數
            "Int" -> {
                initAnswerList.forEachIndexed { index, s ->
                    if (index < questionList[position].answerTop.length) {
                        top += s
                    } else {
                        return@forEachIndexed
                    }
                }
                tv_answer_mid.text = top
            }
            // 分數
            "Frac" -> {
                val topLength = questionList[position].answerTop.length
                val bottomLength = questionList[position].answerBottom.length
                val total = topLength + bottomLength

                for (i in 0 until total) {
                    if (i < topLength) {
                        top += initAnswerList[i]
                    } else {
                        bottom += initAnswerList[i]
                    }
                }
                tv_answer_top.text = top
                tv_answer_mid.text = topLength.coerceAtLeast(bottomLength).fracLength()
                tv_answer_bottom.text = bottom
            }

            // 選擇
            "Select" -> {

            }
        }

    }

    /**
     *  填入答案
     */
    private fun enterAnswer(enterString: String) {

        when (questionList[currentPosition].type) {

            "Int" -> {

                val midLength = tv_answer_mid.text.length
                val midText = tv_answer_mid.text.toString()
                for (i in 0 until midLength) {
                    if (midText.contains(initAnswerList[i])) {
                        tv_answer_mid.text = midText.replace(initAnswerList[i], enterString)
                        questionList[currentPosition].myAnswerTop += enterString
                        Log.v("test__", "整數" + questionList[currentPosition].myAnswerTop)
                        return
                    }
                }
            }

            "Frac" -> {
                val topLength = tv_answer_top.length()
                val bottomLength = tv_answer_bottom.length()
                val total = topLength + bottomLength

                val topText = tv_answer_top.text.toString()
                val bottomText = tv_answer_bottom.text.toString()

                for (i in 0 until total) {
                    if (i < topLength) {
                        if (topText.contains(initAnswerList[i])) {
                            tv_answer_top.text = topText.replace(initAnswerList[i], enterString)
                            questionList[currentPosition].myAnswerTop += enterString
                            Log.v("test__", "分子" + questionList[currentPosition].myAnswerTop)
                            return
                        }
                    } else {
                        if (bottomText.contains(initAnswerList[i])) {
                            tv_answer_bottom.text =
                                bottomText.replace(initAnswerList[i], enterString)
                            questionList[currentPosition].myAnswerBottom += enterString
                            Log.v("test__", "分母" + questionList[currentPosition].myAnswerBottom)
                            return
                        }
                    }
                }

            }

            "Select" -> {

            }

        }

    }

    private fun insertCacheAnswer() {
        val topMyAnswer = questionList[currentPosition].myAnswerTop
        val bottomMyAnswer = questionList[currentPosition].myAnswerBottom

        // 清空
        questionList[currentPosition].myAnswerTop = ""
        questionList[currentPosition].myAnswerBottom = ""

        if (topMyAnswer.isNotEmpty()) {
            topMyAnswer.forEachIndexed { index, c ->
                enterAnswer(c.toString())
            }
        }
        if (bottomMyAnswer.isNotEmpty()) {
            bottomMyAnswer.forEachIndexed { index, c ->
                enterAnswer(c.toString())
            }
        }
    }

    private fun initKeyboardClick() {
        num_0.insertAnswerSetting ({ enterAnswer("0") },this)
        num_1.insertAnswerSetting ({ enterAnswer("1") },this)
        num_2.insertAnswerSetting ({ enterAnswer("2") },this)
        num_3.insertAnswerSetting ({ enterAnswer("3") },this)
        num_4.insertAnswerSetting ({ enterAnswer("4") },this)
        num_5.insertAnswerSetting ({ enterAnswer("5") },this)
        num_6.insertAnswerSetting ({ enterAnswer("6") },this)
        num_7.insertAnswerSetting ({ enterAnswer("7") },this)
        num_8.insertAnswerSetting ({ enterAnswer("8") },this)
        num_9.insertAnswerSetting ({ enterAnswer("9") },this)
        num_add_and_minus.insertAnswerSetting ({ enterAnswer("±") },this)
        num_minus.insertAnswerSetting ({ enterAnswer("-") },this)
    }

    private fun initClearClick() {
        initAnswer(currentPosition)

        when (questionList[currentPosition].type) {
            "Int" -> {
                questionList[currentPosition].myAnswerTop = ""
            }
            "Frac" -> {
                questionList[currentPosition].myAnswerTop = ""
                questionList[currentPosition].myAnswerBottom = ""
            }
            "Select" -> {
                questionList[currentPosition].myAnswerTop = ""
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            // 按下
            MotionEvent.ACTION_DOWN -> {
                if (v is Button) {
                    v.downAnimator()
                }
            }
            // 彈起
            MotionEvent.ACTION_UP -> {
                if (v is Button) {
                    v.upAnimator()
                }
            }
        }
        return super.onTouchEvent(event)
    }


    /**
     *  監聽倒退鍵
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "正在考試中，離開前請交卷", Toast.LENGTH_SHORT).show()
            return false
        }

        return super.onKeyDown(keyCode, event)
    }
}