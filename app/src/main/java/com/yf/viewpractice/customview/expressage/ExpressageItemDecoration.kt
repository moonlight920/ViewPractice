package com.yf.viewpractice.customview.expressage

import android.content.res.Resources
import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yf.viewpractice.R
import com.yf.viewpractice.debugLog
import com.yf.viewpractice.utils.BitmapUtil
import com.yf.viewpractice.utils.Utils



class ExpressageItemDecoration : RecyclerView.ItemDecoration() {

    val OFFSET_TOP = Utils.dp2px(10f)
    val OFFSET_LEFT = Utils.dp2px(108f)
    val OFFSET_BOTTOM = Utils.dp2px(10f)

    // 大圆圈半径
    val CIRCLE_RADIUS = Utils.dp2px(17f)
    // 小圆圈半径
    val SMALL_CIRCLE_RADIUS = Utils.dp2px(4f)

    val ICON_WIDTH = Utils.dp2px(20f)

    // 时间文字的上下间距
    val TEXT_TIME_MARGIN = Utils.dp2px(6f)

    val GRAY = Color.parseColor("#cccccc")

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = GRAY
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    private val dashLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = GRAY
        style = Paint.Style.STROKE
        strokeWidth = 3f
        pathEffect = DashPathEffect(floatArrayOf(4f, 4f), 0f)
    }

    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val circleOuterPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }
    private val circleBluePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#007efe")
        style = Paint.Style.FILL_AND_STROKE
    }
    private val circleInnerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val smallCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.FILL
    }

    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        color = Color.GRAY
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 此处会覆盖item

        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            val position = (child.layoutParams as (RecyclerView.LayoutParams)).viewLayoutPosition
            val expressInfo = DataHolder.dataList[position]

            debugLog("layout index : $position")

            val middleLineTopX = child.left.toFloat() - OFFSET_LEFT / 4
            val middleLineTopY = child.top.toFloat() - OFFSET_TOP
            val middleLineBottomX = child.left.toFloat() - OFFSET_LEFT / 4
            val middleLineBottomY = child.bottom.toFloat() + OFFSET_BOTTOM

            val circleX = middleLineTopX
            val circleY = (middleLineTopY + middleLineBottomY) / 2

            if (expressInfo.status == DeliverStatus.HEADER) {
                // 画虚线
                val path = Path()
                path.moveTo(middleLineTopX, (middleLineTopY + middleLineBottomY) / 2)
                path.lineTo(middleLineBottomX, middleLineBottomY)
                c.drawPath(path,dashLinePaint)

                c.drawOuterColorCircle(circleX, circleY)
                c.drawCenterText("收", circleX, circleY)
            } else {
                if (DataHolder.dataList.lastIndex != position) {
                    // 画流水线
                    c.drawLine(
                        middleLineTopX,
                        middleLineTopY,
                        middleLineBottomX,
                        middleLineBottomY,
                        paint
                    )
                } else {
//                    最后一个线条
                    c.drawLine(
                        middleLineTopX,
                        middleLineTopY,
                        middleLineBottomX,
                        (middleLineTopY + middleLineBottomY) / 2,
                        paint
                    )
                }
                // 写时间
                val dataStr = expressInfo.date
                val textRect = Rect()
                // 测量文字宽度
                textPaint.getTextBounds(dataStr, 0, dataStr.length, textRect)
                // 写第一行文字
                textPaint.textSize = Utils.sp2px(16f).toFloat()
                c.drawText(
                    dataStr,
                    middleLineTopX / 2,
                    (middleLineTopY + middleLineBottomY) / 2f,
                    textPaint
                )
                textPaint.textSize = Utils.sp2px(12f).toFloat()
                c.drawText(
                    expressInfo.time,
                    middleLineTopX / 2,
                    (middleLineTopY + middleLineBottomY) / 2f + textRect.height() + TEXT_TIME_MARGIN,
                    textPaint
                )
                // 画图标
                when (expressInfo.status) {
                    DeliverStatus.RECEIVED -> {
                        c.drawOuterColorCircle(circleX, circleY)
                        c.drawInnerBitmap(parent.context.resources, circleX, circleY, R.drawable.icon_right_mark)
                    }
                    DeliverStatus.DELIVERING -> {
                        // 派送中
                        c.drawOuterCircle(circleX, circleY)
                        c.drawInnerBitmap(parent.context.resources, circleX, circleY, R.drawable.icon_mine)

                    }
                    DeliverStatus.TRANSPORTING -> {
                        // 运输中
                        c.drawOuterCircle(circleX, circleY)
                        c.drawInnerBitmap(parent.context.resources, circleX, circleY, R.drawable.icon_express)
                    }
                    DeliverStatus.COLLECTING -> {
                        c.drawOuterCircle(circleX, circleY)
                        c.drawInnerBitmap(parent.context.resources, circleX, circleY, R.drawable.icon_collecting)

                    }
                    else -> {
                        c.drawCircle(
                            circleX,
                            circleY,
                            SMALL_CIRCLE_RADIUS.toFloat(),
                            smallCirclePaint
                        )
                    }
                }
            }


        }

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 此处画在item下面
        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            // 分割线
//            c.drawLine(
//                child.left.toFloat(),
//                child.bottom.toFloat(),
//                child.right.toFloat(),
//                child.bottom.toFloat(),
//                paint
//            )
//
//            c.drawLine(
//                child.left.toFloat(),
//                child.top.toFloat(),
//                child.right.toFloat(),
//                child.top.toFloat(),
//                paint
//            )
        }
    }


    private fun Canvas.drawOuterCircle(circleX: Float, circleY: Float) {
        drawCircle(circleX, circleY, CIRCLE_RADIUS.toFloat(), circleOuterPaint)
        drawCircle(
            circleX,
            circleY,
            CIRCLE_RADIUS - 2f,
            circleInnerPaint
        )
    }

    private fun Canvas.drawOuterColorCircle(circleX: Float, circleY: Float) {
        drawCircle(circleX, circleY, CIRCLE_RADIUS.toFloat(), circleBluePaint)
    }

    private fun Canvas.drawInnerBitmap(resources: Resources, circleX: Float, circleY: Float, id: Int) {
        val icon = BitmapUtil.getBitmap(resources, id, ICON_WIDTH)
        drawBitmap(icon, circleX - ICON_WIDTH / 2, circleY - ICON_WIDTH / 2, bitmapPaint)
    }

    private fun Canvas.drawCenterText(text: String, centerX: Float, centerY: Float) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isFakeBoldText = true
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = Utils.sp2px(16f).toFloat()
        val fontMetrics = Paint.FontMetrics()
        paint.getFontMetrics(fontMetrics)
        val offset1 = (fontMetrics.top + fontMetrics.bottom) / 2
        drawText(text, centerX, centerY - offset1, paint)

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(OFFSET_LEFT, OFFSET_TOP, 0, OFFSET_BOTTOM)
    }
}