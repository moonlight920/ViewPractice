package com.yf.viewpractice.ui

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yf.viewpractice.R
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        val moveDistance = 1000
        val keyframe1 = Keyframe.ofFloat(0f, 0f)
        val keyframe2 = Keyframe.ofFloat(0.2f, 0.4f * moveDistance)
        val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * moveDistance)
        val keyframe4 = Keyframe.ofFloat(1f, moveDistance.toFloat())

        val propertyValuesHolder =
            PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4)

        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, propertyValuesHolder)
        objectAnimator.duration = 10000
        objectAnimator.start()
    }
}
