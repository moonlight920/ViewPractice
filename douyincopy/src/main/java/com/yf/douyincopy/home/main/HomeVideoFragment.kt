package com.yf.douyincopy.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yf.douyincopy.bean.Card
import com.yf.douyincopy.R
import kotlinx.android.synthetic.main.fragment_nested_card.*

class HomeVideoFragment : Fragment() {

    var cardID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardID = arguments?.getInt("id", 0)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nested_card, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvId.text = cardID.toString()

    }

    companion object {

        fun create(card: Card): HomeVideoFragment {
            val fragment = HomeVideoFragment()
            val bundle = Bundle().apply {
                putInt("id", card.id)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}