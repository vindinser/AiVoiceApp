package com.zs.module_setting

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 设置
 */
@Route(path = ARouterHelper.PATH_SETTING, description = "设置")
class SettingActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_system_setting)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}