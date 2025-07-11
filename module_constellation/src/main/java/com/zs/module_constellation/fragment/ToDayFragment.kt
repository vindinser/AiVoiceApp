package com.zs.module_constellation.fragment

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.zs.lib_base.base.BaseFragment
import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.TodayData
import com.zs.module_constellation.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 今日/明日 星座运势
 */
class ToDayFragment(private val isToday: Boolean, val name: String) : BaseFragment(), Callback<TodayData> {

    private lateinit var mView: View

    override fun getLayoutId(): Int {
        return R.layout.fragment_today
    }

    override fun initView(view: View) {
        mView = view

        if(isToday) {
            loadToday()
        } else {
            loadTomorrow()
        }
    }

    // 加载今日运势
    private fun loadToday() {
        HttpManager.queryTodayConsTell(name, this)
    }

    // 加载明日运势
    private fun loadTomorrow() {
        HttpManager.queryTomorrowConsTell(name, this)
    }

    // 接口成功
    override fun onResponse(call: Call<TodayData>, response: Response<TodayData>) {
        val data = response.body()
        data?.let {
            val tvName = mView.findViewById<TextView>(R.id.tvName)
            tvName.text = "星座名称：${ it.name }"
            val tvDatetime = mView.findViewById<TextView>(R.id.tvDatetime)
            tvDatetime.text = "当前时间：${ it.datetime }"
            val tvColor = mView.findViewById<TextView>(R.id.tvColor)
            tvColor.text = "幸运颜色：${ it.color }"
            val tvNumber = mView.findViewById<TextView>(R.id.tvNumber)
            tvNumber.text = "幸运颜色：${ it.number }"
            val tvQFriend = mView.findViewById<TextView>(R.id.tvQFriend)
            tvQFriend.text = "速配星座：${ it.QFriend }"
            val tvSummary = mView.findViewById<TextView>(R.id.tvSummary)
            tvSummary.text = "今日概述：${ it.summary }"

            val pbHealth = mView.findViewById<ProgressBar>(R.id.pbHealth)
            pbHealth.progress = it.health.toInt()
            val pbLove = mView.findViewById<ProgressBar>(R.id.pbLove)
            pbLove.progress = it.love.toInt()
            val pbMoney = mView.findViewById<ProgressBar>(R.id.pbMoney)
            pbMoney.progress = it.money.toInt()
            val pbWork = mView.findViewById<ProgressBar>(R.id.pbWork)
            pbWork.progress = it.work.toInt()
            val pbAll = mView.findViewById<ProgressBar>(R.id.pbAll)
            // pbAll.progress = it.all.substring(0, it.all.length - 1).toInt()
            pbAll.progress = it.all.toInt()
        }
    }

    // 接口失败
    override fun onFailure(call: Call<TodayData>, t: Throwable) {
        Toast.makeText(activity, "加载${ name }运势失败", Toast.LENGTH_LONG).show()
    }
}