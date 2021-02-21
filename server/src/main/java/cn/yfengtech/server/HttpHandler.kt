package cn.yfengtech.server

import android.content.Context
import fi.iki.elonen.NanoHTTPD

internal interface HttpHandler {
    fun handle(context: Context, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response?
}