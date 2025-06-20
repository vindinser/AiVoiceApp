package com.zs.module_voice_setting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.alibaba.android.arouter.facade.annotation.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 声音设置
 */
@Route(path = ARouterHelper.PATH_VOICE_SETTING)
class VoiceSettingActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_manager)
    }
}