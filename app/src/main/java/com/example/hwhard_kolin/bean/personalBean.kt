package com.example.hwhard_kolin.bean

data class personalBean(
    var account: String = "",
    var password: String = "",
    var name: String = "",
    var school: String = "",
    var id: String = "",
    var rank: String = "新生",
    var score: Int = 0,
    var win: Int = 0,
    var lose: Int = 0
)