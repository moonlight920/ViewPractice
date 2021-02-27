package com.yf.viewpractice.server

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import cn.yfengtech.server.SimpleServer

class WebServerFragment : Fragment() {

    private lateinit var server: SimpleServer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        server = SimpleServer(requireContext().applicationContext, DemoApiHttpHandler(), 9000)
        server.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = FrameLayout(requireContext())

        val tv = TextView(requireContext())

        contentView.addView(
            tv, FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER }
        )



        tv.text = server.getHostAddress() + ":9000"

        return contentView
    }

}