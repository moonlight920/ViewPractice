package com.yf.douyincopy.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yf.douyincopy.*
import com.yf.douyincopy.bean.Card
import com.yf.douyincopy.bean.Cards
import com.yf.douyincopy.bean.ItemBeanHolder
import com.yf.douyincopy.bean.OnCardChangeListener
import com.yf.douyincopy.home.userinfo.MyAdapter
import kotlinx.android.synthetic.main.fragment_userinfo.*

class UserInfoFragment : Fragment(), OnCardChangeListener {

    override fun onChange(card: Card) {
//        tvId.text = "card ${card.id}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_userinfo, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Cards.registerCardChangeListener(this)
        recyclerViewUserInfo.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewUserInfo.adapter = MyAdapter(ItemBeanHolder.dataList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Cards.unregisterCardChangeListener(this)
    }

    companion object {

        fun create(): UserInfoFragment {
            val fragment = UserInfoFragment()
            return fragment
        }
    }
}