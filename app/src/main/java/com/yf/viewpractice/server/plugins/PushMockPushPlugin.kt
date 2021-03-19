package com.yf.viewpractice.server.plugins

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import cn.yfengtech.server.model.Config
import cn.yfengtech.server.model.Response
import cn.yfengtech.server.plugin.PushPlugin

class PushMockPushPlugin : PushPlugin() {

    override fun pid(): String = "PushMockPushPlugin"

    private val mHandler by lazy { Handler(Looper.getMainLooper()) }

    override fun name(): String = "发push"

    override fun getPushConfig(): PushPluginConfig {
        return PushPluginConfig.create {
            desc = "哈哈哈"
            inputItem {
                key = Config.CategoryInputKey.Key1
                desc = "请输入xxx格式内容"
                addFileDemo("Demo1", "file/demo1.txt")
                addTextDemo("Demo2", "demo2文本")
            }
        }
    }

    override fun handleResult(context: Context, params: Map<String, String>): Response {
        val key = params[Config.CategoryInputKey.Key1]
        mHandler.post {
            if (!key.isNullOrBlank()) {
                Toast.makeText(context, "key1:$key", Toast.LENGTH_SHORT).show()
            }
        }
        return Response.success()
    }

}