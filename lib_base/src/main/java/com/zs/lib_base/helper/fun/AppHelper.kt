package com.zs.lib_base.helper.`fun`

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.zs.lib_base.helper.`fun`.data.AppData
import com.zs.lib_base.utils.L
import androidx.core.net.toUri
import com.zs.lib_base.R

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

    // 分页View
    val mAllViewList = ArrayList<View>()

    fun init(mContext: Context) {
        this.mContext = mContext
        pm = mContext.packageManager

        loadAllApp()
    }

    // 加载所有APP（获取设备上所有显示在桌面/启动器中的应用列表）
    private fun loadAllApp() {
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

        initViewPage()
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

    // 初始化 pageview
    private fun initViewPage() {
        for(i in 0 until  getPageSize()) {
            val constraintLayout = View.inflate(mContext, R.layout.layout_app_manager_item, null) as ViewGroup
            // FrameLayout
            val rootView = constraintLayout.getChildAt(0) as ViewGroup
            // 第一层线性布局
            for(j in 0 until rootView.childCount) {
                // 第二层 六个线性布局
                val childX = rootView.getChildAt(j) as ViewGroup
                // 第三层 四个线性布局
                for(k in 0 until childX.childCount) {
                    // 第四层 两个view: imageView、textview
                    val child = childX.getChildAt(k) as ViewGroup
                    // imageView
                    val iv = child.getChildAt(0) as ImageView
                    // textview
                    val tv = child.getChildAt(1) as TextView

                    // 当前下标
                    val index = i * 24 + j * 4 + k
                    // 防止下标越界
                    if(index < mAllAppList.size) {
                        // 获取数据
                        val data = mAllAppList[index]
                        // 渲染图标
                        iv.setImageDrawable(data.appIcon)
                        // 渲染应用名称
                        tv.text = data.appName
                        // 设置点击事件
                        child.setOnClickListener {
                            intentApp(data.packageName)
                        }
                    }
                }
            }
            mAllViewList.add(rootView)
        }
    }

    // 获取页面数量（ViewPage）
    fun getPageSize(): Int {
        return mAllAppList.size / 24 + 1
    }

    // 获取非系统应用
    fun getNotSystemApp(): List<AppData> {
        return mAllAppList.filter { !it.isSystemApp }
    }
}