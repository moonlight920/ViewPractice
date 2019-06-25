package com.yf.viewpractice.utils

import android.content.res.Resources

/**
 * Created by yf.
 * @date 2019-06-25
 */
object Utils {
    fun dp2px(dp: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()

    }

    fun sp2px(spValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}