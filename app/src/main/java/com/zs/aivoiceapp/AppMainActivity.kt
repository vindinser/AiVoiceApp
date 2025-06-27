package com.zs.aivoiceapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.zs.aivoiceapp.service.VoiceService
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper

class AppMainActivity : BaseActivity() {

    // 获取布局ID
    override fun getLayoutId(): Int = R.layout.app_main

    // 获取标题
    override fun getTitleText(): String = getString(com.zs.lib_base.R.string.app_name)

    // 是否显示返回键
    override fun isShowBack(): Boolean = false

    // 权限请求器
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            startServices()
        } else {
            handlePermissionDenied()
        }
    }

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    // 初始化
    override fun initView() {
        // 检查权限
        if (hasRequiredPermissions()) {
            startServices()
        } else {
            requestPermissions()
        }
    }

    // 检查权限
    private fun hasRequiredPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    // 请求权限
    private fun requestPermissions() {
        permissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    // 处理权限被拒绝
    private fun handlePermissionDenied() {
        // 自定义处理逻辑，例如显示对话框或关闭应用
        finish()
    }

    // 启动服务
    private fun startServices() {
        // 启动语音服务
        startService(Intent(this, VoiceService::class.java))

        // 跳转开发者模式
        ARouterHelper.startActivity(ARouterHelper.PATH_DEVELOPER)
    }
}