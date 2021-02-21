package cn.yfengtech.widgets.demo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.practice.taglayout.TagItemView
import cn.yfengtech.widgets.practice.taglayout.TagLayout
import com.yf.smarttemplate.data.DataProvider

class TagLayoutFragment : androidx.fragment.app.Fragment() {

    val list = DataProvider.getCountryEnNameList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tagLayout = view.findViewById<TagLayout>(R.id.tagLayout)
        list.forEach {
            val tagItemView = TagItemView(context!!)
            tagItemView.text = it
            val params = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )
            tagLayout.addView(tagItemView, params)

            tagItemView.setOnClickListener { chileView ->
                tagLayout.removeView(chileView)
//                tagLayout.requestLayout()
            }
        }
        tagLayout.requestLayout()
    }

}
