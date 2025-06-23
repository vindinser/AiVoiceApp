package com.zs.lib_base.base

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity

abstract class BaseActivity: ComponentActivity() {

    // 获取布局ID
    abstract fun getLayoutId(): Int

    // 获取标题
    abstract fun getTitleText(): String

    // 是否显示返回键
    abstract fun isShowBack(): Boolean

    // 初始化
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(getLayoutId())

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar?.let {
                // 标题
                it.title = getTitleText()
                // 是否显示返回键
                it.setDisplayHomeAsUpEnabled(isShowBack())  // 显示返回箭头
                it.setDisplayShowHomeEnabled(isShowBack())   // 显示可点击的 Home 按钮
                // 取消logo展示
                it.setIcon(null)
                // 透明度
                it.elevation = 0f
            }
        }

        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}