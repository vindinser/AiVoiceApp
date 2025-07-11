package com.zs.lib_base.base

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

abstract class BaseActivity: AppCompatActivity() {

    // 权限请求器
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            // 请求通过执行方法需要子类重写（也可不重写）
            permissionsAllowed()
        } else {
            handlePermissionDenied()
        }
    }

    private val codeWindowRequest = 1000

    // 获取布局ID
    abstract fun getLayoutId(): Int

    // 获取标题
    abstract fun getTitleText(): String

    // 是否显示返回键
    abstract fun isShowBack(): Boolean

    // 初始化
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(getLayoutId())

        // 状态栏适配（API 23+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.run {
                statusBarColor = Color.TRANSPARENT
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // actionBar?.let {
            supportActionBar?.let {
                // 标题
                it.title = getTitleText()
                // 是否显示返回键
                it.setDisplayHomeAsUpEnabled(isShowBack())  // 显示返回箭头
                it.setDisplayShowHomeEnabled(isShowBack())   // 显示可点击的 Home 按钮
                // 取消logo展示
                it.setIcon(null)
                // 透明度
                it.elevation = 0f
                // 设置自定义背景色（避免黑色背景）
                // it.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            }
        }

        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    // 检查安卓版本是否大于6.0
    private fun checkGreaterThanM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    // 检查窗口权限
    protected fun checkWindowPermission(): Boolean {
        if(checkGreaterThanM()) {
            return Settings.canDrawOverlays(this)
        }
        return true
    }

    // 申请窗口权限
    protected fun requestWindowPermission() {
        if(checkGreaterThanM()) {
            // startActivityForResult(
            //     Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package${ packageName }")),
            //     codeWindowRequest
            // )
            try {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                    data = Uri.parse("package:$packageName")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, codeWindowRequest)
                } else {
                    openAppSettingsFallback()
                }
            } catch (e: Exception) {
                openAppSettingsFallback()
            }
        }
    }

    // 备用回退方案（处理不支持设备）
    private fun openAppSettingsFallback() {
        try {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:$packageName")
                startActivityForResult(this, codeWindowRequest)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "请到设置 → 应用 → 本应用 → 权限中开启悬浮窗权限", Toast.LENGTH_LONG).show()
        }
    }

    // 检查权限（多个）
    protected fun hasRequiredPermissions(permissions: Array<String>): Boolean {
        if(checkGreaterThanM()) {
            return permissions.all { permission ->
                ContextCompat.checkSelfPermission(
                    this, permission
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }

    // 检查权限（单个）
    protected fun checkPermission(permission: String): Boolean {
        if(checkGreaterThanM()) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED
        }
        return true
    }

    // 检查权限（多个）
    protected fun checkPermission(permission: Array<String>): Boolean {
        if(checkGreaterThanM()) {
            permission.forEach {
                if(checkSelfPermission(it) == PackageManager.PERMISSION_DENIED) {
                    return false
                }
            }
        }
        return true
    }

    // 处理权限被拒绝
    private fun handlePermissionDenied() {
        // 自定义处理逻辑，例如显示对话框或关闭应用
        finish()
    }

    // 请求权限
    protected fun requestPermissions(permissions: Array<String>) {
        permissionLauncher.launch(permissions)
    }

    // 请求权限以后需要做什么
    open fun permissionsAllowed() {}

    // 请求权限（使用AndPermission）
    // protected fun requestPermissionsAndPermission(permission: Array<String>, granted: Auction<List<String>>) {
    //     if(checkGreaterThanM()) {
    //         AndPermission.with(this)
    //             .runtime()
    //             .permission(permission)
    //             .onGranted(granted)
    //             .srart()
    //     }
    // }
}