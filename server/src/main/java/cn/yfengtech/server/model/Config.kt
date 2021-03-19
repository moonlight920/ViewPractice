package cn.yfengtech.server.model

/**
 * 初始化配置，会在pc端显示出来
 */
class Config {

    private val categoryList = mutableListOf<Category>()
    private val pullFuncList = mutableListOf<PullFunc>()

    /**
     * 创建一个分类
     */
    fun category(category: Category.() -> Unit) {
        val c = Category().apply(category)
        if (c.id == null) throw IllegalArgumentException("category id can't is null")
        if (c.name == null) throw IllegalArgumentException("category name can't is null")
        categoryList.add(c)
    }

    fun pullData(pullFunc: PullFunc.() -> Unit) {
        val p = PullFunc().apply(pullFunc)
        if (p.id == null) throw IllegalArgumentException("pullFunc id can't is null")
        if (p.name == null) throw IllegalArgumentException("pullFunc name can't is null")
        pullFuncList.add(p)
    }

    class PullFunc {
        var id: String? = null
        var name: String? = null
        var desc: String? = null
        private val pullList = mutableListOf<PullOption>()

        fun pullOption(item: PullOption.() -> Unit) {
            pullList.add(PullOption().apply(item))
        }

        class PullOption {
            /**
             * 该输入项提交时的参数key，从form表单中根据这个参数获取对应的数据
             */
            var paramKey: String? = null

            /**
             * 对该输入项的一个描述
             */
            var name: String? = null
        }
    }

    class Category {
        /**
         * 功能id，用于在接收参数后判断操作行为
         * 参考[CategoryId]
         */
        var id: String? = null

        /**
         * 功能名称，显示在web页的导航栏
         */
        var name: String? = null

        /**
         * 对该功能的一个基本描述
         */
        var desc: String? = null

        /**
         * 参数输入框，可以支持输入多个参数，不同的参数需要使用不一样的key
         * 可参考[CategoryInputKey]
         */
        private val inputList = mutableListOf<InputItem>()

        /**
         * 创建分类中的子项
         */
        fun inputItem(item: InputItem.() -> Unit) {
            inputList.add(InputItem().apply(item))
        }

        /**
         * 映射到web端的一个输入项
         */
        class InputItem {
            /**
             * 该输入项提交时的参数key，从form表单中根据这个参数获取对应的数据
             */
            var paramKey: String? = null

            /**
             * 对该输入项的一个描述
             */
            var desc: String? = null

            /**
             * 可以提供一些demo，作为该输入项的模板
             */
            private val demos = mutableListOf<Demo>()

            fun addFileDemo(name: String, filePath: String) {
                demos.add(Demo(1, name, filePath))
            }

            fun addTextDemo(name: String, text: String) {
                demos.add(Demo(2, name, text))
            }
        }

        /**
         * 可选的demo数据
         * 如果是file，data是个path
         * 如果是text，data就是文本
         */
        class Demo(val type: Int, val name: String, val data: String) {
        }
    }

    object CategoryId {
        const val ID_A = "ID_A"
        const val ID_B = "ID_B"
    }

    object CategoryInputKey {
        const val Key1 = "key1"
        const val Key2 = "key2"
        const val Key3 = "key3"
    }
}

fun config(config: Config.() -> Unit): Config {
    return Config().apply(config)
}