package com.zs.lib_base.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.zs.lib_base.R

/**
 * 小白点布局
 */
class PointLayoutView: LinearLayout {

    private val mList = ArrayList<ImageView>()

    constructor(context: Context?): super(context) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet): super(context, attrs) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    // 初始化
    private fun init() {
        // 方向 横向
        orientation = HORIZONTAL
        // 居中
        gravity = Gravity.CENTER
    }

    // 设置页面数量
    fun setPointSize(size: Int) {
        if(size > 0) {
            mList.clear()
        }
        for(i in 0 until size) {
            val iv = ImageView(context)
            addView(iv)
            mList.add(iv)
        }

        // 默认第一页
        setCheck(0)
    }

    // 设置选中
    fun setCheck(index: Int) {
        if(index > mList.size) {
            return
        }

        mList.forEachIndexed { i, imageView ->
            imageView.setImageResource(
                if(i == index) R.drawable.img_app_manager_point_p else
                    R.drawable.img_app_manager_point
            )
        }
    }
}