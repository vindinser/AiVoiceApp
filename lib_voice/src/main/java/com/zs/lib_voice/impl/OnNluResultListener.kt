package com.zs.lib_voice.impl

/**
 * 语义结果
 */
interface OnNluResultListener {

    /**
     * =========================== APP 相关操作 ===========================
     */
    // 打开App
    fun openApp(appName: String)
    // 卸载App
    fun unInstallApp(appName: String)
    // 其他App操作（更新、下载、搜索、推荐）
    fun otherApp(appName: String)

    /**
     * =========================== 通用设置 ===========================
     */
    // 返回
    fun back()
    // 返回主页
    fun home()
    // 音量+
    fun setVolumeUp()
    // 音量-
    fun setVolumeDown()
    // 退出
    fun quit()

    /**
     * =========================== 电话相关 ===========================
     */
    // 拨打电话（通讯录）
    fun callPhoneForName(name: String)
    // 拨打电话（号码）
    fun callPhoneForNumber(number: String)

    /**
     * =========================== 笑话相关 ===========================
     */
    // 播放笑话
    fun tellJoke()
    // 笑话列表
    fun jokeList()

    /**
     * =========================== 星座相关 ===========================
     */
    // 星座时间
    fun consTellTime(name: String)
    // 星座信息
    fun consTellInfo(name: String)

    /**
     * =========================== 机器人 ===========================
     */
    fun aiRobot(text: String)

    /**
     * =========================== 天气相关 ===========================
     */
    // 查询天气
    fun queryWeather(city: String)
    // 查询天气详情
    fun queryWeatherInfo(city: String)

    // 听不懂（无法识别）
    fun nluError()
}