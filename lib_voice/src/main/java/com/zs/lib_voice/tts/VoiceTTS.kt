package com.zs.lib_voice.tts

import android.content.Context
import android.util.Log
import com.baidu.tts.client.SpeechError
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.SpeechSynthesizerListener
import com.baidu.tts.client.TtsMode
import com.zs.lib_voice.manager.VoiceManager


/**
 * 百度AI语音 - TTS 封装
 */
object VoiceTTS : SpeechSynthesizerListener {

    private var TAG = VoiceTTS::class.java.simpleName

    // TTS对象
    private lateinit var mSpeechSynthesizer: SpeechSynthesizer

    // 接口对象
    private var mOnTTSResultListener: OnTTSResultListener? = null

    // 初始化TTS
    fun initTTS(mContext: Context) {
        // 初始化 TTS对象
        mSpeechSynthesizer = SpeechSynthesizer.getInstance()
        // 设置上下文
        mSpeechSynthesizer.setContext(mContext)
        // 设置Key
        mSpeechSynthesizer.setAppId(VoiceManager.VOICE_APP_ID)
        mSpeechSynthesizer.setApiKey(VoiceManager.VOICE_APP_KEY, VoiceManager.VOICE_APP_SECRET)

        // 设置监听
        mSpeechSynthesizer.setSpeechSynthesizerListener(this)

        // 发声人
        setPeople("0")
        // 语速
        setVoiceSpeed("5")
        // 音量
        setVoiceVolume("15")

        // 初始化
        mSpeechSynthesizer.initTts(TtsMode.ONLINE)
        Log.i(TAG, "TTS init")
    }

    // 设置语速
    fun setVoiceSpeed(speed: String) {
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, speed)
    }

    // 设置音量
    fun setVoiceVolume(volume: String) {
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, volume)
    }

    // 设置发音人
    fun setPeople(people: String) {
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, people)
    }

    override fun onSynthesizeStart(p0: String?) {
        Log.i(TAG, "合成开始")
    }

    override fun onSynthesizeDataArrived(p0: String?, p1: ByteArray?, p2: Int, p3: Int) {

    }

    override fun onSynthesizeFinish(p0: String?) {
        Log.i(TAG, "合成结束")
    }

    override fun onSpeechStart(p0: String?) {
        Log.i(TAG, "开始播放")
    }

    override fun onSpeechProgressChanged(p0: String?, p1: Int) {

    }

    override fun onSpeechFinish(p0: String?) {
        Log.i(TAG, "播放结束")
        mOnTTSResultListener?.ttsEnd()
    }

    override fun onError(p0: String?, p1: SpeechError?) {
        Log.e(TAG, "TTS 错误: ${ p0 }-${ p1 }")
    }

    // 播放
    fun start(text: String) {
        mSpeechSynthesizer.speak(text)
    }

    // 播放且有回调
    fun start(text: String, mOnTTSResultListener: OnTTSResultListener) {
        this.mOnTTSResultListener = mOnTTSResultListener

        mSpeechSynthesizer.speak(text)
    }

    // 暂停
    fun pause() {
        mSpeechSynthesizer.pause()
    }

    // 继续
    fun resume() {
        mSpeechSynthesizer.resume()
    }

    // 停止
    fun stop() {
        mSpeechSynthesizer.stop()
    }

    // 释放资源
    fun release() {
        mSpeechSynthesizer.release()
    }

    // 接口
    interface OnTTSResultListener {

        // 播放结束
        fun ttsEnd()
    }
}