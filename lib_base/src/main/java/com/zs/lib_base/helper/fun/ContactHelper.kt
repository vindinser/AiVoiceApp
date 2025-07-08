package com.zs.lib_base.helper.`fun`

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import com.zs.lib_base.helper.`fun`.data.ContactData
import androidx.core.net.toUri
import com.zs.lib_base.utils.L

/**
 * 联系人帮助类
 */
@SuppressLint("StaticFieldLeak")
object ContactHelper {

    private lateinit var mContext: Context

    // 联系人列表
    val mContactList = ArrayList<ContactData>()

    // 数据库地址
    private val PHONE_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    // 查询条件 名称 - 号码
    private const val NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
    private const val PHONE = ContactsContract.CommonDataKinds.Phone.NUMBER

    fun init(mContext: Context) {
        this.mContext = mContext

        loadContact()
    }

    // 加载通讯录
    @SuppressLint("Range")
    private fun loadContact() {
        val resolver = mContext.contentResolver
        val cursor = resolver.query(PHONE_URI, arrayOf(NAME, PHONE), null, null, null)
        cursor?.let {
            while(it.moveToNext()) {
                val data = ContactData(
                    it.getString(it.getColumnIndex(NAME)),
                    it.getString(it.getColumnIndex(PHONE)).trim()
                )
                mContactList.add(data)
            }
        }
        L.i("联系人：${ mContactList }")
        cursor?.close()
    }

    // 拨打电话
    fun callPhone(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = "tel:$phone".toUri()
        mContext.startActivity(intent)
    }
}