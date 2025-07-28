package com.zs.module_map

import com.baidu.mapapi.map.MapView
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.PoiResult
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.map.MapManage
import com.zs.lib_base.map.MapManage.OnCodeResultListener
import com.zs.lib_base.utils.L

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
        val mMapView = findViewById<MapView>(R.id.mMapView)
        MapManage.bindMapView(mMapView)

        startLocation()
    }

    // 开启定位
    private fun startLocation() {
        MapManage.setLocationSwitch(true, object : MapManage.OnLocationResultListener {
            override fun result(
                latitude: Double,
                longitude: Double,
                city: String,
                address: String,
                desc: String
            ) {
                L.i("定位成功，地址：${ address }，描述：${ desc }")
                // 设置中心点
                // MapManage.setCenterMap(latitude, longitude)
                //
                // MapManage.searchNearBy(latitude, longitude, "美食")

                // MapManage.startCode(city, address, object : OnCodeResultListener {
                //     override fun result(codeLa: Double, codeLo: Double) {
                //         MapManage.initNaviEngine(this@MapActivity, la, lo, codeLa, codeLo)
                //     }
                // })
            }

            override fun fail() {
                L.i("定位失败")
            }
        })

        // MapManage.poiSearch("美食", object : MapManage.OnPoiResultListener {
        //
        //     override fun result(result: PoiResult) {
        //         L.i("检索成功, ")
        //         // MapManage.setPoiImage(result)
        //     }
        //
        //     override fun fail() {
        //         L.i("检索失败")
        //     }
        // })
    }

    override fun onResume() {
        super.onResume()
        MapManage.onResume()
    }

    override fun onPause() {
        super.onPause()
        MapManage.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        MapManage.onDestroy()
    }
}