package com.zs.lib_base.base.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 辅助万能适配器的 ViewHolder
 */
class CommonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    // View 复用
    private var mViews: SparseArray<View?> = SparseArray()

    companion object {
        // 获取 Holder 对象
        fun getViewHolder(parent: ViewGroup, layoutId: Int): CommonViewHolder {
            val itemView: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return CommonViewHolder(itemView)
        }
    }

    fun getView(viewId: Int): View {
        var view: View? = mViews[viewId]
        if(view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view!!
    }

    // 设置文本
    fun setText(viewId: Int, text: String) {
        (getView(viewId) as TextView).text = text
    }

    // 设置图片
    fun setImageResource(viewId: Int, resId: Int) {
        (getView(viewId) as ImageView).setImageResource(resId)
    }
}