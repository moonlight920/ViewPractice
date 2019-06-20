package com.yf.viewpractice.view07

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yf.viewpractice.R

class CircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val avatarWidth = 400f
    val paddingLeft = 100f
    var paddingTop = 100f

    private val camera = Camera()

    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var flipRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {

        camera.setLocation(0f, 0f, -6f)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)


        // 上半部分
        canvas.save()
        canvas.translate(paddingLeft + avatarWidth / 2, paddingTop + avatarWidth / 2)
//        camera.applyToCanvas(canvas)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-avatarWidth.toInt(), -avatarWidth.toInt(), avatarWidth.toInt(), 0)
        canvas.rotate(flipRotation)
        canvas.translate(-(paddingLeft + avatarWidth / 2), -(paddingTop + avatarWidth / 2))
        canvas.drawBitmap(getBitmap(avatarWidth.toInt()), paddingLeft, paddingTop, paint)
        canvas.restore()

        // 下半部分
        canvas.save()
        canvas.translate(paddingLeft + avatarWidth / 2, paddingTop + avatarWidth / 2)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-avatarWidth.toInt(), 0, avatarWidth.toInt(), avatarWidth.toInt())
        canvas.rotate(flipRotation)
        canvas.translate(-(paddingLeft + avatarWidth / 2), -(paddingTop + avatarWidth / 2))
        canvas.drawBitmap(getBitmap(avatarWidth.toInt()), paddingLeft, paddingTop, paint)
        canvas.restore()

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