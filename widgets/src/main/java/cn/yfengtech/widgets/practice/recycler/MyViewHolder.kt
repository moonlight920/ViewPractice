package cn.yfengtech.widgets.practice.recycler

import androidx.appcompat.widget.AppCompatTextView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cn.yfengtech.widgets.R

/**
 * Created by yf.
 * @date 2019-08-18
 */
class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val tvSummary = itemView.findViewById<AppCompatTextView>(R.id.tvSummary)
}