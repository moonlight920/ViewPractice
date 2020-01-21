package cn.yfengtech.widgets.practice.recycler

import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.support.v7.widget.RecyclerView
import cn.yfengtech.widgets.R

/**
 * Created by yf.
 * @date 2019-08-18
 */
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvSummary = itemView.findViewById<AppCompatTextView>(R.id.tvSummary)
}