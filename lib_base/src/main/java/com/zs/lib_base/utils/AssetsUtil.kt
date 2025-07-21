package com.zs.lib_base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.zs.lib_base.bean.City
import java.nio.charset.Charset

@SuppressLint("StaticFieldLeak")
object AssetsUtil {

    private lateinit var mContext: Context

    private lateinit var assets: AssetManager

    private lateinit var mGson: Gson

    private lateinit var city: City

    @SuppressLint("CheckResult")
    fun initUntil(mContext: Context) {
        this.mContext = mContext

        assets = mContext.assets

        mGson = Gson()
        city = mGson.fromJson(loadAssetsFile("city.json"), City::class.java)
    }

    // 获取城市数据
    fun getCity(): City {
        return city
    }

    /**
     * 读取资源文件
     * @param {string} path 文件路径
     * @return {string} 文件资源
     */
    private fun loadAssetsFile(path: String): String {
        val inputStream = assets.open(path)

        val buffer = ByteArray(inputStream.available())

        inputStream.read(buffer)
        inputStream.close()

        return String(buffer, Charset.forName("utf-8"))
    }
}