package com.zs.module_voice_setting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 声音设置
 */
@Route(path = ARouterHelper.PATH_VOICE_SETTING, description = "声音设置")
class VoiceSettingActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting_voice)
    }
}