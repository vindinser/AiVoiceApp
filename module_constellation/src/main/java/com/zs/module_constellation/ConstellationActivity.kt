package com.zs.module_constellation

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 星座
 */
@Route(path = ARouterHelper.PATH_CONSTELLATION, description = "星座")
class ConstellationActivity: BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_constellation
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_constellation)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}