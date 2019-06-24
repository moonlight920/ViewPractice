package com.yf.viewpractice.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yf.viewpractice.R
import com.yf.viewpractice.debugLog

class TaiJiView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val RADIUS_OUTER = 100f
    val RADIUS_MIDDLE = RADIUS_OUTER / 2
    val RADIUS_INNER = RADIUS_OUTER / 6

    val whitePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val blackPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        whitePaint.color = Color.WHITE
        whitePaint.style = Paint.Style.FILL

        blackPaint.color = Color.BLACK
        blackPaint.style = Paint.Style.FILL

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.translate(width / 2f, height / 2f)
        canvas.drawCircle(0f, 0f, RADIUS_OUTER, blackPaint)
//        canvas.translate(-width / 2f, -height / 2f)

    }
}