package cn.yfengtech.widgets.math

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.roundToInt

class CoordinateView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    /**
     * 每厘米 多少像素
     */
    private val pxPerCM = 50f

    private val mPaint = Paint().apply {
        strokeWidth = 3f
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
    }
    private val mThickPaint = Paint().apply {
        strokeWidth = 10f
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
    }

    private val mLinePath = Path()

    // X的值域
    private val xRange = -10..10

    // x轴计算精细度
    private val xStep = 0.1f

    private var mTempPoint: Pair<Float, Float>? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        val cooWidth = measuredWidth.toFloat()
        val cooHeight = measuredHeight.toFloat()

        val centerX = cooWidth / 2
        val centerY = cooHeight / 2

        canvas.save()
        canvas.translate(centerX, centerY)

        // 绘制根 X，Y坐标轴
        canvas.drawLine(0f, 0f, centerX, 0f, mPaint)
        canvas.drawLine(0f, 0f, -centerX, 0f, mPaint)
        canvas.drawLine(0f, 0f, 0f, centerY, mPaint)
        canvas.drawLine(0f, 0f, 0f, -centerY, mPaint)

        canvas.drawPoint(0f, 0f, mThickPaint)

        // 横坐标左半部分
        val tempXcmCount = (centerX / pxPerCM).roundToInt()
        for (i in 1..tempXcmCount) {
            val startX = -i * pxPerCM
            canvas.drawLine(startX, 0f, startX, -10f, mPaint)
        }
        for (i in 1..tempXcmCount) {
            val startX = i * pxPerCM
            canvas.drawLine(startX, 0f, startX, -10f, mPaint)
        }

        val tempYcmCount = (centerY / pxPerCM).roundToInt()
        for (i in 1..tempYcmCount) {
            val startY = -i * pxPerCM
            canvas.drawLine(0f, startY, 10f, startY, mPaint)
        }
        for (i in 1..tempYcmCount) {
            val startY = i * pxPerCM
            canvas.drawLine(0f, startY, 10f, startY, mPaint)
        }

        drawLines(canvas)

        canvas.restore()
    }

    private fun drawLines(canvas: Canvas) {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND

        var xIndex = xRange.first.toFloat()
        while (xIndex < xRange.last) {
            val x = xIndex * pxPerCM
            val y = -(function(xIndex) * pxPerCM)
            Log.d("CoordinateView", "x=$xIndex  y=${function(xIndex)}")
            Log.d("CoordinateView", "x=$x  y=$y")
            mLinePath.reset()

            if (mTempPoint != null) {
                mLinePath.moveTo(mTempPoint!!.first, mTempPoint!!.second)
                mLinePath.lineTo(x, y)
                canvas.drawPath(mLinePath, mPaint)
            }
            mTempPoint = x to y
//            canvas.drawPoint(x, y, mThickPaint)
            xIndex += xStep
        }
        mTempPoint = null
    }

    private fun function(x: Number): Float {
        val realX = x.toFloat()
//        val y = x.toFloat() * 2 + 1
        val y = realX + 3 / realX
//        val y = 1 / realX
//        val y = realX * realX + 1
        return y
    }
}