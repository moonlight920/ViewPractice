package cn.yfengtech.server.plugin

import android.content.Context
import cn.yfengtech.server.NanoHTTPD
import cn.yfengtech.server.model.Response

abstract class PullPlugin : Plugin {

    /**
     * 进行配置，用于web端向app取数据
     */
    abstract fun getPullConfig(): PullPluginConfig

    /**
     * 通过key，从app获取数据
     */
    abstract fun fetchDataByKey(context: Context, key: String): String?


    final override fun config(): Any = getPullConfig()
    final override fun type(): Plugin.Type = Plugin.Type.PULL

    final override fun handle(
        context: Context,
        session: NanoHTTPD.IHTTPSession,
        bodyParams: Map<String, String>
    ): Response? {
        if (session.method == NanoHTTPD.Method.GET && session.uri == "/api/pull") {
            if (bodyParams["pid"] == pid()) {
                val key = bodyParams["key"] ?: ""
                val result = fetchDataByKey(context, key)
                if (result != null) {
                    return Response.success(result)
                }
            }
        }
        return null
    }


    class PullPluginConfig {

        var desc: String = ""

        private val pullList = mutableListOf<PullOption>()

        fun pullOption(item: PullOption.() -> Unit) {
            pullList.add(PullOption().apply(item))
        }

        class PullOption {
            /**
             * 该输入项提交时的参数key，从form表单中根据这个参数获取对应的数据
             */
            var key: String? = null

            /**
             * 对该输入项的一个描述
             */
            var name: String? = null
        }

        companion object {
            fun create(config: PullPluginConfig.() -> Unit): PullPluginConfig {
                return PullPluginConfig().apply(config)
            }
        }
    }

}