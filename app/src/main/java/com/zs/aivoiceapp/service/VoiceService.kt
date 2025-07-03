package com.zs.aivoiceapp.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.aivoiceapp.R
import com.zs.aivoiceapp.adapter.ChatListAdapter
import com.zs.aivoiceapp.data.ChatListData
import com.zs.aivoiceapp.entity.AppConstants
import com.zs.lib_base.helper.NotificationHelper
import com.zs.lib_base.helper.WindowHelper
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

    private val mHandler = Handler()

    private lateinit var mFullWindowView: View
    private lateinit var mChatListView: RecyclerView
    private var mList = ArrayList<ChatListData>()
    private lateinit var mChatListAdapter: ChatListAdapter

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        initCoreVoiceService()
    }

    // 初始化语音服务
    private fun initCoreVoiceService() {

        L.i("窗口初始化 --> WindowManager")
        WindowHelper.initHelper(this)
        mFullWindowView = WindowHelper.getView(R.layout.layout_window_item)
        mChatListView = mFullWindowView.findViewById<RecyclerView>(R.id.mChatListView)
        mChatListView.layoutManager = LinearLayoutManager(this)
        mChatListAdapter = ChatListAdapter(mList)

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
                        val wakeupText = WordsTools.wakeUpWords()
                        addMineText((wakeupText))
                        VoiceManager.ttsStart(wakeupText, object : VoiceTTS.OnTTSResultListener {
                            override fun ttsEnd() {
                                // 启动语音识别
                                VoiceManager.startAsr()
                            }
                        })
                    }
                }
            }

            override fun asrStartSpeak() {
                showWindow()
                L.i("开始说话")
            }

            override fun asrStopSpeak() {
                L.i("结束说话")
                hideWindow()
            }

            override fun asrResult(result: JSONObject) {
                L.i("====================== RESULT ============================")
                L.i("result：${ result }")
            }

            override fun nluResult(nlu: JSONObject) {
                L.i("====================== NLU ============================")
                L.i("nlu：$nlu")
                addAIText(nlu.toString())
                VoiceEngineAnalyze.analyzeNlu(nlu, this@VoiceService)
            }

            override fun voiceError(text: String) {
                L.i("发生错误：${ text }")
                hideWindow()
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

    // 显示窗口
    private fun showWindow() {
        L.i("================== 显示窗口 ====================")
        WindowHelper.show(mFullWindowView)
    }

    // 隐藏窗口
    private fun hideWindow() {
        mHandler.postDelayed({
            L.i("================== 隐藏窗口 ====================")
            WindowHelper.hide(mFullWindowView)
        }, 2*1000)
    }

    // 添加我的文本
    private fun addMineText(text: String) {
        val bean = ChatListData(AppConstants.TYPE_MINE_TEXT)
        bean.text = text
        baseAddItem(bean)
    }

    // 添加AI的文本
    private fun addAIText(text: String) {
        val bean = ChatListData(AppConstants.TYPE_AI_TEXT)
        bean.text = text
        baseAddItem(bean)
    }

    // 添加基类
    private fun baseAddItem(bean: ChatListData) {
        mList.add(bean)
        mChatListAdapter.notifyItemInserted(mList.size - 1)
    }
}