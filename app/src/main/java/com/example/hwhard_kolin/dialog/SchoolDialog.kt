package com.example.hwhard_kolin.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.util.NetWork
import kotlinx.android.synthetic.main.dialog_school.*

class SchoolDialog(private val confirm: (school: String) -> Unit) :
    DialogFragment() {


    var country: MutableList<String>? = null
    var city: MutableList<MutableList<String>>? = null
    var school: MutableList<MutableList<MutableList<String>>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_school, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPicker()
        initEvent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_fragment)
    }

    private fun initEvent() {


        btn_back.setOnClickListener {
            dismiss()
        }

        btn_confirm.setOnClickListener {
            val school = tv_school.text.toString()

            if (school.isEmpty()) {
                Toast.makeText(context, "請選擇學校", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!NetWork.detectNetWork()) {
                Toast.makeText(context,"網路不穩，請檢查網路",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dismiss()
            confirm.invoke(school)
        }

        tv_school.setOnClickListener {
            val pvOptions = OptionsPickerBuilder(
                context,
                OnOptionsSelectListener { option1, option2, options3, v -> //返回的分别是三个级别的选中位置
                    tv_school.text = school?.get(option1)?.get(option2)?.get(options3)
                })
                .setDecorView(rootView)
                .build<String>()

            pvOptions.setPicker(country, city, school)
            pvOptions.show()
        }
    }


    private fun initPicker() {
        country = mutableListOf("台灣")
        city = mutableListOf(resources.getStringArray(R.array.city).toMutableList())
        school = mutableListOf(
            mutableListOf(
                resources.getStringArray(R.array.Keelung).toMutableList(),
                resources.getStringArray(R.array.Taipei).toMutableList(),
                resources.getStringArray(R.array.New_Taipei).toMutableList(),
                resources.getStringArray(R.array.Taoyuan).toMutableList(),
                resources.getStringArray(R.array.Hsinchu_County).toMutableList(),
                resources.getStringArray(R.array.Hsinchu).toMutableList(),
                resources.getStringArray(R.array.Miaoli).toMutableList(),
                resources.getStringArray(R.array.Taichung).toMutableList(),
                resources.getStringArray(R.array.Changhua).toMutableList(),
                resources.getStringArray(R.array.Nantou).toMutableList(),
                resources.getStringArray(R.array.Yunlin).toMutableList(),
                resources.getStringArray(R.array.Chiayi_County).toMutableList(),
                resources.getStringArray(R.array.Chiayi).toMutableList(),
                resources.getStringArray(R.array.Tainan).toMutableList(),
                resources.getStringArray(R.array.Kaohsiung).toMutableList(),
                resources.getStringArray(R.array.Pingtung).toMutableList()
            )
        )
    }
}