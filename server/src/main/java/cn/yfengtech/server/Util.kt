package cn.yfengtech.server

import android.content.Context
import android.content.res.AssetManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

internal object Util {
    fun assetsToCache(context: Context) {
        val assets: AssetManager = context.assets
        val cacheDir: File = context.cacheDir
        try {
            val basePath = "server/"
            traverseAssetFileList(assets, basePath, basePath, cacheDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun traverseAssetFileList(
        assets: AssetManager,
        basePath: String,
        parentPath: String,
        destFile: File
    ) {
        val httpServerFileList = assets.list(parentPath)
        if (null != httpServerFileList) {
            for (filePath in httpServerFileList) {
                val relativePath = parentPath.substring(basePath.length)
                val newPath =
                    if (basePath == parentPath) filePath else "$relativePath/$filePath"
                if (!newPath.contains(".")) {
                    traverseAssetFileList(assets, basePath, basePath + newPath, destFile)
                } else {
                    val file = File(destFile, newPath)
                    val parentFile = file.parentFile!!
                    if (!parentFile.exists()) {
                        parentFile.mkdirs()
                    }
                    // 不判断文件是否存在，防止缓存
                    copyFileFromAsset(assets, basePath, newPath, file)
                }
            }
        }
    }

    private fun copyFileFromAsset(
        assets: AssetManager, basePath: String,
        assetFileName: String, destFile: File
    ) {
        try {
            assets.open(basePath + assetFileName).use { inputStream ->
                FileOutputStream(destFile).use { fileOutputStream ->
                    val bytes = ByteArray(inputStream.available())
                    inputStream.read(bytes)
                    fileOutputStream.write(bytes)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}