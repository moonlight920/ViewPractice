package cn.yfengtech.widgets.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.yfengtech.widgets.debugLog


class PaintAttrView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val startColor = Color.parseColor("#E91E63")
    val endColor = Color.parseColor("#2196F3")

    override fun dispatchDraw(canvas: Canvas?) {
        debugLog("before dispatchDraw")
        super.dispatchDraw(canvas)
        debugLog("after dispatchDraw")
    }


    override fun draw(canvas: Canvas) {
        debugLog("before draw")
        super.draw(canvas)
        debugLog("after draw")

        canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), width / 2.toFloat(), paint)
    }

    override fun onDraw(canvas: Canvas?) {
        debugLog("before onDraw")
        super.onDraw(canvas)
        debugLog("after onDraw")
    }

    override fun onDrawForeground(canvas: Canvas?) {
        debugLog("before onDrawForeground")
        super.onDrawForeground(canvas)
        debugLog("after onDrawForeground")
    }

    fun setShader(gradientType: Int? = null, apply: Boolean = true) {
        if (apply) {
            val shader = when (gradientType) {
                GRADIENT_RADIAL -> {
                    RadialGradient(
                        width / 2.toFloat(),
                        height / 2.toFloat(),
                        width / 2.toFloat(),
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
                GRADIENT_SWEEP -> {
                    SweepGradient(width / 2.toFloat(), height / 2.toFloat(), startColor, endColor)
                }
                else -> {
                    LinearGradient(
                        0f, 0f, width.toFloat(), height.toFloat(), startColor,
                        endColor, Shader.TileMode.CLAMP
                    )
                }
            }
            paint.shader = shader
        } else {
            paint.shader = null
        }
        invalidate()
    }

    companion object {
        const val GRADIENT_LINEAR = 0
        const val GRADIENT_RADIAL = 1
        const val GRADIENT_SWEEP = 2
    }

}