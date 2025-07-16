package com.zs.aivoiceapp.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.zs.aivoiceapp.R
import com.zs.aivoiceapp.adapter.ChatListAdapter
import com.zs.aivoiceapp.data.ChatListData
import com.zs.aivoiceapp.entity.AppConstants
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.helper.NotificationHelper
import com.zs.lib_base.helper.SoundPoolHelper
import com.zs.lib_base.helper.WindowHelper
import com.zs.lib_base.helper.`fun`.AppHelper
import com.zs.lib_base.helper.`fun`.CommonSettingHelper
import com.zs.lib_base.helper.`fun`.ConsTellHelper
import com.zs.lib_base.helper.`fun`.ContactHelper
import com.zs.lib_base.utils.L
import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.JokeOneData
import com.zs.lib_network.bean.RobotData
import com.zs.lib_voice.engine.VoiceEngineAnalyze
import com.zs.lib_voice.impl.OnAsrResultListener
import com.zs.lib_voice.impl.OnNluResultListener
import com.zs.lib_voice.manager.VoiceManager
import com.zs.lib_voice.tts.VoiceTTS
import com.zs.lib_voice.words.WordsTools
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 语音服务
 */
class VoiceService: Service(), OnNluResultListener {

    private val mHandler = Handler()

    private lateinit var mFullWindowView: View
    private lateinit var mChatListView: RecyclerView
    private var mList = ArrayList<ChatListData>()
    private lateinit var mChatListAdapter: ChatListAdapter

    private lateinit var mLottieView: LottieAnimationView
    private lateinit var tvVoiceTips: TextView

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

        // lottie 动画初始化
        mLottieView = mFullWindowView.findViewById<LottieAnimationView>(R.id.mLottieView)
        // 动画补充文本
        tvVoiceTips = mFullWindowView.findViewById<TextView>(R.id.tvVoiceTips)

