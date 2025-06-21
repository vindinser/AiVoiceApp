package com.zs.module_developer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 开发者模式
 */
@Route(path = ARouterHelper.PATH_DEVELOPER, description = "开发者模式")
class DeveloperActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_developer)
    }
}