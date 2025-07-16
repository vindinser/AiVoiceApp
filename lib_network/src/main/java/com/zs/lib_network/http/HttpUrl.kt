package com.zs.lib_network.http

/**
 * 请求网络地址
 */
object HttpUrl {

    /**
     * 笑话
     */
    // baseurl
    const val JOKE_BASE_URL = "http://v.juhe.cn/"
    // 随机笑话
    const val JOKE_ONE_AUCTION = "joke/randJoke"
    // 笑话列表
    const val JOKE_LIST_AUCTION = "joke/content/text"

    /**
     * 星座
     */
    // baseurl
    const val CONSTELLATION_BASE_URL = "http://web.juhe.cn/"
    // 查询星座运势
    const val CONSTELLATION_AUCTION = "constellation/getAll"

    /**
     * 机器人
     */
    // baseurl
    const val ROBOT_BASE_URL = "http://openapi.turingapi.com/"
    // 机器人对话
    const val ROBOT_AUCTION = "openapi/api/v2"

    // 天气
    const val WEATHER_BASE_URL = "http://apis.juhe.cn/"
    const val WEATHER_ACTION = "simpleWeather/query"
}