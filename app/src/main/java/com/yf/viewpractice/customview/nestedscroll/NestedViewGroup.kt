package com.yf.viewpractice.customview.nestedscroll

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.*

class NestedViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    companion object {
        val TAG = "NestedViewGroup"
    }


    private var mLastMotionY = 0f
    private val mVeloTracker = VelocityTracker.obtain()
    private val mScroller = OverScroller(context)

    private val childBounds = arrayListOf<Rect>()


    private var displayHeight = 0
    /**
     * 测量
     */
    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val layoutWidth = MeasureSpec.getSize(widthMeasureSpec)
        val layoutHeight = MeasureSpec.getSize(heightMeasureSpec)
        displayHeight = layoutHeight

        // 已用高度
        var heightUsed = paddingTop
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
            childBounds.add(
                Rect(left, top, right, bottom)
            )

            // 已用高度累加
            heightUsed += child.measuredHeight + child.marginTop + child.marginBottom
        }

        val totalHeight = heightUsed + paddingBottom
        if (totalHeight < layoutHeight) {
            displayHeight = totalHeight
        }
        setMeasuredDimension(layoutWidth, totalHeight)
    }

    /**
     * 布局
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        val layoutHeight = MeasureSpec.getSize(heightMeasureSpec)
        forEachIndexed { index, view ->
            val rect = childBounds[index]
            view.layout(rect.left, rect.top, rect.right, rect.bottom)
            Log.i(TAG, "info ${view.javaClass.simpleName}  ${view.isScrollContainer}")
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
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

    private fun springBack() {
        if (mScroller.springBack(0, scrollY, 0, width, top, bottom - displayHeight)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private fun fling(yVelocity: Float) {
        mScroller.forceFinished(true)
        mScroller.fling(0, scrollY, 0, yVelocity.toInt()*3, 0, width, top, bottom - displayHeight)
        ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.currY)
            postInvalidate()
        }
    }

    private fun isOverScroll() = scrollY !in top..bottom - displayHeight
}