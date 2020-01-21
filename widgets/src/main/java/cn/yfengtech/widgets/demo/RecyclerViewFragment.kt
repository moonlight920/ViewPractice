package cn.yfengtech.widgets.demo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.practice.recycler.SwipeDragAdapter

import kotlinx.android.synthetic.main.fragment_recycler_view.*

internal class RecyclerViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        recyclerView.adapter = SwipeDragAdapter(context!!)

        val itemTouchHelper = ItemTouchHelper(SwipeDragAdapter.SwipeController())
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}
