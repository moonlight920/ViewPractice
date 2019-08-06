package com.yf.viewpractice.customview.expressage

data class ExpressInfo(
    val date: String,
    val time: String,
    val status: DeliverStatus,
    var summary: String = "",
    var detail: String = ""
)

/**
 * 派发状态
 */
enum class DeliverStatus {
    HEADER,
    COLLECTING,
    DEFAULT,
    TRANSPORTING,
    DELIVERING,
    RECEIVED
}