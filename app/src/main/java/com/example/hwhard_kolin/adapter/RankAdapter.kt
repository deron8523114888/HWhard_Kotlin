package com.example.hwhard_kolin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hwhard_kolin.R
import com.example.hwhard_kolin.bean.personalBean

class RankAdapter(val rankList: MutableList<personalBean> = mutableListOf()) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rank_each, null, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return 100
//        return rankList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder.itemView
        view.findViewById<TextView>(R.id.tv_rank_num).text = position.toString()
    }

}

class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)