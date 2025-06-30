package com.zs.lib_voice.wakeup

import android.content.Context
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.zs.lib_voice.manager.VoiceManager
import org.json.JSONObject


/**
 * 语音唤醒（语音识别）
 */
object VoiceWakeUp {

    // 启动数据
    private lateinit var wakeUpJson: String

    // 唤醒对象
    private lateinit var wp: EventManager

    // 初始化
    fun initWakeUp(mContext: Context, listener: EventListener) {
        val map = HashMap<Any?, Any?>().apply {
            // 百度语音SDK 4.x版本要求 必须通过启动参数传递APP_ID ，仅配置AndroidManifest无法满足唤醒功能的认证要求
            put(SpeechConstant.APP_ID, VoiceManager.VOICE_APP_ID)
            put(SpeechConstant.APP_KEY, VoiceManager.VOICE_APP_KEY)
            put(SpeechConstant.SECRET, VoiceManager.VOICE_APP_SECRET)
        }

        // 本地文件路径
        map[SpeechConstant.WP_WORDS_FILE] = "assets:///WakeUp.bin"
        // 是否获取音量（默认打开）
        map[SpeechConstant.ACCEPT_AUDIO_VOLUME] = false
        // 转化成JSON
        wakeUpJson = JSONObject(map).toString()

        // 设置监听器
        wp = EventManagerFactory.create(mContext, "wp")
        wp.registerListener(listener)
    }

    // 启动唤醒
    fun startWakeUp() {
        wp.send(SpeechConstant.WAKEUP_START, wakeUpJson, null, 0, 0)
    }

    // 停止唤醒
    fun stopWakeUp() {
        wp.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0)
    }

    // 释放唤醒资源
    fun releaseWakeUp(listener: EventListener) {
        wp.unregisterListener(listener)
    }
}