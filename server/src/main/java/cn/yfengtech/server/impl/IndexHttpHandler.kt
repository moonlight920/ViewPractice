package cn.yfengtech.server.impl

import android.content.Context
import cn.yfengtech.server.HttpHandler
import cn.yfengtech.server.NanoHTTPD
import java.io.File

internal class IndexHttpHandler : HttpHandler {
    override fun handle(context: Context, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
        var response: NanoHTTPD.Response? = null
        if (session.method == NanoHTTPD.Method.GET && session.uri == "/") {
            val file = File(context.cacheDir, "index.html")
            response = NanoHTTPD.newFixedLengthResponse(file.readText())
        }
        return response
    }
}