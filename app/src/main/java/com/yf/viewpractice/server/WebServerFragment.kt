package com.yf.viewpractice.server

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.yf.viewpractice.server.plugins.OpenH5Plugin
import com.yf.viewpractice.server.plugins.PullAppDataPlugin
import com.yf.viewpractice.server.plugins.PushMockPushPlugin
import cn.yfengtech.server.SimpleServer

class WebServerFragment : Fragment() {

    private lateinit var server: SimpleServer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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


        val port = 9001

        val server = SimpleServer(requireContext(), port)
        server.setWebTitle("叫我Title")
        server.loadPlugin(PushMockPushPlugin())
        server.loadPlugin(OpenH5Plugin())
        server.loadPlugin(PullAppDataPlugin())
        server.start()


        tv.text = server.getHostAddress() + ":$port"

        return contentView
    }

}