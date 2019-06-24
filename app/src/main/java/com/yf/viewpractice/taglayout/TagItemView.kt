package com.yf.viewpractice.taglayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TagItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path()

    val BG_PADDING_TOP = 5f
    val BG_PADDING_BOTTOM = 5f


    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        textSize = 20f
        setPadding(10, 10, 10, 10)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        path.reset()
        path.addArc(
            0f,
            BG_PADDING_TOP,
            measuredHeight.toFloat(),
            measuredHeight.toFloat() - BG_PADDING_BOTTOM,
            90f,
            180f
        )
        path.lineTo(measuredWidth.toFloat() - measuredHeight / 2, BG_PADDING_TOP)
        path.addArc(
            measuredWidth.toFloat() - measuredHeight,
            BG_PADDING_TOP,
            measuredWidth.toFloat(),
            measuredHeight.toFloat() - BG_PADDING_BOTTOM,
            270f,
            180f
        )
        path.lineTo(measuredHeight.toFloat() / 2, measuredHeight.toFloat() - BG_PADDING_BOTTOM)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
        super.onDraw(canvas)
    }
}