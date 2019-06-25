package com.yf.viewpractice.expressage

object DataHolder {
    val dataList = getData()


    private fun getData(): List<ExpressInfo> {
        val list = arrayListOf<ExpressInfo>()
        for (data in 15 until 25) {
            for (time in 42 until 52) {
                list.add(ExpressInfo("06-$data", "14:$time", "正在派送"))
            }
        }
        return list
    }
}