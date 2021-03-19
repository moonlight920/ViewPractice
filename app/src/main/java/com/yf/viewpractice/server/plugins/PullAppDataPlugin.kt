package com.yf.viewpractice.server.plugins

import android.content.Context
import cn.yfengtech.server.plugin.PullPlugin

class PullAppDataPlugin : PullPlugin() {

    override fun pid(): String = "PullAppDataPlugin"

    override fun name(): String = "拉App数据"

    override fun getPullConfig(): PullPluginConfig {
        return PullPluginConfig.create {
            desc = "哈哈哈"
            pullOption {
                key = "key1"
                name = "获取xx信息"
            }
            pullOption {
                key = "key2"
                name = "获取yy信息"
            }
        }
    }

    override fun fetchDataByKey(context: Context, key: String): String? {
        return when (key) {
            "key1" -> "我是从app端的xx"
            "key2" -> "我是从app端的yy"
            else -> null
        }
    }
}