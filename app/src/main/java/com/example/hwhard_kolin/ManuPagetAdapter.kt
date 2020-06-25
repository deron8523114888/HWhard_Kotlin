package com.example.hwhard_kolin

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hwhard_kolin.mvp.manu.fragment.exam.ExamFragment
import com.example.hwhard_kolin.mvp.manu.fragment.personal.PersonalFragment
import com.example.hwhard_kolin.mvp.manu.fragment.setting.SettingFragment
import com.example.hwhard_kolin.mvp.manu.fragment.study.StudyFragment

class ManuPagetAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val title = arrayListOf("個人資料","上課","考試/練習","設定")

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    override fun getItem(position: Int): Fragment {

        val pagerFragment : Fragment

        when(position){

            0 -> pagerFragment =
                PersonalFragment()
            1 -> pagerFragment =
                StudyFragment()
            2 -> pagerFragment =
                ExamFragment()
            3 -> pagerFragment =
                SettingFragment()

            else -> pagerFragment = Fragment()
        }

        return pagerFragment
    }

    override fun getCount(): Int {
        return 4
    }


}