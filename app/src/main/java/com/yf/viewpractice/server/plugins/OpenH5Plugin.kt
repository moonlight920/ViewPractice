package com.yf.viewpractice.server.plugins

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Handler
import android.os.Looper
import cn.yfengtech.server.model.Response
import cn.yfengtech.server.plugin.PushPlugin


class OpenH5Plugin : PushPlugin() {

    override fun pid(): String = "OpenH5Plugin"

    private val mHandler by lazy { Handler(Looper.getMainLooper()) }

    override fun name(): String = "打开H5"

    override fun getPushConfig(): PushPluginConfig {
        return PushPluginConfig.create {
            desc = "打开h5"
            inputItem {
                key = "url"
                desc = "请输入一个http或https地址"
                addTextDemo("百度", "https://www.baidu.com")
            }
        }
    }

    override fun handleResult(context: Context, params: Map<String, String>): Response {
        val url = params["url"]
        if (url.isNullOrBlank()) {
            return Response.failure("url为空")
        }
        mHandler.post {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            })
        }
        return Response.success()
    }

}