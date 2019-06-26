package com.yf.viewpractice.nestedscroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yf.viewpractice.R
import kotlinx.android.synthetic.main.fragment_userinfo.*


class VideoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun create(): VideoFragment {
            val fragment = VideoFragment()
            return fragment
        }
    }
}

class UserInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_userinfo, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewUserInfo.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewUserInfo.adapter = MyAdapter(ItemBeanHolder.dataList)
    }

    companion object {

        fun create(): UserInfoFragment {
            val fragment = UserInfoFragment()
            return fragment
        }
    }
}