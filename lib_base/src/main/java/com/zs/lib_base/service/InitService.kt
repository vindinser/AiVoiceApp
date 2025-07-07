package com.zs.lib_base.service

import android.app.IntentService
import android.content.Intent
import com.zs.lib_base.helper.SoundPoolHelper
import com.zs.lib_base.helper.`fun`.AppHelper
import com.zs.lib_base.helper.`fun`.CommonSettingHelper
import com.zs.lib_base.utils.L
import com.zs.lib_base.utils.SpUtils

/**
 * 初始化服务
 * 短任务，执行后销毁
 */
class InitService: IntentService(InitService::class.simpleName) {

    override fun onCreate() {
        super.onCreate()
        L.i("IntentService ==> 初始化开始")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        L.i("IntentService ==> 初始化")

        // 初始化SP
        SpUtils.initUtils(this)

        // 初始化播放（SoundPool）
        SoundPoolHelper.init(this)

        // 初始化应用采集
        AppHelper.init(this)

        // 初始化通用设置帮助类
        CommonSettingHelper.init(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        L.i("IntentService ==> 初始化完成")
    }

}