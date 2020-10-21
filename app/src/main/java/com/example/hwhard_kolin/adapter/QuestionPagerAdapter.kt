package com.example.hwhard_kolin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hwhard_kolin.mvp.exam.fragment.QuestionFragment

class QuestionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return QuestionFragment(position)
    }

    override fun getCount(): Int {
        return 5
    }

}