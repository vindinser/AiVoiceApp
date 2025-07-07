package com.zs.lib_base.helper.`fun`

import android.annotation.SuppressLint
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import com.zs.lib_base.utils.L

/**
 * 通用设置帮助类
 */
@SuppressLint("StaticFieldLeak")
object CommonSettingHelper {

    private lateinit var mContext: Context

    // 按键类
    private lateinit var inst: Instrumentation

    fun init(mContext: Context) {
        this.mContext = mContext

        // 模拟按键
        inst = Instrumentation()
    }

    // 返回
    fun back() {
        Thread { inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK) }.start()
    }

    // 返回主页
    fun home() {
        // Thread { inst.sendKeyDownUpSync(KeyEvent.KEYCODE_HOME) }.start()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addCategory(Intent.CATEGORY_HOME)
        mContext.startActivity(intent)
    }

    // 音量+
    fun setVolumeUp() {
        Thread { inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_UP) }.start()
    }

    // 音量-
    fun setVolumeDown() {
        Thread { inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN) }.start()
    }

    // 退出应用
    fun quit() {
        L.i("退出应用")
        // inst.sendKeyDownUpSync(KeyEvent.KEYCODE_)
    }
}