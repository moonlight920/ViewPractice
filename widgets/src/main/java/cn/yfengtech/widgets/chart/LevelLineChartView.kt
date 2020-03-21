package cn.yfengtech.widgets.chart

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.Property
import android.util.TypedValue
import android.view.View
import cn.yfengtech.widgets.R

private const val TAG = "LevelLineChartView"
private const val DEBUG = true
private const val ANIM_DURING = 3000L

/**
 * 折线图渐变色
 */
private val CART_LINE_GRADIENT_COLOR_ARRAY = intArrayOf(
    Color.parseColor("#43A8FF"), Color.parseColor("#1BCF3D"),
    Color.parseColor("#A9E819")
)

/**
 * 折线图下方阴影 渐变色
 */
private val SHADOW_CART_LINE_GRADIENT_COLOR_ARRAY = intArrayOf(
    Color.parseColor("#50C9A5"), Color.TRANSPARENT
)

/**
 * 背景线渐变色 和 渐变位置信息
 */
private val BG_LINE_GRADIENT_PAIR = intArrayOf(
    Color.parseColor("#005463DB"), Color.parseColor("#ff4EC2FF"),
    Color.parseColor("#ff4EC2FF"), Color.parseColor("#005463DB")
) to floatArrayOf(0f, 0.2f, 0.8f, 1f)

/**
 * 纸屏 折线和文字的颜色
 */
private val COLOR_EINK_BLACK = Color.parseColor("#ff585858")

/**
 * 纸屏 折线阴影颜色
 */
private val COLOR_EINK_CART_LINE_SHADOW = Color.parseColor("#E8E8E8")

private val EINK_BG_LINE_GRADIENT_PAIR = intArrayOf(
    Color.parseColor("#00585858"), COLOR_EINK_BLACK,
    COLOR_EINK_BLACK, Color.parseColor("#00585858")
) to floatArrayOf(0f, 0.2f, 0.8f, 1f)

/**
 * 知识点报告页 折线图
 *
 * @author syf
 * @date 2020-03-19 10:55:59
 */
class LevelLineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    /**
     * 为知识点报告页 定义掌握等级
     */
    enum class Level {
        // 训练不足/C/B/A/已完成
        ZERO, C, B, A, FINISH
    }

    companion object {
        private val LEVEL_LIST = mutableListOf(
            Level.C to "层级C",
            Level.B to "层级B",
            Level.A to "层级A"
        )
    }

    /**
     * 兼容纸屏
     */
    private val isEink: Boolean

    /**
     * 文字和折线图 间距
     */
    private val mBottomTextSpacing: Int

    /**
     * 折线图 线条宽度
     */
    private val mCartLineWidth: Float

    private val mPaddingLeft: Int
    private val mPaddingTop: Int
    private val mPaddingRight: Int
    private val mPaddingBottom: Int

    /**
     * 背景线条 线条画笔
     */
    private val mBgLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    /**
     * 折线 线条画笔
     */
    private val mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    /**
     *
     */
    private val mShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    /**
     * 折线 线条路径
     */
    private val mAllLinePath = Path()

    /**
     * 折线下方阴影形状路径
     */
    private val mAllLineShadowPath = Path()

    /**
     * 高亮圆点坐标
     */
    private var mCirclePoint: PointF? = null

    /**
     * 横线的path集合
     */
    private val mHorizontalLinePathList = mutableListOf<Path>()

    /**
     * 横线和斜线的path集合
     */
    private val mAllLinePathList = mutableListOf<Path>()

    /**
     * 当前所处层级和当前所处层级的进度
     */
    private var mCurrentLevel: Level? = null
    private var mCurrentLevelProgress: Float? = null

    /**
     * 在折线图中走过的百分比
     */
    private var mLinePathProgress: Float = 0f

    /**
     * path测量长度工具
     */
    private val mPathMeasure = PathMeasure()

    /**
     * 折线图矩形区域
     */
    private val mCartRectF = RectF()

    /**
     * 文字
     */
    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    /**
     * 底部所占文字高度
     */
    private fun getTextDescHeight(): Float =
        mTextPaint.fontMetrics.bottom - mTextPaint.fontMetrics.top

    /**
     * 圆点 paint
     */
    private val mCirclePointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    /**
     * 已完成 气泡的bitmap
     */
    private val mFinishBubbleBitmap: Bitmap

    /**
     * 高亮点上方气泡尺寸  宽-高
     */
    private val mBubbleSize: Pair<Float, Float>

    /**
     * 气泡纵向偏移坐标
     */
    private val mBubbleOffsetY: Float

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LevelLineChartView,
            R.attr.levelLineChartView,
            R.style.levelLineChartDefault
        )
        mBottomTextSpacing = typedArray.getDimensionPixelOffset(
            R.styleable.LevelLineChartView_lc_bottom_desc_spacing,
            50
        )
        mPaddingLeft =
            typedArray.getDimensionPixelOffset(R.styleable.LevelLineChartView_lc_padding_left, 0)
        mPaddingTop =
            typedArray.getDimensionPixelOffset(R.styleable.LevelLineChartView_lc_padding_top, 0)

        mPaddingRight =
            typedArray.getDimensionPixelOffset(R.styleable.LevelLineChartView_lc_padding_right, 0)
        mPaddingBottom =
            typedArray.getDimensionPixelOffset(R.styleable.LevelLineChartView_lc_padding_bottom, 0)
        isEink = typedArray.getBoolean(R.styleable.LevelLineChartView_lc_is_eink, false)
        typedArray.recycle()

        if (!isEink) {
            mCartLineWidth = dp2px(6f)
            mTextPaint.textSize = sp2px(18f)
            mTextPaint.color = Color.parseColor("#39D9FD")

            mBubbleSize = dp2px(50f) to dp2px(38f)
            mBubbleOffsetY = -dp2px(30f)
            mFinishBubbleBitmap = BitmapFactory.decodeResource(
                resources,
                R.drawable.icon_finish
            )
        } else {
            mCartLineWidth = dp2px(6f)
            mTextPaint.textSize = sp2px(32f)
            mTextPaint.color = COLOR_EINK_BLACK

            mBubbleSize = dp2px(110f) to dp2px(70f)
            mBubbleOffsetY = -dp2px(50f)
            mFinishBubbleBitmap = BitmapFactory.decodeResource(
                resources,
                R.drawable.icon_eink_finish
            )
        }
    }

    /**
     * 布局完成之后，计算path等信息
     *
     * 计算主体折线图，受影响因素较多
     * 折线图高度 = view总高度 - 底部文字高度 - 上下padding - （气泡高度和偏移量）
     * 折线图宽度 = view总宽度 - 左右padding - 气泡的宽度
     */
    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val lineCount = LEVEL_LIST.size // 直线条数
        val biasCount = lineCount - 1 // 斜线条数

        // 气泡宽高和偏移量也算在内
        val bubbleHeight = mBubbleSize.second - mBubbleOffsetY
        val bubbleWidth = mBubbleSize.first
        val cartWidth = width - mPaddingLeft - mPaddingRight - bubbleWidth
        // 计算折线总高度
        // 线条宽度算进去，防止被裁切
        val cartHeight =
            (height - mCartLineWidth - getTextDescHeight()) - mPaddingTop - mPaddingBottom - mBottomTextSpacing - bubbleHeight

        mCartRectF.set(
            mPaddingLeft.toFloat() + bubbleWidth / 2,
            mPaddingTop.toFloat() + bubbleHeight,
            mPaddingLeft + cartWidth + bubbleWidth / 2,
            mPaddingTop + cartHeight + bubbleHeight
        )

        // 区域内有两条斜线，斜度45，宽高相等，+1是因为底部要空出区域
        val biasHeight = cartHeight / (biasCount + 1)
//        tan(Math.PI / 180 * 45)
        val biasWidth = biasHeight

        val lineWidth = (cartWidth - biasWidth * biasCount) / lineCount

        // 开始绘制点
        var endWidth = mCartRectF.left
