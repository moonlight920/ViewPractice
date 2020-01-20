package cn.yfengtech.widgets.practice.event

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import cn.yfengtech.widgets.debugLog


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