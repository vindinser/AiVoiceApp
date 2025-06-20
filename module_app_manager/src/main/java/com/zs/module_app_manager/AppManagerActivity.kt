package com.zs.module_app_manager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.alibaba.android.arouter.facade.annotation.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 应用管理
 */
@Route(path = ARouterHelper.PATH_APP_MANAGER)
class AppManagerActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_manager)
    }
}
