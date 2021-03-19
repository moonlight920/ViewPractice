package cn.yfengtech.server.config

interface PluginConfig {

    fun type():Type

    fun config():Any

    enum class Type(val value:Int){
        PUSH(1),
        PULL(2),
        CUSTOM(3),
    }
}