package com.yf.viewpractice.view07

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yf.viewpractice.R

class CameraView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val camera = Camera()

    init {
        camera.rotateX(30f)
        camera.setLocation(0f,0f,-8f)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.save()
        canvas.translate(200f,200f)
        camera.applyToCanvas(canvas)
        canvas.translate(-200f,-200f)


        canvas.drawBitmap(getBitmap(200), 100f, 100f, paint)
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