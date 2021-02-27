package com.yf.viewpractice.server

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.WorkerThread
import cn.yfengtech.server.impl.BaseApiHttpHandler
import cn.yfengtech.server.model.Config
import cn.yfengtech.server.model.config


class DemoApiHttpHandler : BaseApiHttpHandler() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun getConfig(): Config = config {

        /**
         * 配置一功能项
         */
        category {
            id = Config.CategoryId.ID_A
            name = "A功能"
            desc = "A功能的描述"
            inputItem {
                paramKey = Config.CategoryInputKey.Key1
                desc = "请输入xxx格式内容"
                addFileDemo("Demo1", "file/demo1.txt")
                addTextDemo("Demo2", "demo2文本")
            }
        }

        category {
            id = Config.CategoryId.ID_B
            name = "B功能(多参数)"
            desc = "B功能的描述"
            inputItem {
                paramKey = Config.CategoryInputKey.Key1
                desc = "输入参数1："
                addTextDemo("张三", "zhangsan")
            }
            inputItem {
                paramKey = Config.CategoryInputKey.Key2
                desc = "输入参数2"
                addTextDemo("李四", "lisi")
            }
        }
    }

    @WorkerThread
    override fun handleSubmitResult(
        context: Context,
        categoryId: String,
        params: Map<String, String>
    ): Boolean {
        when (categoryId) {
            Config.CategoryId.ID_A -> {
                val key = params[Config.CategoryInputKey.Key1]
                mHandler.post {
                    if (!key.isNullOrBlank()) {
                        Toast.makeText(context, "key1:$key", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            Config.CategoryId.ID_B -> {
                val key1 = params[Config.CategoryInputKey.Key1]
                val key2 = params[Config.CategoryInputKey.Key2]
                mHandler.post {
                    Toast.makeText(
                        context,
                        "key1:${key1 ?: "null"},key2:${key2 ?: "null"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return true
    }

    override fun pullAppData(params: Map<String, String>): String {
        return ""
    }
}