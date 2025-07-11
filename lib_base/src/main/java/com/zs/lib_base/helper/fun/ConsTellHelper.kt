package com.zs.lib_base.helper.`fun`

import android.content.Context
import com.zs.lib_base.R

/**
 * 星座帮助类
 */
object ConsTellHelper {

    private lateinit var mNameArray: Array<String>
    private lateinit var mTimeArray: Array<String>

    fun init(mContext: Context) {
        mNameArray = mContext.resources.getStringArray(R.array.ConsTellArray)
        mTimeArray = mContext.resources.getStringArray(R.array.ConsTellTimeArray)
    }

    // 根据传递进来的星座名称反馈时间
    fun getConsTellTime(consTellName: String): String {
        mNameArray.forEachIndexed() { index, s ->
            if(s == consTellName) {
                return mTimeArray[index]
            }
        }
        return "查询不到结果"
    }
}