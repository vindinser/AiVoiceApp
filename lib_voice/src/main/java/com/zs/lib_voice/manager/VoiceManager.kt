package com.zs.lib_voice.manager

import android.content.Context
import com.zs.lib_voice.tts.VoiceTTS

/**
 * 语音管理类
 */
object VoiceManager {

    // 语音Key
    const val VOICE_APP_ID = "119333605"
    const val VOICE_APP_KEY = "z5WbhZoNBzcKObxsrYKcWIaU"
    const val VOICE_APP_SECRET = "wKT3COoIYgMcKsFhs3pqlhlAKx1OFFbc"

    fun initManager(mContext: Context) {
        VoiceTTS.initTTS(mContext)
    }

    /**
     * --------------------TTS Start
     */
    // 播放
    fun ttsStart(text: String) {
        VoiceTTS.start(text)
    }

    // 播放且有回调
    fun ttsStart(text: String, mOnTTSResultListener: VoiceTTS.OnTTSResultListener) {
        VoiceTTS.start(text, mOnTTSResultListener)
    }

    // 暂停
    fun ttsPause() {
        VoiceTTS.pause()
    }

    // 继续
    fun ttsResume() {
        VoiceTTS.resume()
    }

    // 停止
    fun ttsStop() {
        VoiceTTS.stop()
    }

    // 释放资源
    fun ttsRelease() {
        VoiceTTS.release()
    }

    // 设置语速
    fun setVoiceSpeed(speed: String) {
        VoiceTTS.setVoiceSpeed(speed)
    }

    // 设置音量
    fun setVoiceVolume(volume: String) {
        VoiceTTS.setVoiceVolume(volume)
    }

    // 设置发音人
    fun setPeople(people: String) {
        VoiceTTS.setPeople(people)
    }
    /**
     * --------------------TTS End
     */
}