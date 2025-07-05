package com.zs.module_app_manager

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.base.adapter.BasePagerAdapter
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.helper.`fun`.AppHelper
import com.zs.lib_base.utils.L
import com.zs.lib_base.view.PointLayoutView

/**
 * 应用管理
 */
@Route(path = ARouterHelper.PATH_APP_MANAGER, description = "应用管理页面")
class AppManagerActivity: BaseActivity() {

    private lateinit var loading: LinearLayout
    private val waitApp = 1000

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if(msg.what == waitApp) {
                wathAppHandler()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_app_manager
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_app_manager)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        var loading: LinearLayout = findViewById(R.id.ll_loading)
        this.loading = loading
        loading.visibility = View.VISIBLE

        // 初始化AppView成功
        wathAppHandler()
    }

    // 等待应用加载
    private fun wathAppHandler() {
        L.i("等待App列表刷新")
        if(AppHelper.mAllViewList.size > 0) {
            initViewPage()
        } else {
            mHandler.sendEmptyMessageDelayed(waitApp, 1000)
        }
    }

    // 初始化页面
    private fun initViewPage() {
        var mViewPage: ViewPager = findViewById(R.id.mViewPage)
        val pageSize = AppHelper.getPageSize()
        mViewPage.offscreenPageLimit = pageSize
        mViewPage.adapter = BasePagerAdapter(AppHelper.mAllViewList)
        loading.visibility = View.GONE

        var lContent: LinearLayout = findViewById(R.id.ll_content)
        lContent.visibility = View.VISIBLE

        var mPointLayoutView: PointLayoutView = findViewById(R.id.mPointLayoutView)
        mPointLayoutView.setPointSize(pageSize)

        mViewPage.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mPointLayoutView.setCheck(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
}
