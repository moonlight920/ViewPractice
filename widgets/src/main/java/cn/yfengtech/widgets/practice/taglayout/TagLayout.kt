package cn.yfengtech.widgets.practice.taglayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.forEachIndexed

class TagLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attrs, defStyleAttr) {

    val ORIENTATION_MIDDLE_PADDING = 30
    val VERTICAL_MIDDLE_PADDING = 12

    val childBounds = arrayListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val layoutWidth = MeasureSpec.getSize(widthMeasureSpec)

        // 这个父容器的宽度
        var widthUsed = 0
        // 当前行的宽度
        var lineWidthUsed = 0
        // 已用高度
        var heightUsed = 0
        // 当前行，height的最大值
        var lineMaxHeight = 0

        forEachIndexed { index, view ->
            // 根据剩余空间，计算子view位置
            measureChildWithMargins(
                view,
                widthMeasureSpec,
                0,
                heightMeasureSpec,
                heightUsed
            )

            var orientationPadding = if (index == 0) 0 else ORIENTATION_MIDDLE_PADDING

            if (lineWidthUsed + view.measuredWidth + orientationPadding > layoutWidth) {
                // 换行
                orientationPadding = 0
                heightUsed += lineMaxHeight + VERTICAL_MIDDLE_PADDING
                lineMaxHeight = 0
                lineWidthUsed = 0
                measureChildWithMargins(
                    view,
                    widthMeasureSpec,
                    0,
                    heightMeasureSpec,
                    heightUsed
                )
            }
            if (childBounds.size <= index) {
                childBounds.add(Rect())
            }
            childBounds[index].set(
                lineWidthUsed + orientationPadding,
                heightUsed,
                view.measuredWidth + lineWidthUsed + orientationPadding,
                view.measuredHeight + heightUsed
            )
            lineWidthUsed += view.measuredWidth + orientationPadding
            widthUsed = Math.max(lineWidthUsed, widthUsed)
            lineMaxHeight = Math.max(lineMaxHeight, view.measuredHeight)
        }

        val width = widthUsed
        val height = heightUsed + lineMaxHeight

        setMeasuredDimension(width, height)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        forEachIndexed { index, view ->
            val rect = childBounds[index]
            view.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}