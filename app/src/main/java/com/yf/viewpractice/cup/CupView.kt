package com.yf.viewpractice.cup

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yf.viewpractice.utils.Utils
import android.graphics.Shader
import android.graphics.Color.parseColor
import android.graphics.LinearGradient
import com.yf.viewpractice.debugLog
import kotlin.random.Random


class CupView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val cupWidth = Utils.dp2px(170f).toFloat()
    val cupHeight = Utils.dp2px(370f).toFloat()
    val cupWallWidth = Utils.dp2px(10f).toFloat()

    val waterWidth = Utils.dp2px(174f).toFloat()
    val waterHeight = Utils.dp2px(334f).toFloat()
    // 水面距离杯子顶部的距离
    val marginCupTop = Utils.dp2px(18f).toFloat()

    val waterRect = Rect(
        (-cupWidth / 2 + cupWallWidth / 2).toInt(),
        (-cupHeight / 2 + marginCupTop).toInt(),
        (cupWidth / 2 - cupWallWidth / 2).toInt(),
        (cupHeight / 2 - cupWallWidth / 2).toInt()
    )

    val cupPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeJoin = Paint.Join.ROUND
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = cupWallWidth
    }

    val waterPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        shader = LinearGradient(
            0f, waterRect.top.toFloat(), 0f, waterRect.bottom.toFloat(), Color.parseColor("#4486ed"),
            Color.parseColor("#363c48"), Shader.TileMode.CLAMP
        )
    }

    val bubblePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#ccffffff")
    }


    val cupPath = Path()

    var isRunning = false

    var xRange = IntRange((waterRect.left + waterRect.right) / 2 - 2, (waterRect.left + waterRect.right) / 2 + 2)
    var yRange =
        IntRange((cupHeight / 2 - cupWallWidth / 2).toInt() - 2, (cupHeight / 2 - cupWallWidth / 2).toInt() + 2)

    init {
        setBackgroundColor(Color.GRAY)
        BubbleHolder.addBubbleList(xRange, yRange, 2)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isRunning = true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isRunning = false
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.translate(width.toFloat() / 2, height.toFloat() / 2)
        cupPath.moveTo(-cupWidth / 2, -cupHeight / 2)
        cupPath.lineTo(-cupWidth / 2, cupHeight / 2)
        cupPath.lineTo(cupWidth / 2, cupHeight / 2)
        cupPath.lineTo(cupWidth / 2, -cupHeight / 2)
        canvas.drawPath(cupPath, cupPaint)

        canvas.drawRect(waterRect, waterPaint)

        BubbleHolder.dataList.forEach {
            canvas.drawCircle(it.x.toFloat(), it.y.toFloat(), it.radius.toFloat(), bubblePaint)
        }
    }

    fun start() {
        Thread {
            while (isRunning) {
                Thread.sleep(35)
                BubbleHolder.dataList.forEach {
                    // 计算边界
                    var circleX = it.x.toFloat()
                    if (circleX <= waterRect.left + it.radius / 2) {
                        circleX = waterRect.left.toFloat() + it.radius / 2
                    }
                    if (circleX >= waterRect.right - it.radius / 2) {
                        circleX = waterRect.right.toFloat() - it.radius / 2
                    }
                    // 增加位移
                    it.x = circleX.toInt() + it.speedX.toInt()
                    it.y -= it.speedY.toInt()
                }
                BubbleHolder.dataList.removeIf {
                    it.y <= waterRect.top
                }
                if (BubbleHolder.dataList.size <= 9) {
                    BubbleHolder.addBubbleList(xRange, yRange, 1)
                }
                postInvalidate()
            }
        }.start()
    }
}