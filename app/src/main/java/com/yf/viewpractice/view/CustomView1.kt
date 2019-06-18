package com.yf.viewpractice.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.yf.viewpractice.debugLog

class CustomView1 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val paint = Paint()
    val path = Path()
    var pathMeasure: PathMeasure? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        path.reset()
        path.addRect(
            width / 2 - 150f,
            height / 2 - 200f,
            width / 2 + 150f,
            height.toFloat() / 2, Path.Direction.CW
        )

        path.addCircle(width.toFloat() / 2, height.toFloat() / 2, 150f, Path.Direction.CW)
        path.addCircle(width.toFloat() / 2, height.toFloat() / 2, 300f, Path.Direction.CCW)

        pathMeasure = PathMeasure(path, true)
        debugLog(pathMeasure?.length.toString())
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        path.fillType = Path.FillType.INVERSE_EVEN_ODD
        canvas?.drawPath(path, paint)
    }
}