package com.zs.lib_base.helper.`fun`

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import com.zs.lib_base.helper.`fun`.data.AppData
import com.zs.lib_base.utils.L
import androidx.core.net.toUri

/**
 * 应用帮助类
 */
@SuppressLint("StaticFieldLeak")
object AppHelper {

    private lateinit var mContext: Context
    // 包管理器
    private lateinit var pm: PackageManager

    // 所有应用
    private val mAllAppList = ArrayList<AppData>()

    fun init(mContext: Context) {
        this.mContext = mContext
        pm = mContext.packageManager

        loadAllApp()
    }

    // 加载所有APP（获取设备上所有显示在桌面/启动器中的应用列表）
    fun loadAllApp() {
        val intent = Intent(Intent.ACTION_MAIN, null)
        // 带有启动类的 intent 对象
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val appInfo = pm.queryIntentActivities(intent, 0)

        appInfo.forEachIndexed { _, resolveInfo ->
            val appData = AppData(
                resolveInfo.activityInfo.packageName,
                resolveInfo.loadLabel(pm) as String,
                resolveInfo.loadIcon(pm),
                resolveInfo.activityInfo.name,
                resolveInfo.activityInfo.flags == ApplicationInfo.FLAG_SYSTEM,
            )
            mAllAppList.add(appData)
        }

        L.e("mAllAppList:${ mAllAppList }")
    }

    // 启动App
    fun lunchApp(appName: String): Boolean {
        if(mAllAppList.size > 0) {
            mAllAppList.forEach{
                if(it.appName == appName) {
                    intentApp(it.packageName)
                    return true
                }
            }
        }
        return false
    }

    // 启动APP
    private fun intentApp(packageName: String) {
        val intent = pm.getLaunchIntentForPackage(packageName)
        intent?.let {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(it)
        }
    }

    // 卸载App
    fun unInstallApp(appName: String): Boolean {
        if(mAllAppList.size > 0) {
            mAllAppList.forEach{
                if(it.appName == appName) {
                    intentUninstallApp(it.packageName)
                    return true
                }
            }
        }
        return false
    }

    // 卸载APP
    private fun intentUninstallApp(packageName: String) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = "package:${ packageName }".toUri()
        mContext.startActivity(intent)
    }

    // 跳转应用商店
    fun intentAppStore(packageName: String, marketPackageName: String) {
        val uri = "market://details?id=${ packageName }".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(marketPackageName)
        mContext.startActivity(intent)
    }
}