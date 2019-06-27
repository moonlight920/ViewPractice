package com.yf.viewpractice

import android.app.Application
import com.yf.smarttemplate.SmartTemplate
import com.yf.viewpractice.cup.CupFragment
import com.yf.viewpractice.expressage.ExpressageFragment
import com.yf.viewpractice.measure.MeasureFragment
import com.yf.viewpractice.taglayout.TagLayoutFragment

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SmartTemplate.init(this) {
            fragmentItem(AnimFragment::class.java) {
                title = "动画1"
            }
            activityItem(AnimationActivity::class.java) {
                title = "动画2"
            }

            fragmentItem(MeasureFragment::class.java) {
                title = "Measure 1"
            }
            fragmentItem(TagLayoutFragment::class.java) {
                title = "TagLayout"
            }
            fragmentItem(TaiJiFragment::class.java) {
                title = "太极"
            }
            fragmentItem(ExpressageFragment::class.java) {
                title = "快递流程图"
            }
            fragmentItem(CupFragment::class.java) {
                title = "杯子冒泡"
            }
        }
    }
}