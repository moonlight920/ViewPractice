package cn.yfengtech.server

import android.content.Context

interface HttpHandler {
    fun handle(context: Context, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response?
}