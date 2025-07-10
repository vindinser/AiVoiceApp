package com.zs.module_joke

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.base.adapter.CommonAdapter
import com.zs.lib_base.base.adapter.CommonViewHolder
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.Data
import com.zs.lib_network.bean.JokeListData
import com.zs.lib_voice.manager.VoiceManager
import com.zs.lib_voice.tts.VoiceTTS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 笑话
 */
@Route(path = ARouterHelper.PATH_JOKE, description = "笑话")
class JokeActivity: BaseActivity(), OnRefreshListener, OnRefreshLoadMoreListener {

    private lateinit var refreshLayout: SmartRefreshLayout

    // 页码
    private var currentPage: Int = 1

    // RecyclerView 数据源
    private val mList = ArrayList<Data>()
    // 适配器
    private lateinit var mJokeAdapter: CommonAdapter<Data>

    // 当前播放下标
    private var currentPlayIndex = -1

    override fun getLayoutId(): Int {
        return R.layout.activity_app_joke
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_joke)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        refreshLayout = findViewById(R.id.refreshLayout)
        // 自动刷新
        // refreshLayout.autoRefresh()

        initList()

        loadData(0)
    }

    /**
     * 加载数据
     * @param {Int} orientation 方向 0：下拉 1：上拉
     */
    private fun loadData(orientation: Int) {
        HttpManager.queryJokeList(currentPage, object : Callback<JokeListData> {

            override fun onResponse(call: Call<JokeListData>, response: Response<JokeListData>) {
                if(orientation == 0) {
                    refreshLayout.finishRefresh()
                    // 列表清空
                    mList.clear()
                    response.body()?.result?.data?.let { mList.addAll(it) }
                    // 适配器全部刷新
                    mJokeAdapter.notifyDataSetChanged()
                } else if(orientation == 1) {
                    refreshLayout.finishLoadMore()
                    // 追加在尾部
                    response.body()?.result?.data?.let {
                        val positionStart: Int = mList.size
                        mList.addAll(it)
                        // 适配器局部刷新
                        mJokeAdapter.notifyItemRangeInserted(positionStart, it.size)
                    }
                }
            }

            override fun onFailure(call: Call<JokeListData>, t: Throwable) {
                if(orientation == 0) {
                    refreshLayout.finishRefresh()
                } else if(orientation == 1) {
                    refreshLayout.finishLoadMore()
                }
            }
        })
    }

    // 初始化列表
    private fun initList() {

        // 刷新组件
        refreshLayout.setRefreshHeader(ClassicsHeader(this))
        refreshLayout.setRefreshFooter(ClassicsFooter(this))
        // 监听
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnRefreshLoadMoreListener(this)

        var mJokeRecyclerView: RecyclerView = findViewById(R.id.mJokeRecyclerView)
        mJokeRecyclerView.layoutManager = LinearLayoutManager(this)
        mJokeAdapter = CommonAdapter(mList, object: CommonAdapter.OnBindDataListener<Data> {
            override fun onBindViewHolder(
                model: Data,
                viewHolder: CommonViewHolder,
                type: Int,
                position: Int
            ) {
                // 设置内容
                viewHolder.setText(R.id.tvContent, model.content)

                // 播放按钮
                val tvPlayer = viewHolder.getView(R.id.tvPlayer) as TextView
                tvPlayer.text = if(currentPlayIndex == position) "暂停" else "播放"

                // 设置点击事件
                tvPlayer.setOnClickListener {
                    if(currentPlayIndex == position) {
                        // 暂停
                        VoiceManager.ttsPause()
                        currentPlayIndex = -1
                        // 局部刷新
                        // mJokeAdapter.notifyItemChanged(position)
                        tvPlayer.text = "播放"
                    } else {
                        // 播放
                        currentPlayIndex = position
                        tvPlayer.text = "暂停"
                        VoiceManager.ttsStart(model.content, object : VoiceTTS.OnTTSResultListener {
                            override fun ttsEnd() {
                                currentPlayIndex = -1
                                tvPlayer.text = "播放"
                            }
                        })
                    }
                }
            }

            override fun getLayoutId(type: Int): Int {
                return R.layout.layout_joke_list_item
            }
        })
        mJokeRecyclerView.adapter = mJokeAdapter
    }

    // 监听头部刷新
    override fun onRefresh(refreshLayout: RefreshLayout) {
        currentPage = 1
        loadData(0)
    }

    // 监听尾部刷新
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        currentPage ++
        loadData(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        VoiceManager.ttsPause()
    }
}