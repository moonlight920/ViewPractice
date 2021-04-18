package com.yf.viewpractice

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val xRange = -10..10
        for (x in xRange) {
            println("x=$x")
            println("y=${function(x)}")
        }
        assertEquals(4, 2 + 2)
    }

    private fun function(x: Number): Float {
        val realX = x.toFloat()
//        val y = x.toFloat() * 2 + 1
//        val y = realX + 1 / realX
//        val y = 1 / realX
        val y = realX * realX + 2 * realX
        return y
    }
}
