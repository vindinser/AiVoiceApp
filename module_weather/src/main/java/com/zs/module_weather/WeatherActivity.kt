package com.zs.module_weather

import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.utils.L
import com.zs.lib_network.HttpManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 天气
 */
@Route(path = ARouterHelper.PATH_WEATHER, description = "天气")
class WeatherActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_weather
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_weather)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        textWeather()
    }


    // 测试使用Retrofit请求天气
    private fun textWeather() {
        HttpManager.queryWeather("北京").enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // 请求成功
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                L.e("天气请求失败")
            }
        })
    }
}