package com.yf.viewpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.yfengtech.server.SimpleServer
import cn.yfengtech.server.WebServerFragment
import cn.yfengtech.widgets.demo.*
import com.yf.smarttemplate.SmartTemplate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SmartTemplate.apply(this) {
            fragmentItem(WebServerFragment::class.java) {
                title = "测试web服务"
                desc = "访问8080端口"
            }
            itemList {
                title = "widgets集合"
                desc = "演示一些自定义控件"

                itemList {
                    title = "view练习"
                    fragmentItem(TagLayoutFragment::class.java) {
                        title = "TagLayout"
                        desc = "动态流式排列，自动折行"
                    }
                    fragmentItem(ExpressageFragment::class.java) {
                        title = "快递流程图"
                    }
                    fragmentItem(CupFragment::class.java) {
                        title = "杯子冒泡"
                    }
                    fragmentItem(NestedViewGroupFragment::class.java) {
                        title = "自定义嵌套滑动ViewGroup"
                    }
                    fragmentItem(TriangleLoadingFragment::class.java) {
                        title = "搜狐视频loading动画"
                    }
                    fragmentItem(TaiJiFragment::class.java) {
                        title = "太极"
                    }
                    fragmentItem(PaintAttrFragment::class.java) {
                        title = "Paint属性演示"
                    }
                    fragmentItem(CurveChartFragment::class.java) {
                        title = "折线图"
                        desc = "动态描述走势的图标"
                    }
                    fragmentItem(LevelLineChartFragment::class.java) {
                        title = "层级 折线图"
                        desc = "定制化 层级折线图，带动画"
                    }
                }
                fragmentItem(AwesomeRecyclerViewFragment::class.java) {
                    title = "列表中显示长图"
                    desc = "类似猫眼中 列表里显示长图广告"
                }
                fragmentItem(RecyclerViewFragment::class.java) {
                    title = "可左右滑的列表控件"
                    desc = "左滑删除等功能"
                }
            }
        }

//        supportFragmentManager.beginTransaction()
//            .replace(android.R.id.content, Client())
//            .commit()
    }
}