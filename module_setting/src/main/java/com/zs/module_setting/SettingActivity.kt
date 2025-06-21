package com.zs.module_setting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 设置
 */
@Route(path = ARouterHelper.PATH_SETTING, description = "设置")
class SettingActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
    }
}