package com.zs.lib_base.helper

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.zs.lib_base.R

/**
 * 通知栏的帮助类
 * 运行顺序：应用启动 -> Application（BaseApp）-> run InitService（子线程，会导致异步） -> init 渠道 -> 绑定通知栏
 *                                         -> MainActivity -> OnCreate -> run VoiceService
 */
@SuppressLint("StaticFieldLeak")
object NotificationHelper {

    private lateinit var mContext: Context

    private lateinit var nm: NotificationManager

    private const val CHANNEL_ID = "ai_voice"
    private const val CHANNEL_NAME = "语音服务"

    // 初始化帮助类
    fun initHelper(mContext: Context) {
        this.mContext = mContext

        nm = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        setBindVoiceChannel()
    }

    // 设置绑定服务的渠道
    private fun setBindVoiceChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 创建渠道对象(8.0以后得版本需要创建渠道)
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            // 呼吸灯
            channel.enableLights(false)
            // 震动
            channel.enableVibration(false)
            // 角标
            channel.setShowBadge(false)

            nm.createNotificationChannel(channel)
        }

    }

    // 绑定语音服务
    fun bindVoiceService(contentText: String): Notification {
        // 构建通知栏对象
        val notificationCompat = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(mContext, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(mContext)
        }
        // 设置标题
        notificationCompat.setContentTitle(CHANNEL_NAME)
        // 设置描述
        notificationCompat.setContentText(contentText)
        // 设置时间
        notificationCompat.setWhen(System.currentTimeMillis())
        // 禁止滑动
        notificationCompat.setAutoCancel(false)
        // 必须设置小图标，否则通知可能不会显示
        notificationCompat.setSmallIcon(R.mipmap.ic_launcher)

        return notificationCompat.build()
    }
}