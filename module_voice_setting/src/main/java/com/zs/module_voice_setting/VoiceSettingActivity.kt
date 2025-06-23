package com.zs.module_voice_setting

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 声音设置
 */
@Route(path = ARouterHelper.PATH_VOICE_SETTING, description = "声音设置")
class VoiceSettingActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_setting_voice
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_voice_setting)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}