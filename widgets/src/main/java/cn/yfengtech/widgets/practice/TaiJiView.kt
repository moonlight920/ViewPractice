package cn.yfengtech.widgets.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TaiJiView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val RADIUS_OUTER = 300f
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

        canvas.save()
        canvas.translate(width / 2f, height / 2f)
        canvas.rotate(30f)
        canvas.drawCircle(0f, 0f, RADIUS_OUTER, blackPaint)
        canvas.drawArc(-RADIUS_OUTER, -RADIUS_OUTER, RADIUS_OUTER, RADIUS_OUTER, 90f, 180f, true, whitePaint)
        canvas.drawArc(-RADIUS_OUTER, -RADIUS_OUTER, RADIUS_OUTER, RADIUS_OUTER, 270f, 180f, true, blackPaint)

        canvas.drawCircle(0f, -RADIUS_OUTER / 2, RADIUS_OUTER / 2, whitePaint)
        canvas.drawCircle(0f, RADIUS_OUTER / 2, RADIUS_OUTER / 2, blackPaint)

        canvas.drawCircle(0f, -RADIUS_OUTER / 2, RADIUS_OUTER / 8, blackPaint)
        canvas.drawCircle(0f, RADIUS_OUTER / 2, RADIUS_OUTER / 8, whitePaint)
//        canvas.translate(-width / 2f, -height / 2f)
        canvas.restore()
    }
}