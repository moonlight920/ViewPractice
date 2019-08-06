package com.yf.viewpractice.view.event

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.yf.viewpractice.debugLog


class TouchEventView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val TAG = "TouchEventView"

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        debugLog("$TAG dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        debugLog("$TAG onTouchEvent")
        return true
    }
}