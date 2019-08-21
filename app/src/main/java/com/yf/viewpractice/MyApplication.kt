package com.yf.viewpractice

import android.app.Application
import com.yf.smarttemplate.SmartTemplate
import com.yf.viewpractice.customview.cup.CupFragment
import com.yf.viewpractice.customview.expressage.ExpressageFragment
import com.yf.viewpractice.measure.MeasureFragment
import com.yf.viewpractice.customview.taglayout.TagLayoutFragment
import com.yf.viewpractice.anim.AnimFragment
import com.yf.viewpractice.anim.AnimationActivity
import com.yf.viewpractice.customview.clock.ClockFragment
import com.yf.viewpractice.customview.souhu.loading.TriangleLoadingFragment
import com.yf.viewpractice.recycler.RecyclerViewFragment
import com.yf.viewpractice.ui.NestedFragment
import com.yf.viewpractice.ui.PaintAttrFragment
import com.yf.viewpractice.ui.TaiJiFragment
import com.yf.viewpractice.ui.TouchEventFragment

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SmartTemplate.init(this) {
            fragmentItem(RecyclerViewFragment::class.java) {
                title = "列表控件"
            }
            itemList {
                title = "自定义View"
                fragmentItem(PaintAttrFragment::class.java) {
                    title = "Paint属性演示"
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
                fragmentItem(TriangleLoadingFragment::class.java) {
                    title = "搜狐视频Loading"
                }
                fragmentItem(ClockFragment::class.java) {
                    title = "炫酷时间锁屏"
                }
            }
            itemList {
                title = "动画"
                fragmentItem(AnimFragment::class.java) {
                    title = "动画1"
                }
                activityItem(AnimationActivity::class.java) {
                    title = "动画2"
                }
            }
            fragmentItem(NestedFragment::class.java) {
                title = "嵌套滑动"
            }
            fragmentItem(TouchEventFragment::class.java) {
                title = "事件分发"
            }
        }
    }
}