package com.example.hwhard_kolin.util

import android.util.Log
import com.example.hwhard_kolin.bean.QuestionBean
import com.example.hwhard_kolin.bean.QuestionList
import com.example.hwhard_kolin.bean.RankBean
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson

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
                fail("讀取伺服器狀態失敗")
            }
    }

    fun loginCheck(
        mAccount: String,
        mPassword: String,
        success: (personalData: String) -> Unit,
        fail: (msg: String) -> Unit
    ) {
        db.collection("personalData")
            .whereEqualTo("account", mAccount)
            .whereEqualTo("password", mPassword)
            .get()
            .addOnSuccessListener {
                if (it.documents.isEmpty()) {
                    fail("帳號或密碼錯誤")
                    return@addOnSuccessListener
                }
                Log.v("personalData", it.documents.first().data.toString())
                success(it.documents.first().data.toString())
            }
            .addOnFailureListener {
                fail("無法連到資料庫")
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
     *  隨機取得五題題目資料
     *  【章節】【類型】【答案】
     */
    // Todo 隨機機制
    fun getRandomQuestionToSP(callBack: (questionList: QuestionList) -> Unit) {
        val questionList = mutableListOf<QuestionBean>()
        db.collection("chapter1").limit(5).get()
            .addOnSuccessListener {
                it.documents.forEach { document ->
                    val questionBean = QuestionBean(
                        chapter = "chapter1",
                        num = document.id,
                        type = document.data?.get("type").toString(),
                        answerTop = document.data?.get("answerTop").toString(),
                        answerBottom = document.data?.get("answerBottom").toString()
                    )
                    questionList.add(questionBean)
                }
                callBack(QuestionList(questionList))
            }
            .addOnFailureListener {
                Log.v("Question", "fail")
            }
    }
}