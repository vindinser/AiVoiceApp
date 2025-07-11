package com.zs.module_constellation.fragment

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.zs.lib_base.base.BaseFragment
import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.WeekData
import com.zs.module_constellation.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 本周星座运势
 */
class WeekFragment(val name: String) : BaseFragment() {

    private lateinit var mView: View

    override fun getLayoutId(): Int {
        return R.layout.fragment_week
    }

    override fun initView(view: View) {
        mView = view

        loadWeekData()
    }

    // 加载本周运势
    private fun loadWeekData() {
        HttpManager.queryWeekConsTell(name, object : Callback<WeekData> {

            override fun onResponse(call: Call<WeekData>, response: Response<WeekData>) {
                val data = response.body()
                data?.let {
                    val tvName = mView.findViewById<TextView>(R.id.tvName)
                    tvName.text = "${it.name}"
                    val tvDate = mView.findViewById<TextView>(R.id.tvDate)
                    tvDate.text = "${it.date}"
                    val tvWeekth = mView.findViewById<TextView>(R.id.tvWeekth)
                    tvWeekth.text = "第${it.weekth}周"
                    val tvHealth = mView.findViewById<TextView>(R.id.tvHealth)
                    tvHealth.text = "健康：${it.health}"
                    val tvJob = mView.findViewById<TextView>(R.id.tvJob)
                    tvJob.text = "学业：${it.job}"
                    val tvLove = mView.findViewById<TextView>(R.id.tvLove)
                    tvLove.text = "爱情：${it.love}"
                    val tvMoney = mView.findViewById<TextView>(R.id.tvMoney)
                    tvMoney.text = "财富：${it.money}"
                    val tvWork = mView.findViewById<TextView>(R.id.tvWork)
                    tvWork.text = "工作：${it.work}"
                }
            }

            override fun onFailure(call: Call<WeekData>, t: Throwable) {
                Toast.makeText(activity, "加载${ name }周运势失败", Toast.LENGTH_LONG).show()
            }
        })
    }
}