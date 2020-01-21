package cn.yfengtech.widgets.practice

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 仿写搜狐播放器加载动画
 *
 * @created songyifeng
 * @date 2019-08-06 19:30
 */
internal class TriangleLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val RADIUS = 100f

    private val allTrianglePath = Path()
    private val allTrianglePathMeasure = PathMeasure()

    private val dstPath = Path()

    /**
     * 底色画笔
     */
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 10f
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }
    /**
     * 红色画笔
     */
    private val redPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 10f
        color = Color.parseColor("#E94147")
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private var startProgress = 0f
    private var endProgress = 0f

    private var rotateDegree = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val centerX = width / 2.toFloat()
        val centerY = height / 2.toFloat()

        allTrianglePath.reset()
        allTrianglePath.addPath(getTrianglePath(centerX, centerY, RADIUS))

        allTrianglePathMeasure.setPath(allTrianglePath, true)
    }

    /**
     * 通过中心点和边长，获取一个等边三角形 path
     */
    private fun getTrianglePath(centerX: Float, centerY: Float, sideLength: Float): Path {
        val path = Path()

        val mTan30 = Math.tan(Math.toRadians(30.0)).toFloat()
        val pointA = PointF(centerX - mTan30 * sideLength / 2, centerY - sideLength / 2)

        val mCos30 = Math.cos(Math.toRadians(30.0)).toFloat()
        val pointB = PointF(centerX + (sideLength / 2) / mCos30, centerY)

        val pointC = PointF(pointA.x, pointA.y + sideLength)

        path.moveTo(pointA.x, pointA.y)
        path.lineTo(pointB.x, pointB.y)
        path.lineTo(pointC.x, pointC.y)
        path.close()
        return path
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.rotate(rotateDegree, width / 2.toFloat(), height / 2.toFloat())
        canvas.drawPath(allTrianglePath, bgPaint)
        allTrianglePathMeasure.getSegment(
            startProgress * allTrianglePathMeasure.length,
            endProgress * allTrianglePathMeasure.length,
            dstPath,
            true
        )
        canvas.drawPath(dstPath, redPaint)
        dstPath.reset()
    }

    private val anim = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 600
        addUpdateListener {
            startProgress = 0f
            endProgress = it.animatedValue as Float
            invalidate()
        }
    }

    private val reverseAnim = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 400
        addUpdateListener {
            startProgress = it.animatedValue as Float
            endProgress = 1f
            invalidate()
        }
    }

    private val rotateAnim = ValueAnimator.ofFloat(0f, 120f).apply {
        duration = 400
        addUpdateListener {
            rotateDegree = it.animatedValue as Float
            invalidate()
        }
    }


    private val animList = AnimatorSet()

    override fun onFinishInflate() {
        super.onFinishInflate()

//        animList.playSequentially(anim, rotateAnim)
        animList.play(rotateAnim).with(reverseAnim).after(anim)
        animList.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                animList.play(rotateAnim).with(reverseAnim).after(anim)
                animList.start()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animList.start()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animList.resume()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animList.pause()
    }
}