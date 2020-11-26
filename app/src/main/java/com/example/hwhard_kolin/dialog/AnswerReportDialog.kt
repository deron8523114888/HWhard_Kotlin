package com.example.hwhard_kolin.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.example.hwhard_kolin.R
import kotlinx.android.synthetic.main.dialog_answer_response.*

class AnswerReportDialog(
    private val index: Int,
    ctx: Context,
    private var list: ArrayList<Boolean>,
    private val others: String,
    val finish: (newList: ArrayList<Boolean>, newString: String) -> Unit
) :
    Dialog(ctx, R.style.dialog_fragment) {

    private val rbList: List<RadioButton> by lazy { listOf(rb1, rb2, rb3, rb4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_answer_response)
        initView()
        initEvent()
    }

    private fun initView() {
        initRadioButton()
        et_text.setText(others)
        val num = index + 1
        tv_title.text = "($num) 題目回報"
    }

    private fun initEvent() {

        rbList.forEachIndexed { index, radioButton ->
            radioButton.setOnClickListener {
                list[index] = !list[index]
                radioButton.isChecked = list[index]
            }
        }

        btn_finish.setOnClickListener {
            dismiss()
            finish.invoke(list, et_text.text.toString())
        }

    }

    private fun initRadioButton() {
        rbList.forEachIndexed { index, radioButton ->
            radioButton.isChecked = list[index]
        }
    }

}