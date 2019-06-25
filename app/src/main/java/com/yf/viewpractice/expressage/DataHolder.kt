package com.yf.viewpractice.expressage

object DataHolder {
    val dataList = getData()


    private fun getData(): List<ExpressInfo> {
        val list = arrayListOf<ExpressInfo>()

        list.add(ExpressInfo("06-01", "14:00", DeliverStatus.COLLECTING, "已揽件","xxx正在装车..."))
        for (data in 10 until 15) {
            list.add(
                ExpressInfo(
                    "06-$data",
                    "14:30",
                    DeliverStatus.DEFAULT,
                    "运输中...",
                    "正在发往xxx \n正在发往xxx \n正在发往xxx \n正在发往xxx "
                )
            )
        }
        list.add(ExpressInfo("06-16", "14:00", DeliverStatus.TRANSPORTING, "运输中"))
        list.add(ExpressInfo("06-17", "14:00", DeliverStatus.DELIVERING, "派件中", "xxx正在派件......"))
        for (time in 16 until 18) {
            list.add(
                ExpressInfo(
                    "06-18",
                    "$time:40",
                    DeliverStatus.DEFAULT,
                    "派送中",
                    "派送中...派送中...派送中...派送中...派送中...派送中...派送中...派送中...派送中..."
                )
            )
        }
        list.add(
            ExpressInfo(
                "06-18",
                "20:00",
                DeliverStatus.RECEIVED,
                "已签收",
                "[北京市]快件已被xxx签收，快件已被xxx签收，快件已被xxx签收，快件已被xxx签收，快件已被xxx签收，快件已被xxx签收，快件已被xxx签收"
            )
        )
        list.add(ExpressInfo("06-18", "20:00", DeliverStatus.HEADER, "[收货地址] 北京北京市 前门大街 1-1-1"))
        return list.reversed()
    }
}