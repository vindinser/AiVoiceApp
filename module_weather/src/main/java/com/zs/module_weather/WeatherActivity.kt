package com.zs.module_weather

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.zs.lib_base.base.BaseActivity

class WeatherActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_manager)
    }
}