package com.yf.viewpractice.customview.clock

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class AwesomeClockView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private var hourCircleRadius = 80f
    private var hourCirclePath = Path()
    private var hourCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = width / 2f
        centerY = height / 2f

        hourCirclePath.reset()
        hourCirclePath.addCircle(centerX, centerY, hourCircleRadius, Path.Direction.CW)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(width / 2f, height / 2f)

        canvas.drawText("aaa",0f,0f,hourCirclePaint)

        canvas.restore()
    }
}
