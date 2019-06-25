package com.yf.viewpractice.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.yf.viewpractice.R

object BitmapUtil {
    fun getBitmap(resources:Resources,resId:Int,width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, resId, options)

    }
}