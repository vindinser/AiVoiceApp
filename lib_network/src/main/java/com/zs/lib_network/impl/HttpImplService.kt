package com.zs.lib_network.impl

import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.JokeListData
import com.zs.lib_network.bean.JokeOneData
import com.zs.lib_network.bean.MonthData
import com.zs.lib_network.bean.RobotData
import com.zs.lib_network.bean.TodayData
import com.zs.lib_network.bean.WeatherData
import com.zs.lib_network.bean.WeekData
import com.zs.lib_network.bean.YearData
import com.zs.lib_network.http.HttpUrl
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 接口服务
 */
interface HttpImplService {

    /**
     * 笑话
     */
    @GET(HttpUrl.JOKE_ONE_AUCTION)
    fun queryJoke(@Query("key") key: String): Call<JokeOneData>
    @GET(HttpUrl.JOKE_LIST_AUCTION)
    fun queryJokeList(
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int,
        @Query("key") key: String
    ): Call<JokeListData>

    /**
     * 星座
     */
    @GET(HttpUrl.CONSTELLATION_AUCTION)
    fun queryTodayConsTellInfo(
        @Query("consName") consName: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): Call<TodayData>

    @GET(HttpUrl.CONSTELLATION_AUCTION)
    fun queryTomorrowConsTellInfo(
        @Query("consName") consName: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): Call<TodayData>

    @GET(HttpUrl.CONSTELLATION_AUCTION)
    fun queryWeekConsTellInfo(
        @Query("consName") consName: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): Call<WeekData>

    @GET(HttpUrl.CONSTELLATION_AUCTION)
    fun queryMonthConsTellInfo(
        @Query("consName") consName: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): Call<MonthData>

    @GET(HttpUrl.CONSTELLATION_AUCTION)
    fun queryYearConsTellInfo(
        @Query("consName") consName: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): Call<YearData>

    /**
     * 机器人
     */
    @Headers(HttpManager.JSON)
    @POST(HttpUrl.ROBOT_AUCTION)
    fun aiRobot(@Body requestBody: RequestBody) : Call<RobotData>

    /**
     * 天气
     */
    @GET(HttpUrl.WEATHER_ACTION)
    fun getWeather(@Query("city") city: String, @Query("key") key: String): Call<WeatherData>
}