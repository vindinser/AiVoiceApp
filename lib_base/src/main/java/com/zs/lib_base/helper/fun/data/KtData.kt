package com.zs.lib_base.helper.`fun`.data

import android.graphics.drawable.Drawable

/**
 * kt 数据
 * @param {string} packageName 包名
 * @param {string} appName 应用名称
 * @param {drawable} appIcon 应用图标
 * @param {string} firstRunName 第一启动类
 * @param {boolean} isSystemApp 是否是系统应用
 */
data class AppData(
    val packageName: String,
    val appName: String,
    val appIcon: Drawable,
    val firstRunName: String,
    val isSystemApp: Boolean
)

/**
 * 联系人 数据
 * @param {string} phoneName 姓名
 * @param {string} phoneNumber 号码
 */
data class ContactData (
    val phoneName: String,
    val phoneNumber: String
)