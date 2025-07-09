package com.zs.lib_network.impl

import com.zs.lib_network.bean.JokeListData
import com.zs.lib_network.bean.JokeOneData
import com.zs.lib_network.http.HttpUrl
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
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

    @GET(HttpUrl.WEATHER_ACTION)
    fun getWeather(@Query("city") city: String, @Query("key") key: String): Call<ResponseBody>
}