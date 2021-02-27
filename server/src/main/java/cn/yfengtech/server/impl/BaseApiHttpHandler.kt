package cn.yfengtech.server.impl

import android.content.Context
import androidx.annotation.WorkerThread
import cn.yfengtech.server.HttpHandler
import cn.yfengtech.server.NanoHTTPD
import cn.yfengtech.server.model.Config
import com.google.gson.Gson

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
    abstract fun handleSubmitResult(context: Context, categoryId: String, params: Map<String, String>): Boolean

    /**
     * web端从app中拉取数据时调用
     */
    @WorkerThread
    abstract fun pullAppData(params: Map<String, String>): String

    private val mGson = Gson()

    override fun handle(context: Context, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {

        if (session.method == NanoHTTPD.Method.GET && session.uri == "/api/config") {
            // 下发配置信息
            val jsonText = mGson.toJson(getConfig())
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, jsonText)
        }

        if (session.method == NanoHTTPD.Method.POST) {
            var params = mapOf<String, String>()
            try {
                val files = hashMapOf<String, String>()
                session.parseBody(files)
                if (!session.parms.isNullOrEmpty()) {
                    params = session.parms
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (session.uri == "/api/submit") {
                // 交给子类来处理
                val isSuccess = handleSubmitResult(context, params["categoryId"] ?: "", params)
                if (isSuccess) {
                    return NanoHTTPD.newFixedLengthResponse("App端处理成功")
                }
            }

            if (session.uri == "/api/pull") {
                val result = pullAppData(params)
                return NanoHTTPD.newFixedLengthResponse(result)
            }

        }
        return null
    }
}