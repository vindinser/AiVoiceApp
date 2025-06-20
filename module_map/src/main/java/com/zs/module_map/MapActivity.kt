package com.zs.module_map

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.alibaba.android.arouter.facade.annotation.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 地图
 */
@Route(path = ARouterHelper.PATH_MAP)
class MapActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_manager)
    }
}