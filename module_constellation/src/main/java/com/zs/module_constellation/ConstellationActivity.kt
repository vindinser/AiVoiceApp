package com.zs.module_constellation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.alibaba.android.arouter.facade.annotation.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 星座
 */
@Route(path = ARouterHelper.PATH_CONSTELLATION)
class ConstellationActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_manager)
    }
}