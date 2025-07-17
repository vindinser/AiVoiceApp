package com.zs.module_weather.tools

import com.zs.module_weather.R

/**
 *
 */
object WeatherIconTools {

    /**
     * 天气状态获取图片
     * @param {string} wid 天气状态
     */
    fun getIcon(wid: String): Int {
        return when(wid) {
            // 晴
            "00" -> R.drawable.wid_00
            // 多云
            "01" -> R.drawable.wid_01
            // 阴
            "02" -> R.drawable.wid_02
            // 阵雨
            "03" -> R.drawable.wid_03
            // 雷阵雨
            "04" -> R.drawable.wid_04
            // 雷阵雨伴有冰雹
            "05" -> R.drawable.wid_05
            // 雨夹雪
            "06" -> R.drawable.wid_06
            // 小雨
            "07" -> R.drawable.wid_07
            // 中雨
            "08" -> R.drawable.wid_08
            // 大雨
            "09" -> R.drawable.wid_09
            // 暴雨
            "10" -> R.drawable.wid_10
            // 大暴雨
            "11" -> R.drawable.wid_11
            // 特大暴雨
            "12" -> R.drawable.wid_12
            // 阵雪
            "13" -> R.drawable.wid_13
            // 小雪
            "14" -> R.drawable.wid_14
            // 中雪
            "15" -> R.drawable.wid_15
            // 大雪
            "16" -> R.drawable.wid_16
            // 暴雪
            "17" -> R.drawable.wid_17
            // 雾
            "18" -> R.drawable.wid_18
            // 冻雨
            "19" -> R.drawable.wid_19
            // 沙尘暴
            "20" -> R.drawable.wid_20
            // 小到中雨
            "21" -> R.drawable.wid_21
            // 中到大雨
            "22" -> R.drawable.wid_22
            // 大到暴雨
            "23" -> R.drawable.wid_23
            // 暴雨到大暴雨
            "24" -> R.drawable.wid_24
            // 大暴雨到特大暴雨
            "25" -> R.drawable.wid_25
            // 小到中雪
            "26" -> R.drawable.wid_26
            // 中到大雪
            "27" -> R.drawable.wid_27
            // 大到暴雪
            "28" -> R.drawable.wid_28
            // 浮尘
            "29" -> R.drawable.wid_29
            // 扬沙
            "30" -> R.drawable.wid_30
            // 强沙尘暴
            "31" -> R.drawable.wid_31
            // 霾
            "53" -> R.drawable.wid_53
            else -> R.drawable.wid_01
        }
    }
}