package com.zs.module_constellation.fragment

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.zs.lib_base.base.BaseFragment
import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.MonthData
import com.zs.module_constellation.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 本月星座运势
 */
class MonthFragment(val name: String) : BaseFragment() {

    private lateinit var mView: View

    override fun getLayoutId(): Int {
        return R.layout.fragment_month
    }

    override fun initView(view: View) {
        mView = view

        loadMonthData()
    }

    // 加载月运势
    private fun loadMonthData() {
        HttpManager.queryMonthConsTell(name, object : Callback<MonthData> {
            override fun onResponse(call: Call<MonthData>, response: Response<MonthData>) {
                val data = response.body()
                data?.let {
                    val tvName = mView.findViewById<TextView>(R.id.tvName)
                    tvName.text = "${it.name}"
                    val tvDate = mView.findViewById<TextView>(R.id.tvDate)
                    tvDate.text = "${it.date}"
                    val tvMonth = mView.findViewById<TextView>(R.id.tvMonth)
                    tvMonth.text = "${it.month}月"
                    val tvHealth = mView.findViewById<TextView>(R.id.tvHealth)
                    tvHealth.text = "${it.health}"
                    val tvLove = mView.findViewById<TextView>(R.id.tvLove)
                    tvLove.text = "${it.love}"
                    val tvMoney = mView.findViewById<TextView>(R.id.tvMoney)
                    tvMoney.text = "${it.money}"
                    val tvWork = mView.findViewById<TextView>(R.id.tvWork)
                    tvWork.text = "${it.work}"
                    val tvHappyMagic = mView.findViewById<TextView>(R.id.tvHappyMagic)
                    tvHappyMagic.text = "${it.happyMagic}"
                    val tvAll = mView.findViewById<TextView>(R.id.tvAll)
                    tvAll.text = "${it.all}"
                }
            }

            override fun onFailure(call: Call<MonthData>, t: Throwable) {
                Toast.makeText(activity, "加载${ name }月运势失败", Toast.LENGTH_LONG).show()
            }
        })
    }


}