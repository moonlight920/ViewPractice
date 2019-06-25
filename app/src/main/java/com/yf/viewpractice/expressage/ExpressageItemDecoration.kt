package com.yf.viewpractice.expressage

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yf.viewpractice.debugLog

class ExpressageItemDecoration : RecyclerView.ItemDecoration() {

    val OFFSET_TOP = 20
    val OFFSET_LEFT = 108
    val OFFSET_BOTTOM = 20

    // 大圆圈半径
    val CIRCLE_RADIUS = 12f
    // 小圆圈半径
    val SMALL_CIRCLE_RADIUS = 4f

    val ICON_WIDTH = 50

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val circleOuterPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val circleInnerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val smallCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 时间文字的上下间距
    val TEXT_TIME_MARGIN = 6f
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.style = Paint.Style.STROKE

        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = Color.GRAY

        smallCirclePaint.color = Color.GRAY
        smallCirclePaint.style = Paint.Style.FILL

        circleOuterPaint.color = Color.GRAY
        circleOuterPaint.style = Paint.Style.STROKE
        circleOuterPaint.strokeWidth = 3f

        circleInnerPaint.color = Color.WHITE
        circleInnerPaint.style = Paint.Style.FILL
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 此处会覆盖item

        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            val position = (child.layoutParams as (RecyclerView.LayoutParams)).viewLayoutPosition

            debugLog("layout index : $position")

            val middleLineTopX = child.left.toFloat() - OFFSET_LEFT / 4
            val middleLineTopY = child.top.toFloat() - OFFSET_TOP
            val middleLineBottomX = child.left.toFloat() - OFFSET_LEFT / 4
            val middleLineBottomY = child.bottom.toFloat() + OFFSET_BOTTOM

            // 画流水线
            c.drawLine(
                middleLineTopX,
                middleLineTopY,
                middleLineBottomX,
                middleLineBottomY,
                paint
            )

            // 写时间
            val dataStr = DataHolder.dataList[position].data
            val textRect = Rect()
            // 测量文字宽度
            textPaint.getTextBounds(dataStr, 0, dataStr.length, textRect)
            // 写第一行文字
            textPaint.textSize = 20f
            c.drawText(
                dataStr,
                middleLineTopX / 2,
                (middleLineTopY + middleLineBottomY) / 2f,
                textPaint
            )
            textPaint.textSize = 18f
            c.drawText(
                DataHolder.dataList[position].time,
                middleLineTopX / 2,
                (middleLineTopY + middleLineBottomY) / 2f + textRect.height() + TEXT_TIME_MARGIN,
                textPaint
            )
            // 画快递车icon
//                val icon = BitmapUtil.getBitmap(parent.resources, R.drawable.icon_express, ICON_WIDTH)
//                c.drawBitmap(icon, middleLineTopX, (middleLineTopY + middleLineBottomY - ICON_WIDTH) / 2, bitmapPaint)

            // 画圆圈
            if (position % 5 == 0) {
                c.drawCircle(middleLineTopX, (middleLineTopY + middleLineBottomY) / 2, CIRCLE_RADIUS, circleOuterPaint)
                c.drawCircle(
                    middleLineTopX,
                    (middleLineTopY + middleLineBottomY) / 2,
                    CIRCLE_RADIUS - 2,
                    circleInnerPaint
                )
            } else {
                c.drawCircle(
                    middleLineTopX,
                    (middleLineTopY + middleLineBottomY) / 2,
                    SMALL_CIRCLE_RADIUS,
                    smallCirclePaint
                )
            }
        }

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 此处画在item下面
        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            // 分割线
            c.drawLine(
                child.left.toFloat(),
                child.bottom.toFloat(),
                child.right.toFloat(),
                child.bottom.toFloat(),
                paint
            )
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(OFFSET_LEFT, OFFSET_TOP, 0, OFFSET_BOTTOM)
    }
}