package com.yf.viewpractice

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomFlipAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 45f)
        bottomFlipAnimator.duration = 1200

        val flipRotationAnimator = ObjectAnimator.ofFloat(view, "flipRotation", 270f)
        flipRotationAnimator.duration = 1200

        val topFlipAnimator = ObjectAnimator.ofFloat(view, "topFlip", -45f)
        topFlipAnimator.duration = 1200

        var animatorSet = AnimatorSet()
        animatorSet.playSequentially(bottomFlipAnimator,flipRotationAnimator,topFlipAnimator)
        animatorSet.start()
        animatorSet.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val bottomFlipRestoreAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 0f)
                val topFlipRestoreAnimator = ObjectAnimator.ofFloat(view, "topFlip", 0f)

                var restoreAnimatorSet = AnimatorSet()
                restoreAnimatorSet.playTogether(bottomFlipRestoreAnimator,topFlipRestoreAnimator)
                restoreAnimatorSet.duration = 1200
                restoreAnimatorSet.start()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })


    }
}

val DEBUG = true
internal inline fun debugLog(value: String) {
    if (DEBUG) {
        Log.d("view_practice", value)
    }
}