        L.i("语音服务启动 --> baiduTTS")
        VoiceManager.initManager(this, object : OnAsrResultListener {

            override fun wakeUpReady() {
                L.i("唤醒准备就绪")
                addAIText("唤醒准备就绪")
                // VoiceManager.ttsStart("唤醒准备就绪")
            }

            override fun wakeUpSuccess(result: JSONObject) {
                L.i("唤醒成功：${ result }")
                val errCode = result.optInt("errorCode")
                if(errCode == 0) {
                    // 唤醒词
                    val word = result.optString("word")
                    if(word == "小雪同学") {
                        wakeUpFix()
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
                addMineText(nlu.optString("raw_text"))
                // addAIText(nlu.toString())
                VoiceEngineAnalyze.analyzeNlu(nlu, this@VoiceService)
            }

            override fun voiceError(text: String) {
                L.i("发生错误：${ text }")
                hideWindow()
            }

            override fun updateUserText(text: String) {
                updateTipsText(text)
            }
        })
    }

    // 唤醒成功
    private fun wakeUpFix() {
        showWindow()
        updateTipsText("正在聆听。。。")
        SoundPoolHelper.play(R.raw.record_start)
        // 应答
        val wakeupText = WordsTools.wakeUpWords()
        addAIText(wakeupText, object : VoiceTTS.OnTTSResultListener {
            override fun ttsEnd() {
                // 启动语音识别
                VoiceManager.startAsr()
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

    // 显示窗口
    private fun showWindow() {
        L.i("================== 显示窗口 ====================")
        mLottieView.playAnimation()
        WindowHelper.show(mFullWindowView)
    }

    // 隐藏窗口
    private fun hideWindow() {
        mHandler.postDelayed({
            L.i("================== 隐藏窗口 ====================")
            mLottieView.pauseAnimation()
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
        VoiceManager.ttsStart(text)
    }

    // 添加AI的文本
    private fun addAIText(text: String, mOnTTSResultListener: VoiceTTS.OnTTSResultListener) {
        val bean = ChatListData(AppConstants.TYPE_AI_TEXT)
        bean.text = text
        baseAddItem(bean)
        VoiceManager.ttsStart(text, mOnTTSResultListener)
    }

    // 添加基类
    private fun baseAddItem(bean: ChatListData) {
        mList.add(bean)
        mChatListAdapter.notifyItemInserted(mList.size - 1)
    }

    // 更新提示语
    private fun updateTipsText(text: String) {
        tvVoiceTips.text = text
    }

    /**
     * ====================================== OnNluResultListener ======================================
     */
    // 查询天气
    override fun queryWeather() {

    }

    // 打开APP
    override fun openApp(appName: String) {
        if(!TextUtils.isEmpty(appName)) {
            L.i("Open App $appName")
            val isOpen = AppHelper.lunchApp(appName)
            if(isOpen) {
                addAIText("正在为您打开$appName")
                // VoiceManager.ttsStart("正在为您打开$appName")
            } else {
                addAIText("很抱歉，无法为您打开$appName")
                // VoiceManager.ttsStart("很抱歉，无法为您打开$appName")
            }
        }
        hideWindow()
    }

    // 卸载APP
    override fun unInstallApp(appName: String) {
        if(!TextUtils.isEmpty(appName)) {
            L.i("卸载 App $appName")
            val isUnInstall = AppHelper.unInstallApp(appName)
            if(isUnInstall) {
                addAIText("正在为您卸载$appName")
            } else {
                addAIText("很抱歉，无法为您卸载$appName")
            }
        }
        hideWindow()
    }

    // 其他App操作（更新、下载、搜索、推荐）
    override fun otherApp(appName: String) {
        if(!TextUtils.isEmpty(appName)) {
            val isIntent = AppHelper.launchAppStore(appName)
            if(isIntent) {
                addAIText("正在为您操作$appName")
            } else {
                nluError()
            }
        }
        hideWindow()
    }

    // 无法识别
    override fun nluError() {
        addAIText(WordsTools.noAnswerWords())
    }

    // 返回
    override fun back() {
        addAIText("正在为您执行返回操作")
        CommonSettingHelper.back()
        hideWindow()
    }

    // 返回主页
    override fun home() {
        addAIText("正在为您返回主页")
        CommonSettingHelper.home()
        hideWindow()
    }

    // 音量+
    override fun setVolumeUp() {
        addAIText("已为您增加音量")
        CommonSettingHelper.setVolumeUp()
        hideWindow()
    }

    // 音量-
    override fun setVolumeDown() {
        addAIText("已为您降低音量")
        CommonSettingHelper.setVolumeDown()
        hideWindow()
    }

    // 退出
    override fun quit() {
        addAIText("正在为您执行退出操作")
        CommonSettingHelper.quit()
        hideWindow()
    }

    // 拨打电话（通讯录联系人）
    override fun callPhoneForName(name: String) {
        val list = ContactHelper.mContactList.filter { it.phoneName == name }
        if(list.isNotEmpty()) {
            addAIText("正在为您拨打${ name }的电话", object : VoiceTTS.OnTTSResultListener{
                override fun ttsEnd() {
                    ContactHelper.callPhone(list[0].phoneNumber)
                }
            })
        } else {
            addAIText("并未在通讯录中找到${ name }的电话")
        }
    }

    // 拨打电话（电话号码）
    override fun callPhoneForNumber(number: String) {
        addAIText("正在为您拨打电话：${ number }", object : VoiceTTS.OnTTSResultListener {
            override fun ttsEnd() {
                ContactHelper.callPhone(number)
            }
        })
    }

    // 播放笑话
    override fun tellJoke() {
        HttpManager.queryJoke(object : Callback<JokeOneData> {
            override fun onResponse(call: Call<JokeOneData>, response: Response<JokeOneData>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        if(it.error_code == 0) {
                            // 查询到笑话 根据result随机播放笑话
                            val radomInt = WordsTools.radomInt(it.result.size)
                            if(radomInt < it.result.size) {
                                val result = it.result[radomInt]
                                addAIText(result.content, object : VoiceTTS.OnTTSResultListener {
                                    override fun ttsEnd() {
                                        hideWindow()
                                    }
                                })
                            }
                        } else {
                            addAIText("很抱歉，没有为您查询到笑话")
                        }
                    }
                } else {
                    addAIText("很抱歉，没有为您查询到笑话")
                }
            }

            override fun onFailure(call: Call<JokeOneData>, t: Throwable) {
                addAIText("很抱歉，没有为您查询到笑话")
            }
        })
    }

    // 笑话列表
    override fun jokeList() {
        addAIText("正在为您搜索笑话")
        ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
        hideWindow()
    }

    // 星座时间
    override fun consTellTime(name: String) {
        val text = ConsTellHelper.getConsTellTime(name)
        addAIText(text, object : VoiceTTS.OnTTSResultListener {
            override fun ttsEnd() {
                hideWindow()
            }
        })
    }

    // 星座信息
    override fun consTellInfo(name: String) {
        addAIText("正在为您搜索${ name }信息", object : VoiceTTS.OnTTSResultListener {
            override fun ttsEnd() {
                ARouterHelper.startActivity(ARouterHelper.PATH_CONSTELLATION, "name", name)
                hideWindow()
            }
        })
    }

    // 机器人对话
    override fun aiRobot(text: String) {
        // 请求机器人回答
        HttpManager.aiRobotChat(text, object : Callback<RobotData> {
            override fun onResponse(call: Call<RobotData>, response: Response<RobotData>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        if(it.intent.code == 0) {
                            // 回答
                            if(it.results.isEmpty()) {
                                nluError()
                            } else {
                                it.results[0].values.get("text")?.let { resText ->
                                    addAIText(resText)
                                    hideWindow()
                                }
                            }
                        } else {
                            nluError()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RobotData>, t: Throwable) {
                nluError()
            }
        })
    }
}