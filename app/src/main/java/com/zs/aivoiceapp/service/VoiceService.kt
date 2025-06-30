package com.zs.aivoiceapp.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import com.zs.lib_base.helper.NotificationHelper
import com.zs.lib_base.utils.L
import com.zs.lib_voice.engine.VoiceEngineAnalyze
import com.zs.lib_voice.impl.OnAsrResultListener
import com.zs.lib_voice.impl.OnNluResultListener
import com.zs.lib_voice.manager.VoiceManager
import com.zs.lib_voice.tts.VoiceTTS
import com.zs.lib_voice.words.WordsTools
import org.json.JSONObject

/**
 * 语音服务
 */
class VoiceService: Service(), OnNluResultListener {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        initCoreVoiceService()
    }

    // 初始化语音服务
    private fun initCoreVoiceService() {

        L.i("语音服务启动 --> baiduTTS")
        VoiceManager.initManager(this, object : OnAsrResultListener{

            override fun wakeUpReady() {
                L.i("唤醒准备就绪")
                // VoiceManager.ttsStart("唤醒准备就绪")
            }

            override fun wakeUpSuccess(result: JSONObject) {
                L.i("唤醒成功：${ result }")
                val errCode = result.optInt("errorCode")
                if(errCode == 0) {
                    // 唤醒词
                    val word = result.optString("word")
                    if(word == "小雪同学") {
                        // 应答
                        VoiceManager.ttsStart(WordsTools.wakeUpWords(), object : VoiceTTS.OnTTSResultListener {
                            override fun ttsEnd() {
                                // 启动语音识别
                                VoiceManager.startAsr()
                            }
                        })
                    }
                }
            }

            override fun asrStartSpeak() {
                L.i("开始说话")
            }

            override fun asrStopSpeak() {
                L.i("结束说话")
            }

            override fun asrResult(result: JSONObject) {
                L.i("====================== RESULT ============================")
                L.i("result：${ result }")
            }

            override fun nluResult(nlu: JSONObject) {
                L.i("====================== NLU ============================")
                L.i("nlu：$nlu")
                VoiceEngineAnalyze.analyzeNlu(nlu, this@VoiceService)
            }

            override fun voiceError(text: String) {
                L.i("发生错误：${ text }")
            }
        })
    }

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

    // 查询天气
    override fun queryWeather() {

    }
}