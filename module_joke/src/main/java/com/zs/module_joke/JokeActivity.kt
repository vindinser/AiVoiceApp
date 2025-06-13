package com.zs.module_joke

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.zs.lib_base.base.BaseActivity

class JokeActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_manager)
    }
}