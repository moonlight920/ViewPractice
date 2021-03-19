package cn.yfengtech.server.plugin

abstract class VueComponentPlugin : Plugin {

    final override fun type(): Plugin.Type = Plugin.Type.VUE_COMPONENT

    final override fun config(): Any = vueComponentConfig()

    abstract fun vueComponentConfig(): VueComponentConfig

    class VueComponentConfig {
        var desc: String = ""
        var componentName: String = ""
        var componentPath: String = ""

        companion object {
            fun create(config: VueComponentConfig.() -> Unit): VueComponentConfig {
                return VueComponentConfig().apply(config)
            }
        }
    }
}