package com.yf.viewpractice.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yf.smarttemplate.SmartTemplate
import com.yf.viewpractice.R
import kotlinx.android.synthetic.main.fragment_nested_scroll.*


/**
 * 嵌套滑动
 */
class NestedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nested_scroll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SmartTemplate.generateSampleData(context!!, recyclerView)
    }
}
