package com.yf.viewpractice.measure

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.ImageViewCompat
import com.yf.viewpractice.debugLog
import java.util.jar.Attributes

class MyImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    AppCompatImageView(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = resolveSize(measuredWidth,widthMeasureSpec)
        val height = resolveSize(measuredHeight,heightMeasureSpec)

        setMeasuredDimension(width, height)
        debugLog("MyImageView onMeasure")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        debugLog("MyImageView onLayout")
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
        debugLog("layout : $l  $t  $r  $b")
        debugLog("MyImageView layout")
    }

}