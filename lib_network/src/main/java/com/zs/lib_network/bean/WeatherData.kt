package com.zs.lib_network.bean

/**
 * 天气数据
 */
data class WeatherData(
    val reason: String,
    val result: WeatherResult,
    val error_code: Int
)

data class WeatherResult(
    val city: String,
    val realtime: RealTimeWeather,
    val future: List<FutureWeather>
)

data class RealTimeWeather(
    val temperature: String,
    val humidity: String,
    val info: String,
    val wid: String,
    val direct: String,
    val power: String,
    val aqi: String
)

data class FutureWeather(
    val date: String,
    val temperature: String,
    val weather: String,
    val wid: DailyWid,
    val direct: String
)

data class DailyWid(
    val day: String,
    val night: String
)