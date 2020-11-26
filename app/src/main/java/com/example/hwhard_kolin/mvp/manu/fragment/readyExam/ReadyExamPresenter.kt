package com.example.hwhard_kolin.mvp.manu.fragment.readyExam

import android.os.Bundle
import com.example.hwhard_kolin.PRACTICE_MODE
import com.example.hwhard_kolin.util.CloudFireStore
import com.example.hwhard_kolin.util.SP
import com.example.hwhard_kolin.util.toChapter

class ReadyExamPresenter(val view: ReadyExamContract.View?) :
    ReadyExamContract.Presenter {

    init {
        view?.setPresenter(this)
    }

    override fun goChapter(mExamModeList: List<Int>) {
        CloudFireStore.getChapterQuestion(
            chapter = mExamModeList.toChapter(),
            rank = view?.getPersonData()?.rank ?: 1,
            success = {

                val bundle = Bundle()
                bundle.putSerializable("question", it)
                bundle.putString("type", PRACTICE_MODE)
                view?.goExam(bundle)

            },
            fail = {
                view?.showErrorMessage(it)
            }
        )
    }

    override fun goVolume(mExamModeList: List<Int>) {
        CloudFireStore.getVolumerQuestion("",1,success = {},fail = {})
    }

    override fun goGSAT(mExamModeList: List<Int>) {
    }

    override fun goRank(mExamModeList: List<Int>) {
    }


    override fun onDestory() {
    }

    override fun start() {
    }

}