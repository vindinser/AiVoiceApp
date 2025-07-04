package com.zs.lib_base.helper

import android.annotation.SuppressLint
import android.content.Context
import android.media.SoundPool

/**
 * 播放铃声
 */
@SuppressLint("StaticFieldLeak")
object SoundPoolHelper {

    private lateinit var mContext: Context
    private lateinit var mSoundPool: SoundPool

    fun init(mContext: Context) {
        this.mContext = mContext

        mSoundPool = SoundPool.Builder().setMaxStreams(1).build()
    }

    // 播放
    fun play(resId: Int) {
        val poolId = mSoundPool.load(mContext, resId, 1)
        mSoundPool.setOnLoadCompleteListener { _, _, status: Int ->
            if(status == 0) {
                /**
                 * int soundID
                 * float leftVolume 左声道 0 - 1
                 * float rightVolume, 右声道
                 * int priority 优先级
                 * int loop 重复数
                 * float rate 速率 0.5 - 2.0
                 */
                mSoundPool.play(poolId, 1f, 1f, 1, 0, 1F)
            }
        }
    }
}