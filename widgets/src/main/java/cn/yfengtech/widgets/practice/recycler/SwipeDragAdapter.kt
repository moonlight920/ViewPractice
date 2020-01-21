package cn.yfengtech.widgets.practice.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import cn.yfengtech.widgets.R
import cn.yfengtech.widgets.debugLog
import com.yf.smarttemplate.data.DataProvider

/**
 * Created by yf.
 * @date 2019-08-18
 */
class SwipeDragAdapter(private val context: Context) : RecyclerView.Adapter<MyViewHolder>() {

    val data = DataProvider.getCountryEnNameList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_swipe_drag, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvSummary.text = data[position]
    }

    class SwipeController : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            debugLog("onMove")
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            debugLog("onSwiped ${(viewHolder as MyViewHolder).tvSummary.text}")
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            val action = when(actionState){
                ItemTouchHelper.ACTION_STATE_IDLE -> "IDLE"
                ItemTouchHelper.ACTION_STATE_DRAG -> "DRAG"
                ItemTouchHelper.ACTION_STATE_SWIPE -> "SWIPE"
                else -> "NULL"
            }
            debugLog("onSelectedChanged ${(viewHolder as? MyViewHolder)?.tvSummary?.text ?: "null"}  actionState: $action")
        }
    }

}