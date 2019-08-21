package com.yf.viewpractice.navigation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

import com.yf.viewpractice.R
import kotlinx.android.synthetic.main.fragment_navi_level1_num1.*


class NaviLevel1Num1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navi_level1_num1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnToLevel2.setOnClickListener {
            val action =
                NaviLevel1Num1FragmentDirections.actionNaviLevel1Num1FragmentToNaviLevel2Num1Fragment("yfengtech")
            findNavController().navigate(action)

        }
    }


}
