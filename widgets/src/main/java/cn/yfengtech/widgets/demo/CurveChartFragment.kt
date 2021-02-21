package cn.yfengtech.widgets.demo


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.chart.CurveChartView
import com.yf.smarttemplate.SmartTemplate

/**
 * A simple [Fragment] subclass.
 */
class CurveChartFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_curve_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        SmartTemplate.generateSampleData(context!!, recyclerView)
    }
}
class MemoryView(context: Context, attrs: AttributeSet? = null) :
    CurveChartView(context, attrs) {
    private val DURATION: Long = 800
    private var action = object : Runnable {
        override fun run() {
            val dalvikHeapMem = getApplicationDalvikHeapMem()
            addData(dalvikHeapMem.allocated * 1f / 1024)
            postDelayed(this, DURATION)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (View.VISIBLE == visibility) {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (View.VISIBLE == visibility) {
            start()
        } else {
            stop()
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus && View.VISIBLE == visibility) {
            start()
        } else if (!hasWindowFocus) {
            stop()
        }
    }

    fun start() {
        removeCallbacks(action)
        post(action)
    }

    fun stop() {
        removeCallbacks(action)
    }

    /**
     * 获取应用dalvik内存信息
     * @return dalvik堆内存KB
     */
    fun getApplicationDalvikHeapMem(): DalvikHeapMem {
        val runtime = Runtime.getRuntime()
        val dalvikHeapMem = DalvikHeapMem()
        dalvikHeapMem.freeMem = runtime.freeMemory() / 1024
        dalvikHeapMem.maxMem = Runtime.getRuntime().maxMemory() / 1024
        dalvikHeapMem.allocated =
            (Runtime.getRuntime().totalMemory() - runtime.freeMemory()) / 1024
        return dalvikHeapMem
    }

    /**
     * Dalvik堆内存，只要App用到的内存都算（包括共享内存）
     */
    class DalvikHeapMem {
        var freeMem: Long = 0
        var maxMem: Long = 0
        var allocated: Long = 0
    }
}
