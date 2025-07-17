package com.zs.module_weather

import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.utils.L
import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.WeatherData
import com.zs.module_weather.tools.WeatherIconTools
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
        intent.run {
            val city = getStringExtra("city")
            if(!TextUtils.isEmpty(city))  {
                loadWeather(city!!)
            } else {
                loadWeather("北京")
            }
        }
    }


    /**
     * 加载城市天气数据
     * @param { string } city 加载天气城市name
     */
    private fun loadWeather(city: String) {

        // 设置标题
        supportActionBar?.title = city

        HttpManager.run {
            queryWeather(city, object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            // 填充数据
                            it.result.realtime.apply {
                                // 设置天气
                                val mInfo = findViewById<TextView>(R.id.mInfo).apply {
                                    text = info
                                }
                                // 设置图片
                                val mIvWid = findViewById<ImageView>(R.id.mIvWid).apply {
                                    setImageResource(WeatherIconTools.getIcon(wid))
                                }
                                // 设置温度
                                findViewById<TextView>(R.id.mTemperature).apply {
                                    text = String.format("%s%s", temperature, getString(R.string.app_weather_t))
                                }
                                // 设置湿度
                                findViewById<TextView>(R.id.mHumidity).apply {
                                    text = String.format("%s\t%s", getString(R.string.app_weather_humidity), humidity)
                                }
                                // 设置风向
                                findViewById<TextView>(R.id.mDirect).apply {
                                    text = String.format("%s\t%s", getString(R.string.app_weather_direct), direct)
                                }
                                // 设置风力
                                findViewById<TextView>(R.id.mSpeed).apply {
                                    text = String.format("%s\t%s", getString(R.string.app_weather_power), power)
                                }
                                // 设置空气质量
                                findViewById<TextView>(R.id.mAirQuality).apply {
                                    text = String.format("%s\t%s", getString(R.string.app_weather_aqi), aqi)
                                }
                            }
                        }
                    } else {
                        queryWeatherError(city)
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    queryWeatherError(city)
                }
            })
        }
    }

    /**
     * 查询天气失败
     * @param { string } city 加载天气城市name
     */
    private fun queryWeatherError(city: String) {
        Toast.makeText(this@WeatherActivity, "${ city }天气请求失败", Toast.LENGTH_LONG).show()
    }
}