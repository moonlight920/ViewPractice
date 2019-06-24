package com.yf.viewpractice.taglayout


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yf.viewpractice.R

class TagLayoutFragment : Fragment() {

    val list = arrayListOf(
        "福州市", "厦门市", "泉州市", "漳州市", "南平市", "三明市", "龙岩市", "莆田市",
        "宁德市", "建瓯市", "武夷山市", "长乐市", "福清市", "晋江市", "南安市", "福安市",
        "龙海市", "邵武市", "石狮市", "福鼎市", "建阳市", "漳平市", "永安市",
        "兰州市", "白银市", "武威市", "金昌市", "平凉市", "张掖市", "嘉峪关市", "酒泉市",
        "庆阳市", "定西市", "陇南市", "天水市", "玉门市", "临夏市", "合作市", "敦煌市", "甘南州",
        "贵阳市", "安顺市", "遵义市", "六盘水市", "兴义市", "都匀市", "凯里市", "毕节市", "清镇市",
        "铜仁市", "赤水市", "仁怀市", "福泉市",
        "海口市", "三亚市", "万宁市", "文昌市", "儋州市", "琼海市", "东方市", "五指山市",
        "石家庄市", "保定市", "唐山市", "邯郸市邢台市", "沧州市", "衡水市", "廊坊市", "承德市", "迁安市",
        "鹿泉市", "秦皇岛市", "南宫市", "任丘市", "叶城市", "辛集市", "涿州市", "定州市", "晋州市", "霸州市",
        "黄骅市", "遵化市", "张家口市", "沙河市", "三河市", "冀州市", "武安市", "河间市深州市", "新乐市",
        "泊头市", "安国市", "双滦区", "高碑店市"
    )

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
