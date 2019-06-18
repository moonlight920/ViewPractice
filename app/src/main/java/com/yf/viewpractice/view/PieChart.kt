package com.yf.viewpractice.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PieChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val RADIUS = 150f
    val MOVE_LENGTH = 20f

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val bounds = RectF()

    val angles = intArrayOf(60, 80, 120, 100)
    val colors = intArrayOf(Color.BLUE, Color.BLACK, Color.RED, Color.YELLOW)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bounds.set(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        var currentAngle = 0f
        for (i in 0..3) {
            paint.color = colors[i]

            canvas?.save()
            if (i == 2) {
                val endX =
                    Math.cos(Math.toRadians(currentAngle.toDouble() + angles[i] / 2)).toFloat() * MOVE_LENGTH
                val endY =
                    Math.sin(Math.toRadians(currentAngle.toDouble() + angles[i] / 2)).toFloat() * MOVE_LENGTH
                canvas?.translate(endX, endY)
            }
            canvas?.drawArc(bounds, currentAngle, angles[i].toFloat(), true, paint)
            canvas?.restore()

            currentAngle += angles[i].toFloat()
        }

    }
}