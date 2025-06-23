package com.zs.module_map

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

/**
 * 地图
 */
@Route(path = ARouterHelper.PATH_MAP, description = "地图")
class MapActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_map
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_map)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}