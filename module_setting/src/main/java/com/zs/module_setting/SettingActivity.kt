package com.zs.module_setting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.zs.lib_base.base.BaseActivity

class SettingActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_manager)
    }
}