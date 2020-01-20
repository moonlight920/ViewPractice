package cn.yfengtech.widgets.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.practice.TaiJiView

class TaiJiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tai_ji, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taijiView = view.findViewById<TaiJiView>(R.id.taijiView)
        taijiView.animate()
            .rotation(35000f)
            .setDuration(14000)
//            .setInterpolator(AccelerateInterpolator())
            .setStartDelay(1000)
            .start()
    }

}
