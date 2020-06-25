package com.example.hwhard_kolin.mvp.model.jdbc.thread

import java.sql.DriverManager

class Rank : Thread() {

    private val mysql_ip = "219.70.21.198"
    private val mysql_port = 3306
    private val db_name = "math"
    private val url = "jdbc:mysql://$mysql_ip:$mysql_port/$db_name"
    private var db_user = "pb"
    private var db_password = "MrFlLLO88JQgZ5kT"
    private var personal_data = ""

    override fun run() {

        Class.forName("com.mysql.jdbc.Driver")
        val con = DriverManager.getConnection(url, db_user, db_password)
        val st = con.createStatement()

        var num = 0
        val sb = StringBuilder()
        val rank = arrayOf(
            "優級", "甲級", "乙級", "丙級", "丁級", "戊級",
            "己級", "庚級", "辛級", "壬級", "癸級", "新生"
        )


        for (j in 0..11) {
            val sql = "SELECT * FROM personal_data WHERE rank = '" + rank[j] + "' order by score desc"
            val rs = st.executeQuery(sql)
            while (rs.next() && num < 100) {
                sb.append(rs.getString("name")).append(",").append(rs.getString("school"))
                    .append(",").append(rs.getString("rank")).append(",")
                num++
            }
        }


        sb.delete(sb.length - 1, sb.length)
        personal_data = sb.toString()



    }

}