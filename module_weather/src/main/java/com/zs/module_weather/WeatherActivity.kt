package com.zs.module_weather

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 天气
 */
@Route(path = ARouterHelper.PATH_WEATHER, description = "天气")
class WeatherActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_weather
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_weather)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}