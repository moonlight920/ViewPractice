package com.yf.viewpractice.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.yf.viewpractice.R

class CustomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.save()
        canvas.translate(100f, 100f)
        canvas.rotate(45f, 100f, 100f)
        canvas.drawBitmap(getBitmap(200), 0f, 0f, paint)
        canvas.restore()

        canvas.rotate(45f, 100f, 100f)
        canvas.translate(150f, 0f)
        canvas.drawBitmap(getBitmap(200), 0f, 0f, paint)
    }

    fun getBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)

    }
}