package cn.yfengtech.server

import android.content.Context
import cn.yfengtech.server.impl.IndexHttpHandler
import cn.yfengtech.server.impl.MediaFileHttpHandler
import fi.iki.elonen.NanoHTTPD

class SimpleServer(private val applicationContext: Context) : NanoHTTPD(8080) {

    private val handlerList = mutableListOf(
        IndexHttpHandler(),
        MediaFileHttpHandler()
    )

    init {
        start(SOCKET_READ_TIMEOUT, true)
        Util.assetsToCache(applicationContext)
    }

    override fun serve(session: IHTTPSession): Response? {
        handlerList.forEach {
            val response = it.handle(applicationContext, session)
            if (response != null) return response
        }
        var msg = "<html><body><h1>Hello server</h1>\n"
        val parms = session.parms
        msg += if (parms["username"] == null) {
            """
<form action='?' method='get'>
    <p>Your name: <input type='text' name='username'></p>
</form>
"""
        } else {
            "<p>Hello, " + parms["username"] + "!</p>"
        }
        val aa = "<p>uri:${session.uri},method:${session.method.name}</p>"
        return newFixedLengthResponse("$msg $aa</body></html>\n")
    }
}