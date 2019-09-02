package com.yf.viewpractice.customview.nestedscroll

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.*
import kotlin.math.max
import kotlin.math.min

class NestedViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    /**
     * 手指按下的y坐标
     */
    private var mLastMotionY = 0f

    /**
     * 滑动速度 计算帮助类
     */
    private val mVeloTracker = VelocityTracker.obtain()

    /**
     * 滚动计算帮助类
     */
    private val mScroller = OverScroller(context)

    /**
     * 内部子view的位置集合
     */
    private val childBounds = arrayListOf<Rect>()


    /**
     * child 总高度
     */
    private var childTotalHeight = 0

    /**
     * 测量
     */
    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (childCount > 0) {
            val layoutWidthMode = MeasureSpec.getMode(widthMeasureSpec)
            val layoutWidthSize = MeasureSpec.getSize(widthMeasureSpec)

            val layoutHeightMode = MeasureSpec.getMode(heightMeasureSpec)
            val layoutHeightSize = MeasureSpec.getSize(heightMeasureSpec)
            // 已用高度
            var heightUsed = paddingTop
            var maxWidth = 0
            childBounds.clear()
            forEachIndexed { _, child ->
                measureChildWithMargins(
                    child,
                    widthMeasureSpec,
                    paddingLeft,
                    heightMeasureSpec,
                    heightUsed
                )

                val left = paddingLeft + child.marginLeft
                val top = heightUsed + child.marginTop
                val right = left + child.measuredWidth
                val bottom = top + child.measuredHeight
                childBounds.add(Rect(left, top, right, bottom))

                // 已用高度累加
                heightUsed += child.measuredHeight + child.marginTop + child.marginBottom

                val childWidth = child.measuredWidth + child.marginLeft + child.marginRight
                maxWidth = max(maxWidth, childWidth)
            }

            val totalHeight = heightUsed + paddingBottom
            childTotalHeight = totalHeight

            var width = 0
            var height = 0
            when (layoutWidthMode) {
                MeasureSpec.EXACTLY -> {
                    width = layoutWidthSize
                }
                MeasureSpec.AT_MOST -> {
                    width = min(layoutWidthSize, maxWidth)
                }
                MeasureSpec.UNSPECIFIED -> {
                    width = layoutWidthSize
                }
            }
            when (layoutHeightMode) {
                MeasureSpec.EXACTLY -> {
                    height = layoutHeightSize
                }
                MeasureSpec.AT_MOST -> {
                    height = min(heightUsed, layoutHeightSize)
                }
                MeasureSpec.UNSPECIFIED -> {
                    height = layoutHeightSize
                }
            }
            setMeasuredDimension(width, height)
        }
    }

    /**
     * 布局
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        forEachIndexed { index, view ->
            val rect = childBounds[index]
            view.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        mVeloTracker.addMovement(event)

        return when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mLastMotionY = event.y
                mScroller.forceFinished(true)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                val diffY = mLastMotionY - event.y
                mLastMotionY = event.y
                if (isOverScroll()) {
                    scrollBy(0, diffY.toInt() * 1 / 2)
                } else {
                    scrollBy(0, diffY.toInt())
                }
                true
            }
            MotionEvent.ACTION_UP -> {
                mVeloTracker.computeCurrentVelocity(500, 2000f)
                if (isOverScroll()) {
                    springBack()
                } else {
                    fling(-mVeloTracker.yVelocity)
                }
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mLastMotionY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetY = ev.y - mLastMotionY
                forEach { view ->
                    if (view.isScrollContainer) {
                        if (offsetY > 0 && !view.canScrollVertically(-1) ||
                            offsetY < 0 && !view.canScrollVertically(1)
                        ) {
                            return true
                        }
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    /**
     * 回弹
     */
    private fun springBack() {
        if (mScroller.springBack(0, scrollY, 0, width, 0, childTotalHeight - height)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    /**
     * 惯性滑动
     */
    private fun fling(yVelocity: Float) {
        mScroller.forceFinished(true)
        mScroller.fling(
            0,
            scrollY,
            0,
            yVelocity.toInt() * 3,
            0,
            width,
            0,
            childTotalHeight - height
        )
        ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.currY)
            postInvalidate()
        }
    }

    /**
     * 判断滑动是否越界
     */
    private fun isOverScroll() = scrollY !in 0..(childTotalHeight - height)

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}