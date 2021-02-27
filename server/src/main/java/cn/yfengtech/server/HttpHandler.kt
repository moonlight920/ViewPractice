package cn.yfengtech.server

import android.content.Context
import cn.yfengtech.server.NanoHTTPD

interface HttpHandler {
    fun handle(context: Context, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response?
}