package com.example.hwhard_kolin.bean

import java.net.URI
import java.net.URL

data class PersonalBean(

    var name: String = "",
    var school: String = "",
    var id: String = "",
    var loginType: String = "",
    var rank: Int = 1,
    var score: Int = 0,
    var win: Int = 0,
    var lose: Int = 0,
    var url: String = ""
)

data class RankBean(
    var name: String = "",
    var school: String = "",
    var rank: Int = 1
)