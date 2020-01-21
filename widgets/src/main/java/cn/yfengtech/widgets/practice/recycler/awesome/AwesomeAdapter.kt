package cn.yfengtech.widgets.practice.recycler.awesome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.support.v7.widget.RecyclerView
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.debugLog
import com.yf.smarttemplate.data.DataProvider
import cn.yfengtech.widgets.practice.recycler.MyViewHolder


private const val TYPE_NORMAL = 0
private const val TYPE_LONG_IMG = 1
private const val TYPE_LONG_IMG_REVERSED = 2

/**
 * Created by yf.
 * @date 2019-08-18
 */
class AwesomeAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListenerList: ArrayList<MyScrollListener> = arrayListOf()

    val data = DataProvider.getCountryEnNameList()

    override fun getItemViewType(position: Int): Int =
        if (position == 14) TYPE_LONG_IMG
        else if (position == 28) TYPE_LONG_IMG_REVERSED
        else TYPE_NORMAL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_LONG_IMG) {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_recycler_long_img, parent, false)
            return LongImgHolder(view, true)
        } else if (viewType == TYPE_LONG_IMG_REVERSED) {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_recycler_long_img, parent, false)
            return LongImgHolder(view, false)
        } else {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.item_recycler_swipe_drag, parent, false)
            return MyViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder -> {
                holder.tvSummary.text = data[position]
            }
        }
    }

    fun onScroll(height: Int, dx: Int, dy: Int) {
        mListenerList.forEach {
            it.onScroll(height, dx, dy)
        }
    }

    inner class LongImgHolder(view: View, reversed: Boolean) : RecyclerView.ViewHolder(view) {
        val longImgView = view.findViewById<ImageView>(R.id.longImgView)
        val scrollView = view.findViewById<ScrollView>(R.id.scrollView)

        init {

            if(reversed){
                longImgView.setImageResource(R.mipmap.img_awesome_4)
            }else{
                longImgView.setImageResource(R.mipmap.img_awesome_3)
            }

            register(object : MyScrollListener {
                override fun onScroll(height: Int, dx: Int, dy: Int) {
                    debugLog("height $height  view top ${itemView.top}  view bottom ${itemView.bottom}  itemView.height ${itemView.height}  awesomeImgView height ${longImgView.height}")

                    val topRange = 0..(height - itemView.height)
                    val scrollYRange = 0..(longImgView.height - itemView.height)
                    val scale = scrollYRange.last.toFloat() / topRange.last

                    if (reversed) {
                        when {
                            itemView.top < topRange.first -> {
                                scrollView.scrollTo(0, scrollYRange.last)
                            }
                            itemView.top in topRange -> {
                                scrollView.scrollTo(
                                    0,
                                    ((topRange.last - itemView.top) * scale).toInt()
                                )
                            }
                            itemView.top > topRange.last -> {
                                scrollView.scrollTo(0, scrollYRange.first)
                            }
                        }
                    } else {
                        debugLog("scale $scale  scrollTo ${ (itemView.top * scale).toInt()}")
                        scrollView.scrollTo(0, (itemView.top * scale).toInt())
                    }
                }
            })
        }
    }


    fun register(listener: MyScrollListener) {
        mListenerList.add(listener)
    }

    interface MyScrollListener {
        fun onScroll(height: Int, dx: Int, dy: Int)
    }
}