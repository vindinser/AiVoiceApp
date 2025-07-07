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

    // 查询天气
    fun queryWeather()

    // 听不懂（无法识别）
    fun nluError()
}