package com.yf.viewpractice.expressage


import android.content.res.Resources
import android.graphics.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yf.viewpractice.R
import com.yf.viewpractice.debugLog
import com.yf.viewpractice.utils.BitmapUtil
import kotlinx.android.synthetic.main.fragment_expressage.*


class ExpressageFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expressage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(ExpressageItemDecoration())
        recyclerView.adapter = MyAdapter(DataHolder.dataList)
    }

    class MyAdapter(var list: List<ExpressInfo>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = list[position].detail
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(android.R.id.text1)
    }

}
