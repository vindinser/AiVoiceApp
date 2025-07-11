package com.zs.module_constellation

import android.graphics.Color
import android.text.TextUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper
import com.zs.module_constellation.fragment.MonthFragment
import com.zs.module_constellation.fragment.ToDayFragment
import com.zs.module_constellation.fragment.WeekFragment
import com.zs.module_constellation.fragment.YearFragment

/**
 * 星座
 */
@Route(path = ARouterHelper.PATH_CONSTELLATION, description = "星座")
class ConstellationActivity: BaseActivity() {

    private lateinit var mTodayFragment: ToDayFragment
    private lateinit var mTomorrowFragment: ToDayFragment
    private lateinit var mWeekFragment: WeekFragment
    private lateinit var mMonthFragment: MonthFragment
    private lateinit var mYearFragment: YearFragment

    private val mListFragment = ArrayList<Fragment>()

    private lateinit var mViewPager: ViewPager

    private lateinit var mTvToday: TextView
    private lateinit var mTvTomorrow: TextView
    private lateinit var mTvWeek: TextView
    private lateinit var mTvMonth: TextView
    private lateinit var mTvYear: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_constellation
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_constellation)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        // View 控制
        initBtn()

        val name = intent.getStringExtra("name")
        if(!TextUtils.isEmpty(name) && name != null) {
            // 语音识别进入，携带参数
            initFragment(name)
        } else {
            // 首页点击进入
            initFragment("双子座")
        }
    }

    // 初始化按钮
    private fun initBtn() {
        mTvToday = findViewById<TextView>(R.id.mTvToday)
        mTvToday.setOnClickListener{
            tabChange(true, 0)
        }
        mTvTomorrow = findViewById<TextView>(R.id.mTvTomorrow)
        mTvTomorrow.setOnClickListener{
            tabChange(true, 1)
        }
        mTvWeek = findViewById<TextView>(R.id.mTvWeek)
        mTvWeek.setOnClickListener{
            tabChange(true, 2)
        }
        mTvMonth = findViewById<TextView>(R.id.mTvMonth)
        mTvMonth.setOnClickListener{
            tabChange(true, 3)
        }
        mTvYear = findViewById<TextView>(R.id.mTvYear)
        mTvYear.setOnClickListener{
            tabChange(true, 4)
        }
    }

    // ViewPage + Fragment 实现滑动切换页面
    private fun initFragment(name: String) {

        // 设置标题
        supportActionBar?.title = name

        mTodayFragment = ToDayFragment(true, name)
        mTomorrowFragment = ToDayFragment(false, name)
        mWeekFragment = WeekFragment(name)
        mMonthFragment = MonthFragment(name)
        mYearFragment = YearFragment(name)

        mListFragment.add(mTodayFragment)
        mListFragment.add(mTomorrowFragment)
        mListFragment.add(mWeekFragment)
        mListFragment.add(mMonthFragment)
        mListFragment.add(mYearFragment)

        // 初始化页面
        initViewPager()
    }

    private fun initViewPager() {
        mViewPager = findViewById<ViewPager>(R.id.mViewPager)
        mViewPager.adapter = PageFragmentAdapter(supportFragmentManager)
        mViewPager.offscreenPageLimit = mListFragment.size
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                tabChange(false, position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        // 待全部初始化以后 进行UI控制
        tabChange(true, 0)
    }

    // 适配器
    inner class PageFragmentAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int {
            return mListFragment.size
        }

        override fun getItem(position: Int): Fragment {
            return mListFragment[position]
        }

    }

    // 切换选项卡
    private fun tabChange(isClick: Boolean, index: Int) {
        if(isClick) {
            mViewPager.currentItem = index
        }

        mTvToday.setTextColor(if(index == 0) Color.RED else Color.BLACK)
        mTvTomorrow.setTextColor(if(index == 1) Color.RED else Color.BLACK)
        mTvWeek.setTextColor(if(index == 2) Color.RED else Color.BLACK)
        mTvMonth.setTextColor(if(index == 3) Color.RED else Color.BLACK)
        mTvYear.setTextColor(if(index == 4) Color.RED else Color.BLACK)
    }
}