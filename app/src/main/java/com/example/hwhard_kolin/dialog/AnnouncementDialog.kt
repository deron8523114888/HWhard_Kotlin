package com.example.hwhard_kolin.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.hwhard_kolin.R
import kotlinx.android.synthetic.main.dialog_announcement.*

class AnnouncementDialog : DialogFragment() {

    private var titleState = listOf(true, false, false)

    private val titleTextView by lazy {
        listOf(
            announcement_title_1,
            announcement_title_2,
            announcement_title_3
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_announcement, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.dialog_fragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        announcement_content_OK.setOnClickListener {
            dismiss()
        }

        announcement_title_1.setOnClickListener {
            titleState = listOf(true, false, false)
            titleColor()
            view.findViewById<TextView>(R.id.announcement_content_text).text =
                getString(R.string.announcemetn_content_1)
        }

        announcement_title_2.setOnClickListener {
            titleState = listOf(false, true, false)
            titleColor()
            view.findViewById<TextView>(R.id.announcement_content_text).text =
                getString(R.string.announcemetn_content_2)
        }

        announcement_title_3.setOnClickListener {
            titleState = listOf(false, false, true)
            titleColor()
            view.findViewById<TextView>(R.id.announcement_content_text).text =
                getString(R.string.announcemetn_content_3)
        }

    }

    private fun titleColor() {
        titleState.forEachIndexed { index, b ->
            if (b) {
                parentFragment?.context?.let { context ->
                    titleTextView[index].run {
                        background = ContextCompat.getDrawable(context, R.drawable.announcement_title_press)
                        setTextColor(Color.BLACK)
                    }
                }
            } else {
                parentFragment?.context?.let { context ->
                    titleTextView[index].run {
                        background = ContextCompat.getDrawable(context, R.drawable.announcement_title_unpress)
                        setTextColor(Color.WHITE)
                    }
                }
            }
        }
    }
}