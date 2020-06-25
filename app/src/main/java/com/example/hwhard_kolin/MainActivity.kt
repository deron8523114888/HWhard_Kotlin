package com.example.hwhard_kolin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hwhard_kolin.mvp.login.LoginView
import com.example.hwhard_kolin.mvp.manu.ManuView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent(this, LoginView::class.java)
        startActivity(intent)
        finish()
    }
}
