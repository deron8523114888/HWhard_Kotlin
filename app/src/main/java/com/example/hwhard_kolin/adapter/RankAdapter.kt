package com.example.hwhard_kolin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.bean.PersonalBean
import com.example.hwhard_kolin.bean.RankBean
import com.example.hwhard_kolin.util.toRank

class RankAdapter(private val rankList: ArrayList<RankBean> = arrayListOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rank_each, null, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder.itemView

        // 名次
        val num = position + 1
        view.findViewById<TextView>(R.id.tv_rank_num).text = num.toString()

        if (position < rankList.size) {
            // 姓名
            view.findViewById<TextView>(R.id.tv_rank_name).text = rankList[position].name

            view.findViewById<TextView>(R.id.tv_rank_school).text = rankList[position].school

            view.findViewById<TextView>(R.id.tv_rank_status).text = rankList[position].rank.toRank()
        } else {

            view.findViewById<TextView>(R.id.tv_rank_name).text = ""

            view.findViewById<TextView>(R.id.tv_rank_school).text = ""

            view.findViewById<TextView>(R.id.tv_rank_status).text = ""
        }

    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)