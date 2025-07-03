package com.zs.lib_voice.manager

import android.content.Context
import android.util.Log
import com.baidu.speech.EventListener
import com.baidu.speech.asr.SpeechConstant
import com.zs.lib_voice.asr.VoiceAsr
import com.zs.lib_voice.impl.OnAsrResultListener
import com.zs.lib_voice.tts.VoiceTTS
import com.zs.lib_voice.wakeup.VoiceWakeUp
import com.zs.lib_voice.words.WordsTools
import org.json.JSONObject

/**
 * 语音管理类
 */
object VoiceManager: EventListener {

    private var TAG = VoiceManager::class.java.simpleName

    // 语音Key
    const val VOICE_APP_ID = "119333605"
    const val VOICE_APP_KEY = "z5WbhZoNBzcKObxsrYKcWIaU"
    const val VOICE_APP_SECRET = "wKT3COoIYgMcKsFhs3pqlhlAKx1OFFbc"

    // 接口
    private lateinit var mOnAsrResultListener: OnAsrResultListener

    // 初始化
    fun initManager(mContext: Context, mOnAsrResultListener: OnAsrResultListener) {

        this.mOnAsrResultListener = mOnAsrResultListener

        // 初始化词条工具
        WordsTools.initWordsTools(mContext)
        // 初始化语音合成（TTS）
        VoiceTTS.initTTS(mContext)
        // 初始化语音唤醒
        VoiceWakeUp.initWakeUp(mContext, this)
        // 初始化语音识别（asr）
        VoiceAsr.initAsr(mContext, this)
    }

    override fun onEvent(name: String?, params: String?, byte: ByteArray?, offset: Int, length: Int) {
        // Log.d(TAG, String.format("event: name=%s, params=%s", name, params))

        // 准备阶段
        name?.let {
            when(it) {
                // 唤醒准备就绪
                SpeechConstant.CALLBACK_EVENT_WAKEUP_READY -> mOnAsrResultListener.wakeUpReady()
                // 开始识别
                SpeechConstant.CALLBACK_EVENT_ASR_BEGIN -> mOnAsrResultListener.asrStartSpeak()
                // 结束识别
                SpeechConstant.CALLBACK_EVENT_ASR_END -> mOnAsrResultListener.asrStopSpeak()
                else -> {}
            }
        }

        // 去除脏数据
        if(params == null) {
            return
        }

        val allJSON = JSONObject(params)

        // 监听成功呢
        name?.let {
            when(it) {
                // 唤醒成功
                SpeechConstant.CALLBACK_EVENT_WAKEUP_SUCCESS -> mOnAsrResultListener.wakeUpSuccess(allJSON)
                // 唤醒失败
                SpeechConstant.CALLBACK_EVENT_WAKEUP_ERROR -> mOnAsrResultListener.voiceError("唤醒失败")
                // 语音识别准备就绪
                // SpeechConstant.CALLBACK_EVENT_ASR_READY -> Log.i(TAG, "ASR(语音识别)准备就绪")
                // 语音识别结束
                SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> mOnAsrResultListener.asrResult(allJSON)
                // 最终语义
                SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {
                    byte?.let {
                        var nlu = JSONObject(String(byte, offset, length))
                        mOnAsrResultListener.asrResult(nlu)
                    }
                }
                else -> {}
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
        VoiceWakeUp.releaseWakeUp(this)
    }
    /**
     * --------------------WakeUp End
     */

    /**
     * --------------------Asr Start
     */
    // 启动识别
    fun startAsr() {
        VoiceAsr.start()
    }

    // 停止识别
    fun stopAsr() {
        VoiceAsr.stop()
    }

    // 取消识别
    fun cancelAsr() {
        VoiceAsr.cancel()
    }

    // 释放识别资源
    fun releaseAsr() {
        VoiceAsr.release(this)
    }
    /**
     * --------------------Asr End
     */
}