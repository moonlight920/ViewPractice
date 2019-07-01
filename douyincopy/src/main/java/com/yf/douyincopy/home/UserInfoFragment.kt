package com.yf.douyincopy.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.yf.douyincopy.*
import com.yf.douyincopy.bean.Card
import com.yf.douyincopy.bean.Cards
import com.yf.douyincopy.bean.ItemBeanHolder
import com.yf.douyincopy.bean.OnCardChangeListener
import com.yf.douyincopy.home.userinfo.MyAdapter
import kotlinx.android.synthetic.main.fragment_userinfo.*
import kotlinx.android.synthetic.main.layout_header_userinfo.*
import kotlinx.android.synthetic.main.layout_title_userinfo.*

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

        onTitleAlphaChange(0f)
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var tempAlpha = 0f
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                // 方便计算，偏移量改成正值
                val offset = -verticalOffset.toFloat()
                val changeStart = headerLayout.height.toFloat() * 3 / 5
                val changeEnd = headerLayout.height.toFloat() * 4 / 5
                if (offset > 0) {
                    when {
                        offset < changeStart -> {
                            if (tempAlpha != 0f) {
                                tempAlpha = 0f
                                onTitleAlphaChange(tempAlpha)
                            }
                        }
                        offset in changeStart..changeEnd -> {
                            val scale = (offset - changeStart) / (changeEnd - changeStart)
                            onTitleAlphaChange(scale)
                            bgView.alpha = scale
                        }
                        offset > changeEnd -> {
                            if (tempAlpha != 1f) {
                                tempAlpha = 1f
                                onTitleAlphaChange(tempAlpha)
                            }
                        }
                    }
                }
            }

        })
    }

    fun onTitleAlphaChange(alpha: Float) {
        bgView.alpha = alpha
        btnFollow.alpha = alpha
        tvUserName.alpha = alpha
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