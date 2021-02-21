package com.yf.viewpractice

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.*
import java.nio.ByteBuffer

class Bean(val name: String)

class Server : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        val iBinder = A()
        return iBinder
    }

    /**
     * 这个就是Binder对象，在服务端实际做事情的
     */
    inner class A : IRemoteService.Stub() {

        override fun sendMsg(bean: Bean): String {
            return "service replay, name is ${bean.name}"
        }

        override fun sendBitmap(bean: Bitmap): String {
            return ""
        }

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
//            val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            Log.d("syf", "start service pid:${Process.myPid()}")
            when (code) {
                TRANSACTION_FUN_sendMsg -> {
                    // 校验
                    data.enforceInterface(DESCRIPTOR)
                    val fd = data.readFileDescriptor()
                    FileInputStream(fd.fileDescriptor).use {
                        val bytes = it.readBytes()
                        val str = String(bytes)
                        Log.d("hahaha", "接收：$str size:${bytes.size}")
                    }
                    val result = "result"
                    reply?.writeString(result)
                    reply?.writeNoException()
                }

                TRANSACTION_FUN_sendBitmap -> {
                    // 校验
                    data.enforceInterface(DESCRIPTOR)
                    val fd = data.readFileDescriptor()
                    FileInputStream(fd.fileDescriptor).use {
                        val bytes = it.readBytes()
                        Log.d("hahaha", "接收Bitmap： size:${bytes.size}")

                        BufferedOutputStream(FileOutputStream(File(cacheDir.absolutePath + "/a.png"))).use { bos ->
                            val ips = ByteArrayInputStream(bytes)
                            val buff = ByteArray(1024)
                            var len = 0
                            while (ips.read(buff).also({ len = it }) != -1) {
                                bos.write(buff, 0, len)
                            }
                            ips.close()
                            it.close()
                        }
                    }
                    val result = "result"
                    reply?.writeString(result)
                    reply?.writeNoException()
                }
            }
            Log.d("syf", "end service pid:${Process.myPid()}")
//            return super.onTransact(code, data, reply, flags)
            return true
        }
    }
}

class Client : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Runtime.getRuntime().exec("pm clear context?.packageName")

        return View(inflater.context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("syf", "bindService")
        activity?.bindService(Intent(activity, Server::class.java), object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d("syf", "onServiceDisconnected")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                Log.d("syf", "onServiceConnected")
                val remoteService = IRemoteService.Stub.asInterface(service)
//                remoteService.sendMsg(Bean("jack"))
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
                remoteService.sendBitmap(bitmap)
            }
        }, Context.BIND_AUTO_CREATE)
    }
}

const val DESCRIPTOR = "special"

/**
 * client端与server端的调用契约，实现这个接口，就代表远程server对象具有什么能力
 * 因为IInterface接口的asBinder方法的实现可以将Binder本地对象或代理对象进行返回
 */
interface IRemoteService : IInterface {

    fun sendMsg(bean: Bean): String
    fun sendBitmap(bean: Bitmap): String

    /**
     * Stub 是用来被访问的
     */
    abstract class Stub : Binder(), IRemoteService {

        companion object {
            const val TRANSACTION_FUN_sendMsg = IBinder.FIRST_CALL_TRANSACTION + 0
            const val TRANSACTION_FUN_sendBitmap = IBinder.FIRST_CALL_TRANSACTION + 1

            /**
             * 给client使用，将服务端返回的IBinder转为RemoteService
             */
            fun asInterface(obj: IBinder): IRemoteService {
                // 先查询 本地是否能查到（是否处同一进程）
                val iin = obj.queryLocalInterface(DESCRIPTOR)
                return if (iin != null && iin is IRemoteService) {
                    iin
                } else {
                    // 本地没有，造一个proxy，用于通信
                    Proxy(obj)
                }
            }
        }

        init {
            // 注册了个服务，key就是描述符
            attachInterface(this, DESCRIPTOR)
        }

        override fun asBinder(): IBinder {
            return this
        }

        /**
         * 给其他客户端使用的，对方会拿到此对象
         */
        private class Proxy(private val mRemote: IBinder) : IRemoteService {

            override fun sendBitmap(bitmap: Bitmap): String {
                val data = Parcel.obtain()
                val replay = Parcel.obtain()
                val result: String?
                // 标识写进去
                try {
                    val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
                    bitmap.copyPixelsToBuffer(byteBuffer)
                    val contentBytes = byteBuffer.array()

                    val mf = MemoryFile("memfile", contentBytes.size);
                    mf.writeBytes(contentBytes, 0, 0, contentBytes.size)
                    val method = MemoryFile::class.java.getDeclaredMethod("getFileDescriptor")
                    val fd = method.invoke(mf) as FileDescriptor

                    data.writeInterfaceToken(DESCRIPTOR)
                    data.writeFileDescriptor(fd)

                    Log.d("hahaha", "发送:bitmap size:${contentBytes.size}")
                    mRemote.transact(TRANSACTION_FUN_sendBitmap, data, replay, 0)
                    result = replay.readString()
                } finally {
                    data.recycle()
                    replay.recycle()
                }
                return result ?: "null"
            }

            override fun sendMsg(bean: Bean): String {
                val data = Parcel.obtain()
                val replay = Parcel.obtain()
                val result: String?
                // 标识写进去
                try {
                    data.writeInterfaceToken(DESCRIPTOR)
//                    data.writeBundle(Bundle().apply {
//                        putString("name", bean.name)
//                    })
                    val str = "偶吼吼吼"
                    val contentBytes = str.toByteArray()
                    val mf = MemoryFile("memfile", contentBytes.size);
                    mf.writeBytes(contentBytes, 0, 0, contentBytes.size)
                    val method = MemoryFile::class.java.getDeclaredMethod("getFileDescriptor")
                    val fd = method.invoke(mf) as FileDescriptor
                    data.writeFileDescriptor(fd)

                    Log.d("hahaha", "发送：$str size:${contentBytes.size}")
                    mRemote.transact(TRANSACTION_FUN_sendMsg, data, replay, 0)
                    result = replay.readString()
                } finally {
                    data.recycle()
                    replay.recycle()
                }
                return result ?: "null"
            }


            override fun asBinder(): IBinder {
                return mRemote
            }

        }
    }
}