package com.zs.lib_base.utils

import android.util.Log
import cn.therouter.BuildConfig

/**
 * Log 日志
 */
object L {

    private const val TAG_DEBUG: String = "AiVoiceApp_Debug"
    private const val TAG: String = "AiVoiceApp"

    fun i(text: String?) {
        if(BuildConfig.DEBUG) {
            text?.let {
                Log.i(TAG_DEBUG, it)
            }
        } else {
            text?.let {
                Log.i(TAG, it)
            }
        }
    }

    fun e(text: String?) {
        if(BuildConfig.DEBUG) {
            text?.let {
                Log.e(TAG_DEBUG, it)
            }
        } else {
            text?.let {
                Log.e(TAG, it)
            }
        }
    }

    fun w(text: String?) {
        if(BuildConfig.DEBUG) {
            text?.let {
                Log.w(TAG_DEBUG, it)
            }
        } else {
            text?.let {
                Log.w(TAG, it)
            }
        }
    }

    fun d(text: String?) {
        if(BuildConfig.DEBUG) {
            text?.let {
                Log.d(TAG_DEBUG, it)
            }
        } else {
            text?.let {
                Log.d(TAG, it)
            }
        }
    }
}