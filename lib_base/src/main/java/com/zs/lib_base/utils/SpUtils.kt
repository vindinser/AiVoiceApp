package com.zs.lib_base.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * SP 封装
 */
object SpUtils {

    private const val SP_NAME = "config"

    // 对象
    private lateinit var sp: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    // 初始化
    fun initUtils(mContext: Context) {
        sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        spEditor = sp.edit()
        spEditor.apply()
    }

    fun putString(key: String, value: String) {
        spEditor.putString(key, value)
        spEditor.commit()
    }

    fun getString(key: String): String? {
        return sp.getString(key, "")
    }

    fun putInt(key: String, value: Int) {
        spEditor.putInt(key, value)
        spEditor.commit()
    }

    fun getInt(key: String, defaultValue: Int): Int? {
        return sp.getInt(key, defaultValue)
    }
}