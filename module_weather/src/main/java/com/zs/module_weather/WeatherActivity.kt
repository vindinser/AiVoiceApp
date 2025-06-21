package com.zs.module_weather

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 天气
 */
@Route(path = ARouterHelper.PATH_WEATHER, description = "天气")
class WeatherActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather)
    }
}