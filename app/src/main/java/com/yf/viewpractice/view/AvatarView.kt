package com.yf.viewpractice.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yf.viewpractice.R
import com.yf.viewpractice.debugLog
import com.yf.viewpractice.utils.BitmapUtil

class AvatarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val PADDING = 100f
    val WIDTH = 300f
    val EDGE_WIDTH = 10f

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    val saveArea = RectF()

    var mBitmap: Bitmap = BitmapUtil.getBitmap(resources, R.drawable.avatar, WIDTH.toInt())

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        saveArea.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint)
        val saved = canvas.saveLayer(saveArea, paint)
        canvas.drawOval(
            PADDING + EDGE_WIDTH,
            PADDING + EDGE_WIDTH,
            PADDING + WIDTH - EDGE_WIDTH,
            PADDING + WIDTH - EDGE_WIDTH,
            paint
        )
        paint.xfermode = xfermode
        canvas.drawBitmap(mBitmap, PADDING, PADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(saved)
    }
}