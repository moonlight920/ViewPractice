package cn.yfengtech.server

import android.content.Context
import cn.yfengtech.server.impl.IndexHttpHandler
import cn.yfengtech.server.impl.MediaFileHttpHandler
import cn.yfengtech.server.impl.PluginsHttpHandler
import cn.yfengtech.server.plugin.Plugin
import java.io.File
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

class SimpleServer @JvmOverloads constructor(
    private val applicationContext: Context,
    port: Int = 9000
) : NanoHTTPD(port) {

    private val mPluginHttpHandler = PluginsHttpHandler()

    private val handlerList = mutableListOf(
        // 首页
        IndexHttpHandler(),
        // 其他媒体文件
        MediaFileHttpHandler(),
        mPluginHttpHandler
    )

    init {
        Util.assetsToCache(applicationContext)
    }

    fun setWebTitle(title: String) {
        mPluginHttpHandler.htmlTitle = title
    }

    fun loadPlugin(plugin: Plugin) {
        mPluginHttpHandler.loadPlugin(plugin)
    }

    override fun serve(session: IHTTPSession): Response? {
        handlerList.forEach {
            val response = it.handle(applicationContext, session)
            if (response != null) return response
        }
        val file = File(applicationContext.cacheDir, "/404.html")
        // 返回404
        return newFixedLengthResponse(file.readText())
    }

    fun getHostAddress(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf: NetworkInterface = en.nextElement()
                val enumIpAddress: Enumeration<InetAddress> = intf.inetAddresses
                while (enumIpAddress.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddress.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return null
    }
}