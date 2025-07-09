package com.zs.lib_voice.words

import android.content.Context
import com.zs.lib_voice.R
import kotlin.random.Random

/**
 * 词条工具
 */
object WordsTools {

    // 唤醒词条
    private lateinit var wakeupArray: Array<String>
    // 无法应答词条
    private lateinit var noAnswerArray: Array<String>
    // 暂不支持功能
    private lateinit var noSupportArray: Array<String>

    // 初始化词条工具
    fun initWordsTools(mContext: Context) {
        mContext.apply {
            wakeupArray = resources.getStringArray(R.array.WakeUpListArray)
            noAnswerArray = resources.getStringArray(R.array.NoAnswerListArray)
            noSupportArray = resources.getStringArray(R.array.NoSupportListArray)
        }
    }

    // 返回数组随机项
    private fun radomArray(array: Array<String>): String {
      return array[Random.nextInt(array.size)]
    }

    // 随机返回唤醒词条
    fun wakeUpWords(): String {
        return radomArray(wakeupArray)
    }

    // 随机返回无法应答词条
    fun noAnswerWords(): String {
        return radomArray(noAnswerArray)
    }

    // 随机返回暂不支持功能词条
    fun noSupportWords(): String {
        return radomArray(noSupportArray)
    }

    /**
     * 对给定的证书进行随机值
     */
    fun radomInt(maxSize: Int): Int {
        return Random.nextInt(maxSize)
    }
}