//        var endHeight = cartHeight + mLineWidth / 2
        var endHeight = mCartRectF.bottom - biasHeight

        mHorizontalLinePathList.clear()
        mAllLinePathList.clear()

        mAllLinePath.moveTo(endWidth, endHeight)
        for (index in 0 until lineCount) {
            // 横线
            if (index < lineCount) {
                // 只存集合中用于计算
                val path = Path().apply {
                    moveTo(endWidth, endHeight)
                    endWidth += lineWidth
                    lineTo(endWidth, endHeight)
                }
                mHorizontalLinePathList.add(path)
                mAllLinePathList.add(path)

                // 实际绘制
                mAllLinePath.lineTo(endWidth, endHeight)
            }
            // 斜线
            if (index < biasCount) {
                // 只用于计算路径length
                val path = Path().apply {
                    moveTo(endWidth, endHeight)
                    endWidth += biasWidth
                    endHeight -= biasHeight
                    lineTo(endWidth, endHeight)
                }
                mAllLinePathList.add(path)

                // 实际绘制
                mAllLinePath.lineTo(endWidth, endHeight)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // eink无法做透明，这里使用不同绘制顺序
        if (isEink) {
            // 绘制折线
            drawCartLine(canvas)
            // 绘制向下阴影
            drawCartLineShadow(canvas)
            // 绘制背景线条
            drawBgLine(canvas)
        } else {
            // 绘制背景线条
            drawBgLine(canvas)
            // 绘制折线
            drawCartLine(canvas)
            // 绘制向下阴影
            drawCartLineShadow(canvas)
        }

        // 绘制高亮点
        drawDataPoint(canvas)
        // 绘制底部文案
        drawBottomDescText(canvas)
        // 绘制气泡
        drawBubble(canvas)
    }

    private fun drawCartLine(canvas: Canvas) {
        // 绘制折线
        if (!isEink) {
            mLinePaint.shader = LinearGradient(
                mCartRectF.left,
                0f,
                mCartRectF.right,
                0f,
                CART_LINE_GRADIENT_COLOR_ARRAY,
                null,
                Shader.TileMode.CLAMP
            )
            mLinePaint.setShadowLayer(dp2px(6f), 0f, 0f, mLinePaint.color)
        } else {
            mLinePaint.color = COLOR_EINK_BLACK
        }
        mLinePaint.strokeWidth = mCartLineWidth
        canvas.drawPath(mAllLinePath, mLinePaint)
    }

    /**
     * 绘制向下的阴影
     */
    private fun drawCartLineShadow(canvas: Canvas) {
        mPathMeasure.setPath(mAllLinePath, false)

        mAllLineShadowPath.reset()
        mPathMeasure.getSegment(
            0f, mPathMeasure.length * mLinePathProgress,
            mAllLineShadowPath, true
        )
        // 当前高亮圆点的位置
        val point = getPathPoint(mAllLinePath, mLinePathProgress)
        mAllLineShadowPath.apply {
            lineTo(point.x, mCartRectF.bottom)
            lineTo(mCartRectF.left, mCartRectF.bottom)
            close()
            offset(0f, mCartLineWidth / 2)
        }
        if (!isEink) {
            mShadowPaint.shader = LinearGradient(
                point.x,
                mCartRectF.top - mCartRectF.height(),
                point.x,
                mCartRectF.bottom,
                SHADOW_CART_LINE_GRADIENT_COLOR_ARRAY,
                null,
                Shader.TileMode.CLAMP
            )
            canvas.drawPath(mAllLineShadowPath, mShadowPaint)
        } else {
            mShadowPaint.color = COLOR_EINK_CART_LINE_SHADOW
            canvas.drawPath(mAllLineShadowPath, mShadowPaint)
        }
    }

    /**
     * 画背景的渐变线条
     */
    private fun drawBgLine(canvas: Canvas) {
        // 因为是渐变线，增加些偏移量
        val startOffset = -dp2px(36f)
        val endOffset = 0f

        var startX = mCartRectF.left + startOffset
        var endX = mCartRectF.right + endOffset
        var y = mCartRectF.bottom

        mBgLinePaint.shader = LinearGradient(
            startX, y, endX, y,
            if (!isEink) BG_LINE_GRADIENT_PAIR.first else EINK_BG_LINE_GRADIENT_PAIR.first,
            if (!isEink) BG_LINE_GRADIENT_PAIR.second else EINK_BG_LINE_GRADIENT_PAIR.second,
            Shader.TileMode.CLAMP
        )
        // 先画一条底部的线
        canvas.drawLine(
            mCartRectF.left + startOffset,
            mCartRectF.bottom,
            mCartRectF.right,
            mCartRectF.bottom, mBgLinePaint
        )
        // 根据折线中横线的位置，画其他线
        mHorizontalLinePathList.forEach {
            val point = getPathPoint(it, 0f)
            startX = point.x + startOffset
            endX = mCartRectF.right + endOffset
            y = point.y + mCartLineWidth / 2
            mBgLinePaint.shader = LinearGradient(
                startX, y, endX, y,
                if (!isEink) BG_LINE_GRADIENT_PAIR.first else EINK_BG_LINE_GRADIENT_PAIR.first,
                if (!isEink) BG_LINE_GRADIENT_PAIR.second else EINK_BG_LINE_GRADIENT_PAIR.second,
                Shader.TileMode.CLAMP
            )
            canvas.drawLine(startX, y, endX, y, mBgLinePaint)
        }
    }

    /**
     * 设置当前level和进度
     *
     * @param level [com.okay.core.widget.LevelLineChartView.Level] 掌握层级
     * @param levelProgress 在当前层级的进度
     * @param anim 是否执行动画
     * @throws IllegalArgumentException progress必须在0-1之间
     */
    @JvmOverloads
    fun setCurrentLevel(level: Level, levelProgress: Float, anim: Boolean = false) {
        if (levelProgress < 0 || levelProgress > 1) throw IllegalArgumentException("progress必须在0-1之间")
        mCurrentLevel = level
        mCurrentLevelProgress = levelProgress
        post {
            val totalProgress = calcPercentage(level, levelProgress)
            log("totalProgress : $totalProgress")
            if (anim) {
                val objAnim =
                    ObjectAnimator.ofFloat(
                        this,
                        propChartProgress,
                        mLinePathProgress,
                        totalProgress
                    )
                objAnim.duration = ANIM_DURING
                objAnim.start()
            } else {
                synchronized(mLinePathProgress) {
                    mLinePathProgress = totalProgress
                    invalidate()
                }
            }
        }
    }

    /**
     * 在折线图上，标出数据点
     */
    private fun drawDataPoint(canvas: Canvas) {

        // 高亮坐标点
        val point = getPathPoint(mAllLinePath, mLinePathProgress)
        mCirclePoint = point
        if (!isEink) {
            mCirclePointPaint.strokeWidth = dp2px(4f)
            mCirclePointPaint.color = Color.WHITE
            mCirclePointPaint.shader = LinearGradient(
                point.x,
                point.y,
                point.x,
                mCartRectF.bottom,
                intArrayOf(Color.WHITE, Color.TRANSPARENT),
                null,
                Shader.TileMode.CLAMP
            )
        } else {
            mCirclePointPaint.strokeWidth = dp2px(6f)
            mCirclePointPaint.color = Color.parseColor("#888888")
        }
        // 绘制白点下方竖线
        canvas.drawLine(point.x, point.y, point.x, mCartRectF.bottom, mCirclePointPaint)

        // 外圈 圆点
        if (!isEink) {
            mCirclePointPaint.color = Color.parseColor("#4BFFFFFF")
            mCirclePointPaint.strokeWidth = dp2px(30f)
        } else {
            mCirclePointPaint.color = Color.BLACK
            mCirclePointPaint.strokeWidth = dp2px(54f)
        }
        mCirclePointPaint.strokeCap = Paint.Cap.ROUND

        canvas.drawPoint(point.x, point.y, mCirclePointPaint)

        // 内圈 圆点
        if (!isEink) {
            mCirclePointPaint.color = Color.WHITE
            mCirclePointPaint.strokeWidth = dp2px(14f)
        } else {
            mCirclePointPaint.color = Color.WHITE
            mCirclePointPaint.strokeWidth = dp2px(24f)
        }
        mCirclePointPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawPoint(point.x, point.y, mCirclePointPaint)
    }

    /**
     * 绘制气泡，紧密关联业务，当前只有100%的时候显示气泡
     */
    private fun drawBubble(canvas: Canvas) {
        if (mCurrentLevel == Level.FINISH
            && mLinePathProgress == 1f
            && mCirclePoint != null
        ) {
            val bitmapWidth = mBubbleSize.first
            val bitmapHeight = mBubbleSize.second
            val point = mCirclePoint!!
            val rectF = RectF(
                point.x - bitmapWidth / 2,
                point.y - bitmapHeight,
                point.x + bitmapWidth / 2,
                point.y
            )
            rectF.offset(0f, mBubbleOffsetY)
            canvas.drawBitmap(mFinishBubbleBitmap, null, rectF, Paint())
        }
    }

    /**
     * 计算点在全路径的百分比
     */
    private fun calcPercentage(level: Level, progress: Float): Float {
        if (level == Level.ZERO) {
            return 0f
        } else if (level == Level.FINISH) {
            return 1f
        } else {
            if (mHorizontalLinePathList.size > 0) {
                val index = getIndexByLevel(level)
                if (index < 0) return 0f
                val targetPath = mHorizontalLinePathList[index]

                var passLength = 0f
                var stop = false
                mAllLinePathList.forEach {
                    if (it != targetPath) {
                        if (!stop) passLength += getPathLength(it)
                    } else {
                        stop = true
                        passLength += getPathLength(it, progress)
                    }
                }

                val totalLength = getPathLength(mAllLinePath)

                // 返回通过的路径/总路径 百分比
                return passLength / totalLength
            } else {
                return 0f
            }
        }
    }

    /**
     * 获取层级所代表的的索引值
     */
    private fun getIndexByLevel(level: Level): Int {
        LEVEL_LIST.forEachIndexed { index, pair ->
            if (level == pair.first) {
                return index
            }
        }
        return -1
    }

    /**
     * 绘制底部文案
     */
    private fun drawBottomDescText(canvas: Canvas) {

        canvas.save()
        canvas.translate(0f, height.toFloat() - mPaddingBottom - getTextDescHeight())

        mHorizontalLinePathList.forEachIndexed { index, path ->

            val point = getPathPoint(path, 0.5f)

            val text = LEVEL_LIST.getOrNull(index)?.second ?: "null"

            //计算baseline
            val fontMetrics = mTextPaint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = getTextDescHeight() / 2 + distance
            canvas.drawText(text, point.x, baseline, mTextPaint)
        }
        canvas.restore()
    }

    /**
     * 获取path上的某坐标点
     */
    private fun getPathPoint(path: Path, progress: Float): PointF {
        val floatArray = FloatArray(2)
        mPathMeasure.setPath(path, false)
        mPathMeasure.getPosTan(mPathMeasure.length * progress, floatArray, null)
        return PointF(floatArray[0], floatArray[1])
    }

    /**
     * 根据progress获取给定path的length
     */
    private fun getPathLength(path: Path, progress: Float = 1f): Float {
        mPathMeasure.setPath(path, false)
        return mPathMeasure.length * progress
    }

    private fun log(str: String) = Log.d(TAG, str)

    private val propChartProgress: Property<LevelLineChartView, Float> =
        object : Property<LevelLineChartView, Float>(Float::class.java, "cart_progress") {
            override fun get(obj: LevelLineChartView): Float {
                return mLinePathProgress
            }

            override fun set(obj: LevelLineChartView, progress: Float) {
                mLinePathProgress = progress
                obj.invalidate()
            }
        }
}

private fun sp2px(sp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().displayMetrics)

private fun dp2px(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)

