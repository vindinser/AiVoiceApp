package com.zs.module_developer

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 开发者模式
 */
@Route(path = ARouterHelper.PATH_DEVELOPER, description = "开发者模式")
class DeveloperActivity: BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_developer
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_developer)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}