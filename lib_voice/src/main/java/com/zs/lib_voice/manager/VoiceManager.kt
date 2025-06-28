package com.zs.lib_voice.manager

import android.content.Context
import android.util.Log
import com.baidu.speech.EventListener
import com.baidu.speech.asr.SpeechConstant
import com.zs.lib_voice.tts.VoiceTTS
import com.zs.lib_voice.wakeup.VoiceWakeUp

/**
 * 语音管理类
 */
object VoiceManager: EventListener {

    private var TAG = VoiceManager::class.java.simpleName

    // 语音Key
    const val VOICE_APP_ID = "119333605"
    const val VOICE_APP_KEY = "z5WbhZoNBzcKObxsrYKcWIaU"
    const val VOICE_APP_SECRET = "wKT3COoIYgMcKsFhs3pqlhlAKx1OFFbc"

    // 初始化
    fun initManager(mContext: Context) {
        VoiceTTS.initTTS(mContext)

        VoiceWakeUp.initWakeUp(mContext, this)
    }

    override fun onEvent(name: String?, params: String?, byte: ByteArray?, offset: Int, length: Int) {
        Log.d(TAG, String.format("event: name=%s, params=%s", name, params))

        name?.let {
            when(it) {
                SpeechConstant.CALLBACK_EVENT_WAKEUP_SUCCESS -> ttsStart("我在呢！")
            }
        }
    }

    /**
     * --------------------TTS Start
     */
    // 播放
    fun ttsStart(text: String) {
        Log.d(TAG, "TTS：播放--> $text")
        VoiceTTS.start(text)
    }

    // 播放且有回调
    fun ttsStart(text: String, mOnTTSResultListener: VoiceTTS.OnTTSResultListener) {
        Log.d(TAG, "TTS：播放且有回调--> $text")
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

    /**
     * --------------------WakeUp Start
     */
    // 启动唤醒
    fun startWakeUp() {
        Log.d(TAG, "启动唤醒")
        VoiceWakeUp.startWakeUp()
    }

    // 停止唤醒
    fun stopWakeUp() {
        Log.d(TAG, "停止唤醒")
        VoiceWakeUp.stopWakeUp()
    }

    // 释放唤醒资源
    fun releaseWakeUp() {
        Log.d(TAG, "释放唤醒资源")
        VoiceWakeUp.releaseWakeUp()
    }
    /**
     * --------------------WakeUp End
     */
}