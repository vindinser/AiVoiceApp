package com.zs.aivoiceapp

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import com.zs.lib_base.helper.NotificationHelper

/**
 * 语音服务
 */
class VoiceService: Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    /**
     * START_STICKY, 当系统内存不足的时候，杀掉了服务，那么在系统内存不再紧张的时候，启动服务
     * START_NOT_STICKY, 当系统内存不足的时候，杀掉了服务，直到下一次 startService才启动
     * START_REDELIVER_INTENT, 重新传递Intent值
     * START_STICKY_COMPATIBILITY, 兼容版本，但是不能保证系统kill掉服务一定能重启
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bindNotification();
        return START_STICKY_COMPATIBILITY
    }

    // 绑定通知栏
    private fun bindNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                1000,
                NotificationHelper.bindVoiceService("正在运行"),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
            )
        } else {
            startForeground(1000, NotificationHelper.bindVoiceService("正在运行"))
        }
    }
}