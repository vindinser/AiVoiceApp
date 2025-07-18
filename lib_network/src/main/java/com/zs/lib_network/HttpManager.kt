package com.zs.lib_network

import com.zs.lib_network.bean.JokeListData
import com.zs.lib_network.bean.JokeOneData
import com.zs.lib_network.bean.MonthData
import com.zs.lib_network.bean.RobotData
import com.zs.lib_network.bean.TodayData
import com.zs.lib_network.bean.WeatherData
import com.zs.lib_network.bean.WeekData
import com.zs.lib_network.bean.YearData
import com.zs.lib_network.http.HttpKey
import com.zs.lib_network.http.HttpUrl
import com.zs.lib_network.impl.HttpImplService
import com.zs.lib_network.interceptor.HttpInterceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 对外的网络管理类
 */
object HttpManager {

    private const val PAGE_SIZE = 20
    const val JSON = "content-type: application/json;charset=UTF-8"

    // 拦截器
    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpInterceptor()).build()
    }

    /**
     * =========================== 笑话相关 ===========================
     */
    // 天气对象
    private val retrofitJoke by lazy {
        Retrofit.Builder()
            .client(getClient())
            .baseUrl(HttpUrl.JOKE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 笑话接口对象
    private val apiJoke by lazy {
        retrofitJoke.create(HttpImplService::class.java)
    }

    // 查询笑话
    fun queryJoke(callback: Callback<JokeOneData>) {
        apiJoke.queryJoke(HttpKey.JOKE_KEY).enqueue(callback)
    }

    // 查询笑话列表
    fun queryJokeList(page: Int, callback: Callback<JokeListData>) {
        apiJoke.queryJokeList(page, PAGE_SIZE, HttpKey.JOKE_KEY).enqueue(callback)
    }

    /**
     * =========================== 星座相关 ===========================
     */
    // 星座对象
    private val retrofitConsTell by lazy {
        Retrofit.Builder()
            .client(getClient())
            .baseUrl(HttpUrl.CONSTELLATION_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 星座接口对象
    private val apiConsTell by lazy {
        retrofitConsTell.create(HttpImplService::class.java)
    }

    // 查询星座信息 - 今天/明天
    fun queryTodayConsTell(name: String, callback: Callback<TodayData>) {
        apiConsTell.queryTodayConsTellInfo(name, "today", HttpKey.CONSTELLATION_KEY).enqueue(callback)
    }

    // 查询星座信息 - 今天/明天
    fun queryTomorrowConsTell(name: String, callback: Callback<TodayData>) {
        apiConsTell.queryTomorrowConsTellInfo(name, "tomorrow", HttpKey.CONSTELLATION_KEY).enqueue(callback)
    }

    // 查询星座信息 - 本周
    fun queryWeekConsTell(name: String, callback: Callback<WeekData>) {
        apiConsTell.queryWeekConsTellInfo(name, "week", HttpKey.CONSTELLATION_KEY).enqueue(callback)
    }

    // 查询星座信息 - 本月
    fun queryMonthConsTell(name: String, callback: Callback<MonthData>) {
        apiConsTell.queryMonthConsTellInfo(name, "month", HttpKey.CONSTELLATION_KEY).enqueue(callback)
    }

    // 查询星座信息 - 本年
    fun queryYearConsTell(name: String, callback: Callback<YearData>) {
        apiConsTell.queryYearConsTellInfo(name, "year", HttpKey.CONSTELLATION_KEY).enqueue(callback)
    }

    /**
     * =========================== 机器人相关 ===========================
     */
    // 机器人对象
    private val retrofitAiRobot by lazy {
        Retrofit.Builder()
            .client(getClient())
            .baseUrl(HttpUrl.ROBOT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 机器人接口对象
    private val apiRobot by lazy {
        retrofitAiRobot.create(HttpImplService::class.java)
    }

    // 机器人对话
    fun aiRobotChat(text: String, callback: Callback<RobotData>) {
        val jsonObject = JSONObject()
        jsonObject.put("reqType", 0)

        val perception = JSONObject()

        val inputText = JSONObject()
        inputText.put("text", text)

        perception.put("inputText", inputText)

        jsonObject.put("perception", perception)

        val userInfo = JSONObject()
        userInfo.put("apiKey", HttpKey.ROBOT_KEY)
        userInfo.put("userId", "AIVOICE")

        jsonObject.put("userInfo", userInfo)

        val body = RequestBody.create(JSON.toMediaTypeOrNull(), jsonObject.toString())

        apiRobot.aiRobot(body).enqueue(callback)
    }


    /**
     * =========================== 天气相关 ===========================
     */
    // 天气对象
    private val retrofitWeather by lazy {
        Retrofit.Builder()
            .client(getClient())
            .baseUrl(HttpUrl.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 天气接口对象
    private val apiWeather by lazy {
        retrofitWeather.create(HttpImplService::class.java)
    }

    // 根据城市查询天气
    fun queryWeather(city: String, callback: Callback<WeatherData>) {
        return apiWeather.getWeather(city, HttpKey.WEATHER_KEY).enqueue(callback)
    }
}