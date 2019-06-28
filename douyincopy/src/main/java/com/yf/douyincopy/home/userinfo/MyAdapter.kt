package com.yf.douyincopy.home.userinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.yf.douyincopy.R
import com.yf.douyincopy.bean.ItemBean

class MyAdapter(var list: List<ItemBean>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nested_scroll_1, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvSummary.text = list[position].title
        holder.iv.setImageResource(list[position].imageId)
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvSummary = itemView.findViewById<AppCompatTextView>(R.id.tvSummary)
    val iv = itemView.findViewById<AppCompatImageView>(R.id.iv)
}
