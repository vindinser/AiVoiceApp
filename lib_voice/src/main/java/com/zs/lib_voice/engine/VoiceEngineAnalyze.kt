package com.zs.lib_voice.engine

import android.util.Log
import com.zs.lib_voice.impl.OnNluResultListener
import com.zs.lib_voice.words.NluWords
import org.json.JSONObject

/**
 * 语音引擎分析
 */
object VoiceEngineAnalyze {

    private var TAG = VoiceEngineAnalyze::class.java.simpleName

    private lateinit var mOnNluResultListener: OnNluResultListener

    // 分析结果
    fun analyzeNlu(nlu: JSONObject, mOnNluResultListener: OnNluResultListener) {

        this.mOnNluResultListener = mOnNluResultListener

        // 用户说的话
        val rawText = nlu.optString("row_text")
        Log.i(TAG, rawText)

        // 解析 result
        val result = nlu.optJSONArray("result")?:return
        val nluResultLength = result.length()
        if(nluResultLength >= 1) {
            // 存在多条情况
            analyzeNluSingle(result[0] as JSONObject)
        } else {
            return
        }
    }

    // 单条解析
    private fun analyzeNluSingle(result: JSONObject) {
        val domain = result.optString("domain")
        val intent = result.optString("intent")
        val slots = result.optJSONObject("slots")

        slots?.let {
            when(domain) {
                // 打开APP
                NluWords.NUL_APP -> {
                    when(intent) {
                        NluWords.INTENT_OPEN_APP,
                        NluWords.INTENT_UNINSTALL_APP,
                        NluWords.INTENT_UPDATE_APP,
                        NluWords.INTENT_DOWNLOAD_APP,
                        NluWords.INTENT_SEARCH_APP,
                        NluWords.INTENT_RECOMMEND_APP -> {
                            // 获取打开App的名称
                            val userAppName = it.optJSONArray("user_app_name")
                            userAppName?.let { appName ->
                                if(appName.length() > 0) {
                                    val obj = appName[0] as JSONObject
                                    val word = obj.optString("word")
                                    when (intent) {
                                        // 打开APP
                                        NluWords.INTENT_OPEN_APP -> mOnNluResultListener.openApp(word)
                                        // 卸载APP
                                        NluWords.INTENT_UNINSTALL_APP -> mOnNluResultListener.unInstallApp(word)
                                        // 其他APP操作
                                        else -> mOnNluResultListener.otherApp(word)
                                    }
                                } else {
                                    mOnNluResultListener.nluError()
                                }
                            }
                        }
                        else -> mOnNluResultListener.nluError()
                    }

                }
                // 通用设置
                NluWords.NUL_INSTRUCTION -> {
                    when(intent) {
                        // 返回
                        NluWords.INTENT_RETURN -> mOnNluResultListener.back()
                        // 返回主页
                        NluWords.INTENT_BACK_HOME -> mOnNluResultListener.home()
                        // 音量加
                        NluWords.INTENT_VOLUME_UP -> mOnNluResultListener.setVolumeUp()
                        // 音量减
                        NluWords.INTENT_VOLUME_DOWN -> mOnNluResultListener.setVolumeDown()
                        // 退出
                        NluWords.INTENT_QUIT -> mOnNluResultListener.quit()
                        else -> mOnNluResultListener.nluError()
                    }
                }
                // 电影
                NluWords.NUL_MOVIE -> {
                    when(intent) {
                        // 音量改变
                        NluWords.INTENT_MOVIE_VOL -> {
                            val userD = slots.optJSONArray("user_d")
                            userD?.let { user ->
                                if(user.length() > 0) {
                                    val word = (user[0] as JSONObject).optString("word")
                                    when (word) {
                                        "大点" -> mOnNluResultListener.setVolumeUp()
                                        "小点" -> mOnNluResultListener.setVolumeDown()
                                        else -> mOnNluResultListener.nluError()
                                    }
                                } else {
                                    mOnNluResultListener.nluError()
                                }
                            }
                        }
                        else -> mOnNluResultListener.nluError()
                    }
                }
                // 机器人
                NluWords.NUL_ROBOT -> {
                    when(intent) {
                        // 音量改变
                        NluWords.INTENT_ROBOT_VOLUME -> {
                            val volumeControl = slots.optJSONArray("user_volume_control")
                            volumeControl?.let { control ->
                                val word = (control[0] as JSONObject).optString("word")
                                when (word) {
                                    "大点" -> mOnNluResultListener.setVolumeUp()
                                    "小点" -> mOnNluResultListener.setVolumeDown()
                                    else -> mOnNluResultListener.nluError()
                                }
                            }
                        }

                        else -> mOnNluResultListener.nluError()
                    }                }
                // 拨打电话
                NluWords.NUL_TELEPHONE -> {
                    when(intent) {
                        NluWords.INTENT_CALL -> {
                            if(slots.has("user_call_target")) {
                                val callTarget = slots.optJSONArray("user_call_target")
                                callTarget?.let { target ->
                                    if(target.length() > 0) {
                                        val name = (target[0] as JSONObject).optString("word")
                                        mOnNluResultListener.callPhoneForName(name)
                                    } else {
                                        mOnNluResultListener.nluError()
                                    }
                                }
                            } else if(slots.has("user_phone_number")) {
                                val phoneNumber = slots.optJSONArray("user_phone_number")
                                phoneNumber?.let { number ->
                                    if(number.length() > 0) {
                                        val phone = (number[0] as JSONObject).optString("word")
                                        mOnNluResultListener.callPhoneForNumber(phone)
                                    } else {
                                        mOnNluResultListener.nluError()
                                    }
                                }
                            } else {
                                mOnNluResultListener.nluError()
                            }
                        }

                        else -> mOnNluResultListener.nluError()
                    }
                }

                NluWords.NLU_WEATHER -> {
                    // 获取其他类型
                    // mOnNluResultListener.queryWeather()
                }

                else -> mOnNluResultListener.nluError()
            }
        }
    }
}