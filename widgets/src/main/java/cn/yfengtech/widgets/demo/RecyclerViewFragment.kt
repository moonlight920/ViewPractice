package cn.yfengtech.widgets.demo


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.practice.recycler.SwipeDragAdapter

import kotlinx.android.synthetic.main.fragment_recycler_view.*

class RecyclerViewFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
        recyclerView.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                context,
                androidx.recyclerview.widget.RecyclerView.VERTICAL
            )
        )
        recyclerView.adapter = SwipeDragAdapter(context!!)

        val itemTouchHelper = ItemTouchHelper(SwipeDragAdapter.SwipeController())
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}
