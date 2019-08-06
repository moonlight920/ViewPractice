package com.yf.viewpractice.view.event

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.yf.viewpractice.debugLog


class TouchEventLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val TAG = "TouchEventLayout"

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        debugLog("$TAG dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        debugLog("$TAG onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        debugLog("$TAG onInterceptTouchEvent")
        return true
    }
}