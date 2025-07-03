package com.zs.lib_base.helper

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager

/**
 * 窗口帮助类
 */
object WindowHelper {

    private lateinit var mContext: Context
    private lateinit var vm: WindowManager
    private lateinit var lp: WindowManager.LayoutParams

    // 初始化窗口帮助类
    fun initHelper(mContext: Context) {
        this.mContext = mContext

        // 初始化 WindowManager
        vm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // 创建布局（铺满窗口）
        lp = createLayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    /**
     * 创建布局属性
     * @param {Int} width 窗口的宽
     * @param {Int} height 窗口的高
     */
    fun createLayoutParams(width: Int, height: Int): WindowManager.LayoutParams {
        val lp = WindowManager.LayoutParams()
        lp.apply {
            this.width = width
            this.height = height
            // 居中
            gravity = Gravity.CENTER
            // 透明
            format = PixelFormat.TRANSLUCENT
            // 标志位
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or // 无法触摸
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or // 没有焦点
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or // 常亮
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS // 透明状态
            // 窗口层级（是否被覆盖）
            type = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE
        }

        return lp
    }

    // 获取视图控件
    fun getView(layoutId: Int): View {
        return View.inflate(mContext, layoutId, null)
    }

    // 显示窗口
    fun show(view: View) {
        if(view.parent == null) {
            vm.addView(view, lp)
        }
    }

    // 显示窗口（自定义窗口属性）
    fun show(view: View, lp: WindowManager.LayoutParams) {
        if(view.parent == null) {
            vm.addView(view, lp)
        }
    }

    // 隐藏窗口
    fun hide(view: View) {
        if(view.parent != null) {
            vm.removeView(view)
        }
    }

    // 更新窗口
    fun update(view: View, lp: WindowManager.LayoutParams) {
        vm.updateViewLayout(view, lp)
    }
}