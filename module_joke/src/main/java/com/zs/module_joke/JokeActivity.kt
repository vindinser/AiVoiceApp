package com.zs.module_joke

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 笑话
 */
@Route(path = ARouterHelper.PATH_JOKE, description = "笑话")
class JokeActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_app_joke
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_joke)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}