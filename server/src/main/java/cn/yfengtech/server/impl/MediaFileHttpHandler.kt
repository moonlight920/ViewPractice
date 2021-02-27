package cn.yfengtech.server.impl

import android.content.Context
import android.webkit.MimeTypeMap
import cn.yfengtech.server.HttpHandler
import cn.yfengtech.server.NanoHTTPD
import java.io.File

internal class MediaFileHttpHandler : HttpHandler {

    override fun handle(context: Context, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
        val file = File(context.cacheDir, session.uri)
        var response: NanoHTTPD.Response? = null
        if (session.method == NanoHTTPD.Method.GET && file.exists()) {
            val extension = MimeTypeMap.getFileExtensionFromUrl(session.uri)
            val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

            response = if (type != null)
                NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.OK,
                    type,
                    file.readText()
                )
            else
                NanoHTTPD.newFixedLengthResponse(file.readText())
        }
        return response
    }
}