package com.example.hwhard_kolin.mvp.model.jdbc

class Jdbc : JdbcContract{

    private val mysql_ip = "219.70.21.198"
    private val mysql_port = 3306
    private val db_name = "math"
    private val url = "jdbc:mysql://$mysql_ip:$mysql_port/$db_name"
    private val sql: String? = null
    private val str: String? = null
    private val db_user: String? = null
    private val db_password: String? = null


    override fun getRankData() {

    }

    override fun getPersonalData() {
        //TODO("Not yet implemented")
    }


}