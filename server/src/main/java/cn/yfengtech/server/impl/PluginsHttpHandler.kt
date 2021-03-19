package cn.yfengtech.server.impl

import android.content.Context
import com.google.gson.Gson
import cn.yfengtech.server.HttpHandler
import cn.yfengtech.server.NanoHTTPD
import cn.yfengtech.server.getPostBodyMap
import cn.yfengtech.server.plugin.Plugin

class PluginsHttpHandler(var htmlTitle:String = "Debug Tools Server") : HttpHandler {

    private val mGson = Gson()

    private val mPluginList = mutableListOf<Plugin>()

    fun loadPlugin(plugin: Plugin) {
        if (mPluginList.find { it.pid() == plugin.pid() } == null) {
            // 增加插件
            mPluginList.add(plugin)
        }
    }

    private fun generateConfigJson(): String {
        return mGson.toJson(mapOf(
            "title" to htmlTitle,
            "plugins" to mPluginList.map {
                mapOf(
                    "pid" to it.pid(),
                    "name" to it.name(),
                    "type" to it.type().value,
                    "config" to it.config()
                )
            }
        ))
    }


    override fun handle(context: Context, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
        if (session.method == NanoHTTPD.Method.GET && session.uri == "/api/config") {
            // 下发配置信息
            return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.OK,
                NanoHTTPD.MIME_PLAINTEXT,
                generateConfigJson()
            )
        }
        val bodyParams = session.getPostBodyMap()

        mPluginList.forEach {
            val response = it.handle(context, session, bodyParams)
            if (response != null) {
                return NanoHTTPD.newJson(mGson.toJson(response))
            }
        }
        return null
    }
}