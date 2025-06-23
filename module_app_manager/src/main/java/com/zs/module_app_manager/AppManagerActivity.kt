package com.zs.module_app_manager

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 应用管理
 */
@Route(path = ARouterHelper.PATH_APP_MANAGER, description = "应用管理页面")
class AppManagerActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_app_manager
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_app_manager)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}
