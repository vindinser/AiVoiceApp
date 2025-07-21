package com.zs.module_weather.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.base.adapter.CommonAdapter
import com.zs.lib_base.base.adapter.CommonViewHolder
import com.zs.lib_base.utils.AssetsUtil
import com.zs.module_weather.R
import com.zs.module_weather.bean.CitySelectBean

/**
 * 城市选择
 *
 * 1、加载assets数据
 * 2、实现热门的头部
 * 3、实现列表（多布局）
 * 4、自定义导航view
 * 5、双向的数据通信
 * 6、双向的联动
 */
class CitySelectActivity: BaseActivity() {

    private val mList = ArrayList<CitySelectBean>()

    private lateinit var mCitySelectAdopter: CommonAdapter<CitySelectBean>

    private val mListTitle = ArrayList<String>()

    /**
     * 布局类型
     */
    // 标题
    private val mTypeTitle = 1000
    // 热门城市
    private val mTypeHotCity = 1001
    // 内容
    private val mTypeContent = 1002

    override fun getLayoutId(): Int {
        return R.layout.activity_city_select
    }

    override fun getTitleText(): String {
        return "选择城市"
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

        parsingData()

        initListView()
    }

    private fun initListView() {
        val mCityListView = findViewById<RecyclerView>(R.id.mCityListView)
        mCityListView.layoutManager = LinearLayoutManager(this)

        // 分割线
        mCityListView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        mCitySelectAdopter = CommonAdapter(mList, object : CommonAdapter.OnMoreBindDataListener<CitySelectBean> {

            override fun onBindViewHolder(
                model: CitySelectBean,
                viewHolder: CommonViewHolder,
                type: Int,
                position: Int
            ) {
                when(model.type) {
                    mTypeTitle -> viewHolder.setText(R.id.mTvCityTitle, model.title)
                    mTypeHotCity -> {
                        val mCityHotView = viewHolder.getView(R.id.mCityHotView) as RecyclerView
                        setHotCityView(mCityHotView)
                    }
                    mTypeContent -> {
                        viewHolder.setText(R.id.mTvCityContent, model.content)
                        viewHolder.itemView.setOnClickListener {
                            finishResult(model.city)
                        }
                    }
                }
            }

            override fun getItemViewType(position: Int): Int {
                return mList[position].type
            }

            override fun getLayoutId(type: Int): Int {
                return when(type) {
                    mTypeTitle -> R.layout.layout_city_title
                    mTypeHotCity -> R.layout.layout_city_hot
                    mTypeContent -> R.layout.layout_city_content
                    else -> R.layout.layout_city_hot
                }
            }
        })

        mCityListView.adapter = mCitySelectAdopter
    }

    // 设置热门城市
    private fun setHotCityView(mCityHotView: RecyclerView) {
        val cityList = resources.getStringArray(R.array.HotCityArray)

        mCityHotView.layoutManager = GridLayoutManager(this, 3)
        mCityHotView.adapter = CommonAdapter<String>(cityList.toList(), object : CommonAdapter.OnBindDataListener<String> {
            override fun onBindViewHolder(
                model: String,
                viewHolder: CommonViewHolder,
                type: Int,
                position: Int
            ) {
                viewHolder.setText(R.id.mTvHotCity, model)
                viewHolder.itemView.setOnClickListener {
                    finishResult(model)
                }
            }

            override fun getLayoutId(type: Int): Int {
                return R.layout.layout_hot_city_item
            }
        })
    }

    // 解析数据
    private fun parsingData() {
        addTitle("热门")
        addHot()

        val cities = AssetsUtil.getCity()
        cities.result.forEach {
            it.apply {
                if(!mListTitle.contains(province)) {
                    addTitle(province)
                }

                addContent("${ city }${ district }", district)
            }
        }
    }

    // 添加标题
    private fun addTitle(title: String) {
        val data = CitySelectBean(mTypeTitle, title, "", "")
        mList.add(data)
        mListTitle.add(title)
    }

    // 添加内容
    private fun addContent(content: String, city: String) {
        val data = CitySelectBean(mTypeContent, "", content, city)
        mList.add(data)
    }

    // 添加热门
    private fun addHot() {
        val data = CitySelectBean(mTypeHotCity, "", "", "")
        mList.add(data)
    }

    // 结果回调
    private fun finishResult(city: String) {
        val intent = Intent()
        intent.putExtra("city", city)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}