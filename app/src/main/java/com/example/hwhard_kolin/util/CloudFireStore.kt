package com.example.hwhard_kolin.util

import android.accounts.Account
import android.util.Log
import com.example.hwhard_kolin.bean.personalBean
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object CloudFireStore {

    val db = FirebaseFirestore.getInstance()

    /**
     *  註冊寫入DB
     */
    fun regist(bean: personalBean) {
        db.collection("personalData").add(bean)
            .addOnSuccessListener {
                Log.v("db_log", "---------------")
                Log.v("db_log", "Regist_Success")
                Log.v("db_log", bean.toString())
                Log.v("db_log", "---------------")
            }
    }


    /**
     *  取得單一文件，或單一文件裡的 (key,value) 值
     */
    fun readSingleData(key: String) {
        db.collection("personalData").document("vaxjAKlZbcRgi2tohsRn").get()

            .addOnSuccessListener { result ->
            }

            .addOnFailureListener {
            }
    }

    /**
     *  取得整個集合
     */
    fun readAllData() {

        db.collection("personalData").get()

            .addOnSuccessListener {
            }

            .addOnFailureListener {

            }

    }

    /**
     * 取得排行榜資料
     */
    fun getRankData() {

        // Query.Direction.DESCENDING 由高至低排序
        db.collection("personalData").orderBy("score", Query.Direction.DESCENDING).limit(100).get()

            .addOnSuccessListener {
                var info = ""
                for (eachData in it) {
                    info += "${eachData.data}"
                }
                Log.v("db_log", "---------------")
                Log.v("db_log", "getRankData_Success")
                Log.v("db_log", info)
                Log.v("db_log", "---------------")
            }

            .addOnFailureListener {
                Log.v("db_log", "---------------")
                Log.v("db_log", "getRankData_Failure")
                Log.v("db_log", "---------------")
            }

    }


}