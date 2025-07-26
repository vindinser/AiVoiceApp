package com.zs.lib_base.map

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MapView
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener
import com.baidu.mapapi.search.poi.PoiCitySearchOption
import com.baidu.mapapi.search.poi.PoiDetailResult
import com.baidu.mapapi.search.poi.PoiDetailSearchResult
import com.baidu.mapapi.search.poi.PoiIndoorResult
import com.baidu.mapapi.search.poi.PoiNearbySearchOption
import com.baidu.mapapi.search.poi.PoiResult
import com.baidu.mapapi.search.poi.PoiSearch


/**
 * 百度地图管理类
 */
object MapManage {

    const val TAG = "MapManage"

    // 最大缩放 4-21
    private const val MAX_ZOOM = 17f

    @SuppressLint("StaticFieldLeak")
    private var mMapView: MapView? = null

    private var mBaiduMap: BaiduMap? = null

    // 用户的城市
    private var locationCity: String? = ""

    // 定位客户端
    @SuppressLint("StaticFieldLeak")
    private lateinit var mLocationClicent: LocationClient

    // 定位对外的回调
    private var mOnLocationResultListener: OnLocationResultListener? = null

    private var mPoiSearch: PoiSearch? = null

    // Poi对外的回调
    private var mOnPoiResultListener: OnPoiResultListener? = null

    // 初始化
    fun init(mContext: Context) {
        // 在初始化地图前添加隐私协议确认
        SDKInitializer.setAgreePrivacy(mContext, true)
        SDKInitializer.initialize(mContext)

        LocationClient.setAgreePrivacy(true)
        mLocationClicent = LocationClient(mContext)

        // 初始化定位
        initLocation()

        initPoi()
    }

    // 绑定 地图 mapview
    fun bindMapView(mMapView: MapView) {
        this.mMapView = mMapView
        mBaiduMap = mMapView.map

        // 默认缩放
        zoomMap(MAX_ZOOM)
    }

    // 缩放地图
    fun zoomMap(value: Float) {
        val builder = MapStatus.Builder()
        builder.zoom(value)
        mBaiduMap?.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
    }

    // 设置默认中心点
    fun setCenterMap(la: Double, lo: Double) {
        val latLng = LatLng(la, lo)
        val newLatLng = MapStatusUpdateFactory.newLatLng(latLng)
        mBaiduMap?.setMapStatus(newLatLng)
    }

    /**
     * 设置地图类型
     * MAP_TYPE_NORMAL	普通地图（包含3D地图）
     * MAP_TYPE_SATELLITE	卫星图
     * MAP_TYPE_NONE	空白地图
     */
    fun setMapType(index: Int) {
        val type = when (index) {
            1 -> BaiduMap.MAP_TYPE_NORMAL
            2 -> BaiduMap.MAP_TYPE_SATELLITE
            else -> BaiduMap.MAP_TYPE_NONE
        }
        mBaiduMap?.setMapType(type);
    }

    // 设置实时路况
    fun setTrafficEnabled(isOpen: Boolean) {
        mBaiduMap?.isTrafficEnabled = isOpen
    }

    // 设置热力图
    fun setBaiduHeatMapEnabled(isOpen: Boolean) {
        mBaiduMap?.isBaiduHeatMapEnabled = isOpen
    }

    // 设置定位开关
    fun setMyLocationEnabled(isOpen: Boolean) {
        mBaiduMap?.isMyLocationEnabled = isOpen
    }

    // 设置定位
    fun setLocationSwitch(isOpen: Boolean, mOnLocationResultListener: OnLocationResultListener?) {
        if(isOpen) {
            mLocationClicent.start()
            this.mOnLocationResultListener = mOnLocationResultListener
        } else {
            mLocationClicent.stop()
        }
    }

    // poi 覆盖物
    fun setPoiImage(poiResult: PoiResult) {
        mBaiduMap?.clear()

        // 创建PoiOverlay对象
        val poiOverlay = PoiOverlay(mBaiduMap)

        // 设置Poi检索数据
        poiOverlay.setData(poiResult)

        // 将poiOverlay添加至地图并缩放至合适级别
        poiOverlay.addToMap()
        poiOverlay.zoomToSpan()
    }

