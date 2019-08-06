package com.yf.viewpractice.customview.expressage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yf.viewpractice.R
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
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_express, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.tvSummary.text = list[position].summary
            list[position].detail.let {
                if (it.isNotBlank()) {
                    holder.tvDesc.visibility = View.VISIBLE
                    holder.tvDesc.text = it
                } else {
                    holder.tvDesc.visibility = View.GONE
                }
            }


        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSummary = itemView.findViewById<TextView>(R.id.tvSummary)
        val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
    }

}
