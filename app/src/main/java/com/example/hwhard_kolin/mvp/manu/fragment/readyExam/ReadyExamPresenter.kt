package com.example.hwhard_kolin.mvp.manu.fragment.readyExam

import android.os.Bundle
import android.util.Log
import com.example.hwhard_kolin.PRACTICE_MODE
import com.example.hwhard_kolin.RANK_MODE
import com.example.hwhard_kolin.bean.AnswerBean
import com.example.hwhard_kolin.bean.QuestionBean
import com.example.hwhard_kolin.bean.QuestionList
import com.example.hwhard_kolin.util.CloudFireStore
import com.example.hwhard_kolin.util.getDegreeAndNum
import com.example.hwhard_kolin.util.getRandomQuestionNum
import com.example.hwhard_kolin.util.toChapter
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson

class ReadyExamPresenter(val view: ReadyExamContract.View?) :
    ReadyExamContract.Presenter {

    init {
        view?.setPresenter(this)
    }

    override fun goChapter(mExamModeList: List<Int>, rank: Int) {
        CloudFireStore.getChapterQuestion(
            chapter = mExamModeList.toChapter(),
            rank = rank,
            success = {

                val bundle = Bundle()
                bundle.putSerializable("question", it)
                bundle.putString("type", PRACTICE_MODE)
                view?.goExam(bundle)

                examLog(it.questionList)
            },
            fail = {
                view?.showErrorMessage(it)
            }
        )
    }

    override fun goVolume(volume: Int, rank: Int) {
        CloudFireStore.getVolumerQuestion(
            volume = volume.toString(),
            success = {
                logic(it, rank)
            },
            fail = {
                view?.showErrorMessage(it)
            }
        )
    }

    /**
     *  versionNum :  0 -> A 版 , 1 -> B 版
     */
    override fun goGSAT(versionNum: Int, rank: Int) {
        val version = if (versionNum == 0) {
            "A"
        } else {
            "B"
        }
        CloudFireStore.getAll(
            version = version,
            success = {
                logic(it, rank)
            },
            fail = {
                view?.showErrorMessage(it)
            }
        )
    }

    override fun goRank(versionNum: Int, rank: Int) {
        val version = if (versionNum == 0) {
            "A"
        } else {
            "B"
        }
        CloudFireStore.getAll(
            version = version,
            success = {
                logic(it, rank, isRank = true)
            },
            fail = {
                view?.showErrorMessage(it)
            }
        )
    }


    /**
     *  將取得的 QuerySnapshot 丟進，經過邏輯判斷，開始考試
     */
    private fun logic(data: QuerySnapshot, rank: Int, isRank: Boolean = false) {
        val resultList = mutableListOf<QuestionBean>()

        // 難度和所需題數
        val questionInfo = rank.getDegreeAndNum()

        if (questionInfo.degree1.isEmpty()) {
            view?.showErrorMessage("牌位錯誤，請聯繫客服")
            return
        }

        if (data.documents.size == 0) {
            view?.showErrorMessage("該模式尚未有對應資料，請聯繫客服")
            return
        }

        // 五題隨機對應章節
        val chapterList = data.documents.size.getRandomQuestionNum(isRepeat = true, count = 5)

        // 第一難度
        // Todo 目前題目會重複，需修正
        for (i in 0 until questionInfo.num1) {

            val answer1 =
                data.documents[chapterList[i] - 1].data?.get(questionInfo.degree1) as Map<*, *>

            if (answer1.isEmpty() || answer1.size < questionInfo.num1) {
                view?.showErrorMessage("${data.documents[chapterList[i] - 1].id}_${chapterList[i]}_${questionInfo.degree1}：題庫數量不足，請聯繫客服")
                return
            }
            // 藉由題庫數量取得 random 題號
            val randon =
                answer1.size.getRandomQuestionNum(isRepeat = true, count = 1)[0].toString()

            val answerBean =
                Gson().fromJson(answer1[randon].toString(), AnswerBean::class.java)

            resultList.add(
                QuestionBean(
                    chapter = data.documents[chapterList[i] - 1].id,
                    degree = questionInfo.degree1,
                    num = randon,
                    type = answerBean.type,
                    answerTop = answerBean.answerTop,
                    answerBottom = answerBean.answerBottom
                )
            )

        }

        // 第二難度
        // Todo 目前題目會重複，需修正
        if (questionInfo.degree2.isNotEmpty()) {
            for (i in questionInfo.num1 until 5) {

                val answer2 =
                    data.documents[chapterList[i]].data?.get(questionInfo.degree2) as Map<*, *>

                if (answer2.isEmpty() || answer2.size < questionInfo.num2) {
                    view?.showErrorMessage("${chapterList[i]}_${questionInfo.degree2}：題庫數量不足，請聯繫客服")
                    return
                }
                // 藉由題庫數量取得 random 題號
                val randon =
                    answer2.size.getRandomQuestionNum(
                        isRepeat = true,
                        count = 1
                    )[0].toString()

                val answerBean =
                    Gson().fromJson(answer2[randon].toString(), AnswerBean::class.java)

                resultList.add(
                    QuestionBean(
                        chapter = data.documents[chapterList[i]].id,
                        degree = questionInfo.degree1,
                        num = randon,
                        type = answerBean.type,
                        answerTop = answerBean.answerTop,
                        answerBottom = answerBean.answerBottom
                    )
                )
            }
        }

        val bundle = Bundle()
        bundle.putSerializable("question", QuestionList(resultList))
        if (isRank) {
            bundle.putString("type", RANK_MODE)
        } else {
            bundle.putString("type", PRACTICE_MODE)
        }
        view?.goExam(bundle)


        examLog(resultList)
    }


    private fun examLog(resultList: MutableList<QuestionBean>) {
        Log.v("Question", "---------------------")
        resultList.forEach { bean ->
            Log.v(
                "Question",
                "${bean.chapter}/${bean.degree}/${bean.num}/${bean.type}/${bean.answerTop}/${bean.answerBottom}"
            )
        }
        Log.v("Question", "---------------------")
    }


    override fun onDestory() {}

    override fun start() {}

}