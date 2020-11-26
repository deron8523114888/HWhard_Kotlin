package com.example.hwhard_kolin.util

import android.util.Log
import android.widget.Toast
import com.example.hwhard_kolin.bean.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import org.spongycastle.asn1.x509.sigi.PersonalData


object CloudFireStore {

    val db = FirebaseFirestore.getInstance()


    /**
     *  檢查伺服器是否開啟
     */
    fun systemCheck(success: () -> Unit, fail: (msg: String) -> Unit) {
        db.collection("SystemInfo").document("T6XdjjK5tKIQswcrybBg").get()
            .addOnSuccessListener {
                val systemIsOpen = it.data?.get("SystemIsOpen")
                if (systemIsOpen == "1") {
                    success()
                } else {
                    fail("伺服器維修中")
                }
            }
            .addOnFailureListener {
                fail("讀取資料庫失敗，請聯繫客服")
            }
    }

    /**
     *  檢查積分系統是否開啟
     */
    fun rankCheck(success: () -> Unit, fail: (msg: String) -> Unit) {
        db.collection("SystemInfo").document("lVbZb1umHo2A0LVQcLyI").get()
            .addOnSuccessListener {
                val rankIsOpen = it.data?.get("RankIsOpen")
                if (rankIsOpen == "1") {
                    success()
                } else {
                    fail("積分模式維修中,詳細請查看公告")
                }
            }
            .addOnFailureListener {
                fail("讀取資料庫失敗，請聯繫客服")
            }
    }

    /**
     *  取得公告內容
     */
    fun getAnnouncement(response: (content: String) -> Unit) {
        db.collection("SystemInfo").document("Mljt1JpJw4T2KPAwnjOF").get()
            .addOnSuccessListener {
                val content = it.data?.get("content").toString()
                response(content)
            }
            .addOnFailureListener {
                response("讀取公告失敗，請聯繫客服")
            }
    }


    /**
     * 取得排行榜資料
     */
    fun getRankData(callBack: (ArrayList<RankBean>) -> Unit) {

        val resultList = ArrayList<RankBean>()

        db.collection("personalData")
            .orderBy("rank", Query.Direction.DESCENDING) // 取得每個牌位
            .orderBy("score", Query.Direction.DESCENDING) // 同牌位，依照分數排序
            .limit(100)
            .get()
            .addOnSuccessListener {

                it.documents.forEach { documentSnapshot ->
                    val data = documentSnapshot.data
                    val bean = RankBean(
                        name = data?.get("name").toString(),
                        school = data?.get("school").toString(),
                        rank = data?.get("rank").toString().toInt()
                    )
                    resultList.add(bean)
                }
                callBack(resultList)
            }
            .addOnFailureListener {
                Log.v("Rank", "fail")
            }
    }

    /**
     *  【章節練習】
     */
    fun getChapterQuestion(
        chapter: String, rank: Int,
        success: (questionList: QuestionList) -> Unit,
        fail: (msg: String) -> Unit
    ) {
        // 最後送出的題目 list
        val resultList = mutableListOf<QuestionBean>()

        val questionInfo = rank.getDegreeAndNum()
        if (questionInfo.degree1.isNotEmpty()) {
            db.collection("Question").document(chapter).get()
                .addOnSuccessListener { q1 ->

                    // 第一難度的答案 list
                    val answer1 = q1.data?.get(questionInfo.degree1) as Map<*, *>
                    if (answer1.size < questionInfo.num1) {
                        fail("題庫數量不足，請聯繫客服")
                        return@addOnSuccessListener
                    }
                    // 藉由題庫數量取得 random 題號
                    val randonList1 = answer1.size.getFiveRandom()

                    // 將資料庫取得的題目 對照 random 題號挑出，並加入 questionList
                    for (i in 0 until questionInfo.num1) {
                        val k = answer1["3"].toString()
                        Gson().fromJson(
                            answer1[randonList1[i].toString()].toString(),
                            AnswerBean::class.java
                        ).run {
                            val questionBean = QuestionBean(
                                chapter = chapter,
                                degree = questionInfo.degree1,
                                num = randonList1[i].toString(),
                                type = type,
                                answerTop = answerTop,
                                answerBottom = answerBottom
                            )
                            resultList.add(questionBean)
                        }
                    }

                    success(QuestionList(resultList))

                    // Todo 取得第二難度的題目
                }
                .addOnFailureListener {
                    fail("連接資料庫失敗")
                }
        }
    }


    fun getVolumerQuestion(
        chapter: String, rank: Int,
        success: (questionList: QuestionList) -> Unit,
        fail: (msg: String) -> Unit
    ) {
        db.collection("Question").whereEqualTo("a", "1").get()
            .addOnSuccessListener {
                for (i in 0 until it.documents.size) {
                    Log.v("Question", it.documents[i].id)
                }
            }
            .addOnFailureListener {
                fail("連線資料庫失敗，請聯繫客服")
            }
    }


    /**
     *  新增答案
     */
    fun newQuestion(
        chapter: String,
        degree: String,
        answer: Map<String, Map<String, String>>,
        answerBean: AnswerBean
    ) {
        db.collection("Question").document(chapter).update(degree, answer)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    /**
     *  更新個人資料
     */
    fun updatePersonalData(personalBean: PersonalBean) {
        db.collection("personalData")
            .document(personalBean.id)
            .update(
                "rank", personalBean.rank,
                "score", personalBean.score,
                "win", personalBean.win,
                "lose", personalBean.lose
            )
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }
}