package com.zs.aivoiceapp

import android.Manifest
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.zs.aivoiceapp.converter.ScaleInTransformer
import com.zs.aivoiceapp.data.MainListData
import com.zs.aivoiceapp.service.VoiceService
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.base.adapter.BasePagerAdapter
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.helper.`fun`.AppHelper
import com.zs.lib_base.helper.`fun`.ContactHelper

class AppMainActivity : BaseActivity() {

    // 权限
    private val requiredPermissions = arrayOf(
        // 麦克风
        Manifest.permission.RECORD_AUDIO,
        // 打电话
        Manifest.permission.CALL_PHONE,
        // 读取联系人
        Manifest.permission.READ_CONTACTS,
        // 网络定位
        Manifest.permission.ACCESS_COARSE_LOCATION,
        // GPS定位
        Manifest.permission.ACCESS_FINE_LOCATION,
        // 振动器（用于导航过程中的震动提示）
        Manifest.permission.VIBRATE,
        // 摄像头（于AR步行导航）
        Manifest.permission.CAMERA
    )

    private val mList = ArrayList<MainListData>()
    private val mListView = ArrayList<View>()

    // 获取布局ID
    override fun getLayoutId(): Int = R.layout.app_main

    // 获取标题
    override fun getTitleText(): String = getString(com.zs.lib_base.R.string.app_name)

    // 是否显示返回键
    override fun isShowBack(): Boolean = false

    // 初始化
    override fun initView() {
        /* 使用 AndPermission 获取权限
        if(checkPermission(Manifest.permission.RECORD_AUDIO)) {
            startServices()
        } else {
            requestPermissionsAndPermission(requiredPermissions, object : Auction<List<String>> { startServices })
        }
        */

        // 检查权限
        if(hasRequiredPermissions(requiredPermissions)) {
            startServices()
        } else {
            requestPermissions(requiredPermissions)
        }

        // 窗口权限
        if(!checkWindowPermission()) {
            requestWindowPermission()
        }

        // 初始化数据
        initPagerData()
        // 初始化view
        initPageView()
    }

    // 权限申请通过后启动服务
    override fun permissionsAllowed() {
        startServices()
    }

    // 启动服务
    private fun startServices() {

        // 读取联系人
        ContactHelper.init(this)

        // 启动语音服务
        startService(Intent(this, VoiceService::class.java))

        // 跳转开发者模式
        // ARouterHelper.startActivity(ARouterHelper.PATH_DEVELOPER)
    }

    // 初始化数据
    private fun initPagerData() {
        val title = resources.getStringArray(R.array.MainTitleArray)
        val color = resources.getIntArray(R.array.MainColorArray)
        val icon = resources.obtainTypedArray(R.array.MainIconArray)

        for((index, value) in title.withIndex()) {
            mList.add(MainListData(value, color[index], icon.getResourceId(index, 0)))
        }

        val windowHeight = windowManager.defaultDisplay.height

        mList.forEach {
            val view = View.inflate(this, R.layout.main_list, null)

            val mCvMainView = view.findViewById<CardView>(R.id.mCvMainView)
            val mTvMainIcon = view.findViewById<ImageView>(R.id.mTvMainIcon)
            val mTvMainText = view.findViewById<TextView>(R.id.mTvMainText)

            mCvMainView.setCardBackgroundColor(it.color)
            mTvMainIcon.setImageResource(it.icon)
            mTvMainText.text = it.title

            mCvMainView.layoutParams?.let { lp ->
                lp.height = windowHeight / 5 * 3
            }

            // 设置点击事件
            view.setOnClickListener { _ ->
                when(it.icon) {
                    R.drawable.img_main_weather -> ARouterHelper.startActivity(ARouterHelper.PATH_WEATHER)
                    R.drawable.img_main_constellation -> ARouterHelper.startActivity(ARouterHelper.PATH_CONSTELLATION)
                    R.drawable.img_main_joke -> ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
                    R.drawable.img_main_map -> ARouterHelper.startActivity(ARouterHelper.PATH_MAP)
                    R.drawable.img_main_app_manager -> ARouterHelper.startActivity(ARouterHelper.PATH_APP_MANAGER)
                    R.drawable.img_main_voice_setting -> ARouterHelper.startActivity(ARouterHelper.PATH_VOICE_SETTING)
                    R.drawable.img_main_system_setting -> ARouterHelper.startActivity(ARouterHelper.PATH_SETTING)
                    R.drawable.img_main_developer -> ARouterHelper.startActivity(ARouterHelper.PATH_DEVELOPER)
                }
            }

            mListView.add(view)
        }
    }

    // 初始化view
    private fun initPageView() {
        var mViewPager: ViewPager = findViewById(R.id.mViewPager)
        mViewPager.pageMargin = 20
        mViewPager.offscreenPageLimit = mList.size
        mViewPager.adapter = BasePagerAdapter(mListView)
        // 设置偏移量
        mViewPager.setPageTransformer(true, ScaleInTransformer())
    }
}