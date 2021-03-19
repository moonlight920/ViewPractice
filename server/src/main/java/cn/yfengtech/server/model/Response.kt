package cn.yfengtech.server.model

class Response private constructor(private var isSuccessful: Boolean) {
    private var error: String? = null
    private var data: Any? = null

    companion object {
        fun success(data: Any? = null) = Response(true).apply {
            this.data = data
        }

        fun failure(error: String) = Response(false).apply {
            this.error = error
        }
    }
}