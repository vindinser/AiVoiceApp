package com.zs.lib_base.base

import android.app.Application
import com.zs.lib_base.helper.ARouterHelper

/**
 * 基类App
 */
class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        ARouterHelper.initHelper(this)
    }
}