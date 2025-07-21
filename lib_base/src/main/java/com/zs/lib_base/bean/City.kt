package com.zs.lib_base.bean

data class City(
    val error_code: Int,
    val reason: String,
    val result: List<CityResult>
)

data class CityResult(
    val city: String,
    val cityCode: String,
    val district: String,
    val province: String,
    val provinceCode: String,
    val id: String
)