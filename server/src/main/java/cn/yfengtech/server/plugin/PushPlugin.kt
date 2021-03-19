package cn.yfengtech.server.plugin

import android.content.Context
import cn.yfengtech.server.NanoHTTPD
import cn.yfengtech.server.model.Response

abstract class PushPlugin : Plugin {

    /**
     * 进行配置，用于web端向app里推数据
     */
    abstract fun getPushConfig(): PushPluginConfig

    /**
     * 处理web端发来的数据
     */
    abstract fun handleResult(context: Context, params: Map<String, String>): Response


    final override fun config(): Any = getPushConfig()
    final override fun type(): Plugin.Type = Plugin.Type.PUSH
    final override fun handle(
        context: Context,
        session: NanoHTTPD.IHTTPSession,
        bodyParams: Map<String, String>
    ): Response? {
        if (session.method == NanoHTTPD.Method.POST && session.uri == "/api/submit") {
            if (bodyParams["pid"] == pid()) {
                return handleResult(context, bodyParams)
            }
        }
        return null
    }


    class PushPluginConfig {

        var desc: String = ""
        private val inputList = mutableListOf<InputItem>()

        /**
         * 创建分类中的子项
         */
        fun inputItem(item: InputItem.() -> Unit) {
            inputList.add(InputItem().apply(item))
        }

        /**
         * 映射到web端的一个输入项
         */
        class InputItem {
            /**
             * 该输入项提交时的参数key，从form表单中根据这个参数获取对应的数据
             */
            var key: String? = null

            /**
             * 对该输入项的一个描述
             */
            var desc: String? = null

            /**
             * 可以提供一些demo，作为该输入项的模板
             */
            private val demos = mutableListOf<Demo>()

            fun addFileDemo(name: String, filePath: String) {
                demos.add(Demo(1, name, filePath))
            }

            fun addTextDemo(name: String, text: String) {
                demos.add(Demo(2, name, text))
            }

            /**
             * 可选的demo数据
             * 如果是file，data是个path
             * 如果是text，data就是文本
             */
            class Demo(val type: Int, val name: String, val data: String)
        }

        companion object {
            fun create(config: PushPluginConfig.() -> Unit): PushPluginConfig {
                return PushPluginConfig().apply(config)
            }
        }
    }

}