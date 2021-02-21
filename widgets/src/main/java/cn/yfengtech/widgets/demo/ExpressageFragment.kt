package cn.yfengtech.widgets.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.practice.expressage.DataHolder
import cn.yfengtech.widgets.practice.expressage.ExpressInfo
import cn.yfengtech.widgets.practice.expressage.ExpressageItemDecoration
import kotlinx.android.synthetic.main.fragment_expressage.*


class ExpressageFragment : androidx.fragment.app.Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expressage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
        recyclerView.addItemDecoration(ExpressageItemDecoration())
        recyclerView.adapter =
            MyAdapter(DataHolder.dataList)
    }

    class MyAdapter(var list: List<ExpressInfo>) : androidx.recyclerview.widget.RecyclerView.Adapter<MyViewHolder>() {
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

    class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val tvSummary = itemView.findViewById<TextView>(R.id.tvSummary)
        val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
    }

}
