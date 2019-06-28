package com.yf.douyincopy.bean

import com.yf.douyincopy.R

object ItemBeanHolder {

    val dataList = getData()

    private fun getData(): ArrayList<ItemBean> {
        val list = arrayListOf<ItemBean>()
        for (i in 0..100) {
            val imageId = when {
                i % 4 == 0 -> {
                    R.drawable.guava
                }
                i % 3 == 0 -> {
                    R.drawable.jackfruit
                }
                i % 2 == 0 -> {
                    R.drawable.pomegranate
                }
                else -> {
                    R.drawable.mix_fruit
                }
            }
            val title = "title index $i"
            list.add(ItemBean(imageId, title))
        }
        return list
    }
}