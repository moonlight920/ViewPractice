package cn.yfengtech.server.plugin

import android.content.Context
import androidx.annotation.WorkerThread
import cn.yfengtech.server.NanoHTTPD
import cn.yfengtech.server.model.Response

/**
 * 将debug工具在web端抽象成插件化，每个功能项作为插件增加，随时插拔，以便配置和扩展
 *
 * @author songyifeng 2021-03-08 17:54:33
 */
interface Plugin {

    /**
     * 插件唯一id，用于配对的request和response
     */
    fun pid(): String

    /**
     * 在web端展示的功能名称
     */
    fun name(): String

    /**
     * 功能类型，当前提供功能有
     *  1. 从app端获取数据
     *  2. 将数据推送到app端
     *  3. 其他自定义插件
     */
    fun type(): Type

    /**
     * 插件的配置项，用于推送到web端渲染页面，参考[PullPlugin.PullPluginConfig]等
     */
    fun config(): Any

    enum class Type(val value: Int) {
        PUSH(1),
        PULL(2),
        VUE_COMPONENT(3),
        CUSTOM(4),
    }

    /**
     * web端操作后，请求到app上，此方法用于响应请求逻辑
     *
     * tips:此方法会在子线程回调
     */
    @WorkerThread
    fun handle(
        context: Context,
        session: NanoHTTPD.IHTTPSession,
        bodyParams: Map<String, String>
    ): Response?

}