    // 初始化定位
    private fun initLocation() {
        val option = LocationClientOption()
        // 打开GPS
        option.isOpenGnss = true
        // 设置坐标类型 - baidu
        option.setCoorType("bd09ll")
        // 间隔时间
        option.setScanSpan(1000)
        // 高精度
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        // 是否需要地址信息
        option.setIsNeedAddress(true)
        // 地址描述
        option.setIsNeedLocationDescribe(true)
        // 设备方向结果
        option.setNeedDeviceDirect(true)
        // GPS有效时按照一秒一次频率输出GPS结果
        option.isLocationNotify = true
        // 再stop时杀掉这个进程
        option.setIgnoreKillProcess(true)
        // POI结果
        option.setIsNeedLocationPoiList(true)
        // 不收集CRASH信息
        option.SetIgnoreCacheException(false)

        mLocationClicent.locOption = option

        // 设置监听
        mLocationClicent.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation?) {
                if (location == null || mMapView == null) {
                    return
                }

                // val locData = MyLocationData.Builder()
                //     .accuracy(location.radius) // 此处设置开发者获取到的方向信息，顺时针0-360
                //     .direction(location.direction).latitude(location.latitude)
                //     .longitude(location.longitude).build()
                // mBaiduMap?.setMyLocationData(locData)

                Log.e(TAG, "location：${ location }")

                if(location.locType == 61 || location.locType == 161) {
                    locationCity = location.city
                    mOnLocationResultListener?.result(
                        location.latitude,
                        location.longitude,
                        location.city,
                        location.addrStr,
                        location.locationDescribe
                    )
                } else {
                    mOnLocationResultListener?.fail()
                }
                // 停止定位
                setLocationSwitch(false, null)
            }
        })
    }

    fun onResume() {
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView?.onResume()
    }

    fun onPause() {
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView?.onPause()
    }

    fun onDestroy() {
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView?.onDestroy()
        mMapView = null

        mLocationClicent.stop()
        mBaiduMap?.isMyLocationEnabled = false

        mPoiSearch?.destroy()
    }

    // 初始化POI（地点检索）
    private fun initPoi() {
        mPoiSearch = PoiSearch.newInstance()
        mPoiSearch?.setOnGetPoiSearchResultListener(object : OnGetPoiSearchResultListener {
            override fun onGetPoiResult(result: PoiResult?) {
                result?.let {
                    if(it.error == SearchResult.ERRORNO.NO_ERROR) {
                        mOnPoiResultListener?.result(it)
                        // 再地图上绘制覆盖点
                        setPoiImage(it)
                    }
                }
            }

            override fun onGetPoiDetailResult(p0: PoiDetailResult?) {
                // 废弃
            }

            override fun onGetPoiDetailResult(result: PoiDetailSearchResult?) {

            }

            override fun onGetPoiIndoorResult(result: PoiIndoorResult?) {

            }
        })
    }

    private fun poiSearchInCity(keyword: String, city: String) {
        mPoiSearch?.searchInCity(
            PoiCitySearchOption()
                .city(city)
                .keyword(keyword)
                .pageNum(0)
        )
    }

    fun poiSearch(keyword: String, mOnPoiResultListener: OnPoiResultListener) {
        this.mOnPoiResultListener = mOnPoiResultListener
        var searchCity = locationCity;
        if(TextUtils.isEmpty(locationCity)) {
            setLocationSwitch(true, object : OnLocationResultListener {
                override fun result(
                    latitude: Double,
                    longitude: Double,
                    city: String,
                    address: String,
                    desc: String
                ) {
                    searchCity = city
                }

                override fun fail() {
                    searchCity = "北京"
                }
            })
        }

        poiSearchInCity(keyword, searchCity!!)
    }

    fun poiSearch(keyword: String, city: String?, mOnPoiResultListener: OnPoiResultListener) {
        this.mOnPoiResultListener = mOnPoiResultListener
        poiSearchInCity(keyword, if(TextUtils.isEmpty(city) || city == "") "北京" else city!!)
    }

    // 搜索周边 以天安门(39.915446, 116.403869)为中心，搜索半径100米以内的餐厅
    fun searchNearBy(la: Double, lo: Double, keyword: String) {
        mPoiSearch?.searchNearby(
            PoiNearbySearchOption()
            .location(LatLng(la, lo))
            .radius(500)
            //支持多个关键字并集检索，不同关键字间以$符号分隔，最多支持10个关键字检索。如:”银行$酒店”
            .keyword(keyword)
            .pageNum(0))
    }

    interface OnLocationResultListener {

        fun result(latitude: Double, longitude: Double, city: String, address: String, desc: String)

        fun fail()
    }

    interface OnPoiResultListener {

        fun result(result: PoiResult)

        fun fail()
    }
}