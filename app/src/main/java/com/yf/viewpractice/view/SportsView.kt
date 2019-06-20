package com.yf.viewpractice.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yf.viewpractice.R
import com.yf.viewpractice.debugLog

class SportsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val RADIUS = 300f

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.textSize = 100f
        paint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        paint.color = Color.RED
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        paint.color = Color.GREEN
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2 + RADIUS,
            -90f,
            220f,
            false,
            paint
        )

        paint.style = Paint.Style.FILL
        // 垂直居中1
        val rect = Rect()
        paint.getTextBounds("Hello", 0, "Hello".length, rect)
        val offset = (rect.top + rect.bottom) / 2
        canvas.drawText("Hello", width / 2f, height / 2f - offset, paint)

        // 垂直居中2
        val fontMetrics = Paint.FontMetrics()
        paint.getFontMetrics(fontMetrics)
        val offset1 = (fontMetrics.top + fontMetrics.bottom) / 2
        canvas.drawText("Hello", width / 2f, height / 2f - offset1, paint)

        // 居左，不留缝隙
        val rect1 = Rect()
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds("Hello", 0, "Hello".length, rect1)
        val offset2 = (rect.top + rect.bottom) / 2
        canvas.drawText("Hello", -rect1.left.toFloat(), height / 2f - offset2, paint)

    }
}