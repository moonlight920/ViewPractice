package com.yf.viewpractice.customview.cup

import kotlin.random.Random

data class Bubble(var radius: Int, var speedX: Float, var speedY: Float, var x: Int, var y: Int)

object BubbleHolder {
    val dataList = arrayListOf<Bubble>()

    fun addBubbleList(x: IntRange, y: IntRange, count: Int) {
        for (i in 0..count) {
            val radius = Random.nextInt(10, 36)
            val speedX = Random.nextInt(-2, 2).toFloat()
            val speedY = Random.nextInt(5, 10).toFloat()
            dataList.add(Bubble(radius, speedX, speedY, x.random(), y.random()))
        }
    }
}