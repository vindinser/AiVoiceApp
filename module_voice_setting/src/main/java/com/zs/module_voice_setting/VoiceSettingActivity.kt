package com.zs.module_voice_setting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.zs.lib_base.base.BaseActivity

class VoiceSettingActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_manager)
    }
}