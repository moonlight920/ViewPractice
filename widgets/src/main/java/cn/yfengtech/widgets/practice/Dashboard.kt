package cn.yfengtech.widgets.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class Dashboard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val ANGLE = 120f
    val radius = 150f

    val LENGTH = 100f

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    val arc = Path()
    val dash = Path()

    var pathEffect: PathDashPathEffect? = null

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f

        arc.addArc(
            width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius,
            90 + ANGLE / 2,
            360 - ANGLE
        )
        val pathMeasure = PathMeasure(arc, false)

        dash.addRect(0f, 0f, 2f, 10f, Path.Direction.CW)
        pathEffect = PathDashPathEffect(dash, (pathMeasure.length - 2) / 20, 0f, PathDashPathEffect.Style.ROTATE)

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawArc(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius,
            90 + ANGLE / 2,
            360 - ANGLE, false, paint
        )
        paint.pathEffect = pathEffect
        canvas?.drawArc(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius,
            90 + ANGLE / 2,
            360 - ANGLE,
            false,
            paint
        )
        val endX = Math.cos(Math.toRadians(getAngleFromMark(5))).toFloat() * LENGTH + width / 2f
        val endY = Math.sin(Math.toRadians(getAngleFromMark(5))).toFloat() * LENGTH + height / 2f

        canvas?.drawLine(width / 2f, height / 2f, endX, endY, paint)
    }

    fun getAngleFromMark(mark: Int): Double {
        return (90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark).toDouble()
    }
}