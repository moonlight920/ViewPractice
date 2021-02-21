package cn.yfengtech.widgets.demo


import android.app.Activity
import android.app.ActivityManager
import android.app.Service
import android.content.*
import android.os.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.chart.LevelLineChartView

import kotlinx.android.synthetic.main.fragment_cup.*
import kotlinx.android.synthetic.main.fragment_level_chart.*
import java.io.FileDescriptor
import kotlin.random.Random

class LevelLineChartFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_level_chart, container, false)

        val v = view.findViewById<LevelLineChartView>(R.id.lcView)
        v.setCurrentLevel(LevelLineChartView.Level.A, 0.3f)
//        v.setCurrentLevel(LevelLineChartView.Level.FINISH, 0.8f, true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAnim.setOnClickListener {
            val randomLevel = when (Random.nextInt(4)) {
                0 -> LevelLineChartView.Level.ZERO
                1 -> LevelLineChartView.Level.C
                2 -> LevelLineChartView.Level.B
                3 -> LevelLineChartView.Level.A
                4 -> LevelLineChartView.Level.FINISH
                else -> LevelLineChartView.Level.FINISH
            }
            lcView.setCurrentLevel(randomLevel, Random.nextFloat(), true)



            try {
                val cls = javaClass.classLoader?.loadClass("android.os.ServiceManager")
                val method = cls?.getMethod("addService", String::class.java, IBinder::class.java)
                val a = Binder()
                val obj = method?.invoke(null, "hahaha", a)
                print(obj.toString())
            } catch (e: Exception) {
                print(e)
            }

            val boolean =
                activity?.bindService(Intent(activity, B::class.java), object : ServiceConnection {
                    override fun onServiceDisconnected(name: ComponentName?) {
                    }

                    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                        activity?.getSystemService("")
                        print(service)
                    }

                }, Context.BIND_AUTO_CREATE)
            print(boolean)
        }
    }

    class B : Service() {
        override fun onBind(intent: Intent?): IBinder? {
            val binder = Binder()
            return binder
        }
    }

    class A : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            TODO("Not yet implemented")
        }

    }

}
