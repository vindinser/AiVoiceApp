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
            mOnNluResultListener.aiRobot(rawText)
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
                // 笑话
                NluWords.NUL_JOKE -> {
                    when(intent) {
                        // 讲笑话
                        NluWords.INTENT_TELL_JOKE -> mOnNluResultListener.tellJoke()
                        // 获取笑话列表
                        NluWords.INTENT_SEARCH_JOKE -> mOnNluResultListener.jokeList()

                        else -> mOnNluResultListener.nluError()
                    }
                }
                // 搜索
                NluWords.NUL_SEARCH -> {
                    when(intent) {
                        NluWords.INTENT_SEARCH -> mOnNluResultListener.jokeList()
                        else -> mOnNluResultListener.nluError()
                    }
                }
                // 小说
                NluWords.NUL_NOVEL -> {
                    when(intent) {
                        NluWords.INTENT_SEARCH_NOVEL -> mOnNluResultListener.jokeList()
                        else -> mOnNluResultListener.nluError()
                    }
                }
                // 星座
                NluWords.NUL_CONSTELLATION -> {
                    val consTellNameArray = slots.optJSONArray("user_constell_name")
                    consTellNameArray?.let { consTell ->
                        if(consTell.length() > 0) {
                            val wordObject = consTell[0] as JSONObject
                            val word = wordObject.optString("word")

                            when(intent) {
                                // 查看星座时间
                                NluWords.INTENT_CONSTELLATION_TIME -> mOnNluResultListener.consTellTime(word)
                                // 查看星座信息（运势）
                                NluWords.INTENT_CONSTELLATION_INFO -> mOnNluResultListener.consTellInfo(word)
                                else -> mOnNluResultListener.nluError()
                            }
                        } else {
                            mOnNluResultListener.nluError()
                        }
                    }
                }
                // 天气
                NluWords.NLU_WEATHER -> {
                    val userLocal = slots.optJSONArray("user_local")
                    userLocal?.let { local ->
                        if(local.length() > 0) {
                            val localObj = local[0] as JSONObject
                            val word = localObj.optString("word")
                            when(intent) {
                                NluWords.INTENT_USER_WEATHER -> mOnNluResultListener.queryWeather(word)
                                else -> mOnNluResultListener.queryWeatherInfo(word)
                            }

                        } else {
                            mOnNluResultListener.nluError()
                        }
                    }
                }
                // 地图
                NluWords.NUL_MAP -> {
                    when(intent) {
                        NluWords.INTENT_MAP_ROUTE -> {
                            val navigate = slots.optJSONArray("user_navigate")
                            if(navigate.length() > 0) {
                                (navigate[0] as JSONObject).apply {
                                    if(optString("word") == "导航") {
                                        val routeArrival = slots.optJSONArray("user_route_arrival")
                                        if(routeArrival.length() > 0) {
                                            (routeArrival[0] as JSONObject).apply {
                                                val word = optString("word")
                                                mOnNluResultListener.routeMap(word)
                                            }
                                        } else {
                                            mOnNluResultListener.nluError()
                                        }
                                    }
                                }
                            } else {
                                mOnNluResultListener.nluError()
                            }
                        }
                        NluWords.INTENT_MAP_NEARBY -> {
                            val userTarget = slots.optJSONArray("user_target")
                            if(userTarget.length() > 0) {
                                (userTarget[0] as JSONObject).apply {
                                    val word = optString("word")
                                    mOnNluResultListener.nearByMap(word)
                                }
                            } else {
                                mOnNluResultListener.nluError()
                            }
                        }
                        else -> mOnNluResultListener.nluError()
                    }
                }

                else -> mOnNluResultListener.nluError()
            }
        }
    }
}