package com.zs.module_developer

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.base.adapter.CommonAdapter
import com.zs.lib_base.base.adapter.CommonViewHolder
import com.zs.lib_base.helper.ARouterHelper
import com.zs.module_developer.data.DeveloperListData

/**
 * 开发者模式
 */
@Route(path = ARouterHelper.PATH_DEVELOPER, description = "开发者模式")
class DeveloperActivity: BaseActivity() {

    // 标题
    private val mTypeTitle = 0
    // 内容
    private val mTypeContent = 1

    private val mList = ArrayList<DeveloperListData>()


    override fun getLayoutId(): Int {
        return R.layout.activity_developer
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_developer)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        initData()
        initListView()
    }

    // 初始化数据
    private fun initData() {
        val dataArr = resources.getStringArray(com.zs.lib_base.R.array.DeveloperListArray)
        dataArr.forEach {
            // 标题
            if(it.contains("[")) {
                addItemData(mTypeTitle, it.replace("[", "").replace("]", ""))
            } else {
                addItemData(mTypeContent, it)
            }
        }
    }

    // 初始化列表
    private fun initListView() {
        // 布局管理器
        var rvDeveloperView: RecyclerView = findViewById(R.id.rvDeveloperView)
        rvDeveloperView.layoutManager = LinearLayoutManager(this)

        // 分割线
        rvDeveloperView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // 适配器
        rvDeveloperView.adapter = CommonAdapter(mList, object :CommonAdapter.OnMoreBindDataListener<DeveloperListData> {
            override fun onBindViewHolder(
                model: DeveloperListData,
                viewHolder: CommonViewHolder,
                type: Int,
                position: Int
            ) {
                when(model.type) {
                    mTypeTitle -> viewHolder.setText(R.id.mTvDeveloperTitle, model.text)
                    mTypeContent -> {
                        viewHolder.setText(R.id.mTvDeveloperContent, "${ position }.${ model.text }")
                        viewHolder.itemView.setOnClickListener() {
                            itemClickFun(position)
                        }
                    }
                }
            }

            override fun getLayoutId(type: Int): Int {
                return if(type == mTypeTitle) {
                    return R.layout.layout_developer_title
                } else {
                    return R.layout.layout_developer_content
                }
            }

            override fun getItemViewType(position: Int): Int {
                return mList[position].type
            }
        })
    }

    // 添加数据
    private fun addItemData(type: Int, text: String) {
        mList.add(DeveloperListData(type, text))
    }

    // 点击事件
    private fun itemClickFun(position: Int) {
        when(position) {
            1 -> ARouterHelper.startActivity(ARouterHelper.PATH_APP_MANAGER)
            2 -> ARouterHelper.startActivity(ARouterHelper.PATH_CONSTELLATION)
            3 -> ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
            4 -> ARouterHelper.startActivity(ARouterHelper.PATH_MAP)
            5 -> ARouterHelper.startActivity(ARouterHelper.PATH_SETTING)
            6 -> ARouterHelper.startActivity(ARouterHelper.PATH_VOICE_SETTING)
            7 -> ARouterHelper.startActivity(ARouterHelper.PATH_WEATHER)
        }
    }
}