package cn.yfengtech.widgets.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import cn.yfengtech.widgets.R

/**
 * paint 属性演示
 *
 * @created yf
 * @date 2019-08-05 10:10
 */
internal class TriangleLoadingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_souhu_loading, container, false)
    }
}
