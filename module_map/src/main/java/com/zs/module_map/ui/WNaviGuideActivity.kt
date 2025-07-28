package com.zs.module_map.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.therouter.router.Route
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_voice.manager.VoiceManager


/**
 * 线路导航
 */
// @Route(path = ARouterHelper.PATH_MAP_NAVI, description = "地图导航")
class WNaviGuideActivity : Activity() {

    /*private lateinit var mNaviHelper: WalkNavigateHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 获取WalkNavigateHelper实例
        mNaviHelper = WalkNavigateHelper.getInstance()

        // 获取诱导页面地图展示View
        val view: View = mNaviHelper.onCreate(this@WNaviGuideActivity)
        setContentView(view)
        mNaviHelper.startWalkNavi(this@WNaviGuideActivity)

        mNaviHelper.setTTsPlayer { text, _ ->
            VoiceManager.ttsStart(text)
        }

        mNaviHelper.setWalkNaviStatusListener(object : IWNaviStatusListener {
            /**
             * 普通步行导航模式和步行AR导航模式的切换
             * @param i 导航模式
             * @param walkNaviModeSwitchListener 步行导航模式切换的监听器
             */
            override fun onWalkNaviModeChange(mode: Int, walkNaviModeSwitchListener: WalkNaviModeSwitchListener) {
                mNaviHelper.switchWalkNaviMode(this@WNaviGuideActivity, mode, walkNaviModeSwitchListener);
            }

            override fun  onNaviExit() {

            }
        })

    }

    override fun onResume() {
        super.onResume()
        mNaviHelper.resume()
    }

    override fun onPause() {
        super.onPause()
        mNaviHelper.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mNaviHelper.quit()
    }*/
}