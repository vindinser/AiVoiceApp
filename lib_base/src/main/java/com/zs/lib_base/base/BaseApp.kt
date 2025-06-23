package com.zs.lib_base.base

import android.app.Application
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.utils.SpUtils

/**
 * 基类App
 */
class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化路由
        ARouterHelper.initHelper(this)

        // 初始化SP
        SpUtils.initUtils(this)
    }
}