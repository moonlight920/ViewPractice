package com.yf.viewpractice.measure

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.forEach
import com.yf.viewpractice.debugLog
import java.util.jar.Attributes

class MyLinearLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayoutCompat(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        forEach { view ->
            val lp = view.layoutParams
            when (lp.width) {
                ViewGroup.LayoutParams.MATCH_PARENT -> {

                }
                else -> {
                    MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY)
                }
            }
        }

        debugLog("MyLinearLayout onMeasure")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        debugLog("MyLinearLayout onLayout")
    }


}