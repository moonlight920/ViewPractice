package cn.yfengtech.server

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class WebServerFragment : Fragment() {

    private lateinit var server: SimpleServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        server = SimpleServer(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FrameLayout(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop()
    }
}