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
    fun start(text: String) {
        VoiceTTS.start(text)
    }

    // 暂停
    fun pause() {
        VoiceTTS.pause()
    }

    // 继续
    fun resume() {
        VoiceTTS.resume()
    }

    // 停止
    fun stop() {
        VoiceTTS.stop()
    }

    // 释放资源
    fun release() {
        VoiceTTS.release()
    }
    /**
     * --------------------TTS End
     */
}