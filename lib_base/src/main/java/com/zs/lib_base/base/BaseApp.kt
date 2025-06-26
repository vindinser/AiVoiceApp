package com.zs.lib_base.base

import android.app.Application
import android.content.Intent
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.helper.NotificationHelper
import com.zs.lib_base.service.InitService

/**
 * 基类App
 */
open class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化路由
        ARouterHelper.initHelper(this)

        // 初始化语音服务
        NotificationHelper.initHelper(this)

        // 初始化IntentService
        startService(Intent(this, InitService::class.java))

    }
}