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
        if(nluResultLength <= 0) {
            return
        } else if(nluResultLength == 1) {
            // 单条
            analyzeNluSingle(result[0] as JSONObject)
        } else {
            // 多条
        }
    }

    // 单条解析
    private fun analyzeNluSingle(result: JSONObject) {
        val domain = result.optString("domain")
        val intent = result.optString("intent")
        val slots = result.optJSONObject("slots")

        when(domain) {
            NluWords.NLU_WEATHER -> {
                // 获取其他类型
                // mOnNluResultListener.queryWeather()
            }
        }
    }
}