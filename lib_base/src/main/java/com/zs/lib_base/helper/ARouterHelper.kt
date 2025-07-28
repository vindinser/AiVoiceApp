package com.zs.lib_base.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cn.therouter.BuildConfig
import com.therouter.TheRouter

/**
 * 路由帮助类
 */
object ARouterHelper {

    // Module 第一次启动 Path
    const val PATH_APP_MANAGER = "/app_manager/app_manager_activity"
    const val PATH_CONSTELLATION = "/constellation/constellation_activity"
    const val PATH_DEVELOPER = "/developer/developer_activity"
    const val PATH_JOKE = "/joke/joke_activity"
    const val PATH_MAP = "/map/map_activity"
    const val PATH_MAP_NAVI = "/map/map_navi_activity"
    const val PATH_SETTING = "/setting/setting_activity"
    const val PATH_VOICE_SETTING = "/voice_setting/voice_setting_activity"
    const val PATH_WEATHER = "/weather/weather_activity"

    // 初始化
    fun initHelper(application: Application) {
        TheRouter.init(application, BuildConfig.DEBUG)
    }

    // 跳转页面
    fun startActivity(path: String) {
        // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
        TheRouter.build(path).navigation();
    }

    // 跳转页面
    fun startActivity(activity: Activity, path: String, requestCode: Int) {
        // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
        TheRouter.build(path).navigation(activity, requestCode);
    }

    // 跳转页面并携带参数（String）
    fun startActivity(path: String, key: String, value: String) {
        TheRouter.build(path)
            .withString(key, value)
            .navigation()
    }

    // 跳转页面并携带参数（Int）
    fun startActivity(path: String, key: String, value: Int) {
        TheRouter.build(path)
            .withInt(key, value)
            .navigation()
    }

    // 跳转页面并携带参数（Boolean）
    fun startActivity(path: String, key: String, value: Boolean) {
        TheRouter.build(path)
            .withBoolean(key, value)
            .navigation()
    }

    // 跳转页面并携带参数（Bundle）
    fun startActivity(path: String, key: String, bundle: Bundle) {
        TheRouter.build(path)
            .withBundle(key, bundle)
            .navigation()
    }

    // 跳转页面并携带参数（Object）
    fun startActivity(path: String, key: String, any: Any) {
        TheRouter.build(path)
            .withObject(key, any)
            .navigation()
    }
}