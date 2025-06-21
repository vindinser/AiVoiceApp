package com.zs.module_joke

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 笑话
 */
@Route(path = ARouterHelper.PATH_JOKE, description = "笑话")
class JokeActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_joke)
    }
}