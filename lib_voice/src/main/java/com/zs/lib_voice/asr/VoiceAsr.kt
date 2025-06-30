package com.zs.lib_voice.asr

import android.content.Context
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.zs.lib_voice.manager.VoiceManager
import org.json.JSONObject

/**
 * 语音识别
 */
object VoiceAsr {

    // 启动数据
    private lateinit var asrJson: String

    // 识别对象
    private lateinit var asr: EventManager

    fun initAsr(mContext: Context, listener: EventListener) {

        val map = HashMap<Any?, Any?>().apply {
            // 百度语音SDK 4.x版本要求 必须通过启动参数传递APP_ID ，仅配置AndroidManifest无法满足唤醒功能的认证要求
            put(SpeechConstant.APP_ID, VoiceManager.VOICE_APP_ID)
            put(SpeechConstant.APP_KEY, VoiceManager.VOICE_APP_KEY)
            put(SpeechConstant.SECRET, VoiceManager.VOICE_APP_SECRET)
        }

        // 是否获取音量（默认打开）
        map[SpeechConstant.ACCEPT_AUDIO_VOLUME] = true
        map[SpeechConstant.ACCEPT_AUDIO_DATA] = false
        map[SpeechConstant.DISABLE_PUNCTUATION] = false
        map[SpeechConstant.PID] = 1537 // 15363
        // 转化成JSON
        asrJson = JSONObject(map).toString()

        asr = EventManagerFactory.create(mContext, "asr")
        asr.registerListener(listener)
    }

    // 开始识别
    fun start() {
        asr.send(SpeechConstant.ASR_START, asrJson, null, 0, 0)
    }

    // 停止识别
    fun stop() {
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0)
    }

    // 取消识别
    fun cancel() {
        asr.send(SpeechConstant.ASR_CANCEL, null, null, 0, 0)
    }

    // 释放资源
    fun release(listener: EventListener) {
        asr.unregisterListener(listener)
    }
}