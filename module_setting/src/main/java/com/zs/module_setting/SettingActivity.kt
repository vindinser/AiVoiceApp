package com.zs.module_setting

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.base.adapter.CommonAdapter
import com.zs.lib_base.base.adapter.CommonViewHolder
import com.zs.lib_base.helper.ARouterHelper

/**
 * 设置
 */
@Route(path = ARouterHelper.PATH_SETTING, description = "设置")
class SettingActivity: BaseActivity() {

    private lateinit var mList: ArrayList<String>

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_system_setting)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

        mList = ArrayList()

        for(i in 1..30) {
            mList.add("测试适配器：第${ i }个")
        }

        var mRecyclerView: RecyclerView = findViewById(R.id.mRecyclerView)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = CommonAdapter(mList, object :CommonAdapter.OnBindDataListener<String> {
            override fun onBindViewHolder(
                model: String,
                viewHolder: CommonViewHolder,
                type: Int,
                position: Int
            ) {
                viewHolder.setText(R.id.textView, model)
            }

            override fun getLayoutId(type: Int): Int {
                return R.layout.text_item
            }

        })
    }
}