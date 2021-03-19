package cn.yfengtech.server.impl

import android.content.Context
import androidx.annotation.WorkerThread
import cn.yfengtech.server.HttpHandler
import cn.yfengtech.server.model.Config
import com.google.gson.Gson
import cn.yfengtech.server.NanoHTTPD
import cn.yfengtech.server.getPostBodyMap
import cn.yfengtech.server.model.Response

abstract class BaseApiHttpHandler : HttpHandler {

    /**
     * 获取web所需的配置信息
     */
    abstract fun getConfig(): Config

    /**
     * 该函数处理web端发来的请求
     * @param categoryId 功能id
     * @param params form表达中携带的参数
     *
     * 子线程回调，注意切换线程操作
     */
    @WorkerThread
    abstract fun handleSubmitResult(
        context: Context,
        categoryId: String,
        params: Map<String, String>
    ): Response

    /**
     * web端从app中拉取数据时调用
     */
    @WorkerThread
    abstract fun pullAppData(
        context: Context,
        pullFuncId: String,
        params: Map<String, String>
    ): Response

    open fun handleCustom(context: Context, session: NanoHTTPD.IHTTPSession): Response? = null

    private val mGson = Gson()

    override fun handle(context: Context, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {

        if (session.method == NanoHTTPD.Method.GET && session.uri == "/api/config") {
            // 下发配置信息
            val jsonText = mGson.toJson(getConfig())
            return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.OK,
                NanoHTTPD.MIME_PLAINTEXT,
                jsonText
            )
        }

        if (session.method == NanoHTTPD.Method.POST) {
            val params = session.getPostBodyMap()

            if (session.uri == "/api/submit") {
                // 交给子类来处理
                val response = handleSubmitResult(context, params["categoryId"] ?: "", params)
                return NanoHTTPD.newJson(mGson.toJson(response))
            }

            if (session.uri == "/api/pull") {
                val result = pullAppData(context, params["pullFuncId"] ?: "", params)
                return NanoHTTPD.newJson(mGson.toJson(result))
            }
        }

        // 处理 自定义接口
        val response = handleCustom(context, session)
        if (response != null) {
            return NanoHTTPD.newJson(mGson.toJson(response))
        }

        return null
    }
}