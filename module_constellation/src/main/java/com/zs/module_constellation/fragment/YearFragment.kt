package com.zs.module_constellation.fragment

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.zs.lib_base.base.BaseFragment
import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.YearData
import com.zs.module_constellation.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 本年星座运势
 */
class YearFragment(val name: String) : BaseFragment() {

    private lateinit var mView: View

    override fun getLayoutId(): Int {
        return R.layout.fragment_year
    }

    override fun initView(view: View) {
        mView = view

        loadYearData()
    }

    // 加载年运势
    private fun loadYearData() {
        HttpManager.queryYearConsTell(name, object : Callback<YearData> {
            override fun onResponse(call: Call<YearData>, response: Response<YearData>) {
                val data = response.body()
                data?.let {
                    val tvName = mView.findViewById<TextView>(R.id.tvName)
                    tvName.text = "${it.name}"
                    val tvDate = mView.findViewById<TextView>(R.id.tvDate)
                    tvDate.text = "${it.date}"
                    val tvHealth = mView.findViewById<TextView>(R.id.tvHealth)
                    tvHealth.text = "${it.health[0]}"
                    val tvLove = mView.findViewById<TextView>(R.id.tvLove)
                    tvLove.text = "${it.love[0]}"
                    val tvFinance = mView.findViewById<TextView>(R.id.tvFinance)
                    tvFinance.text = "${it.finance[0]}"
                    val tvCareer = mView.findViewById<TextView>(R.id.tvCareer)
                    tvCareer.text = "${it.career[0]}"
                    val tvFuture = mView.findViewById<TextView>(R.id.tvFuture)
                    tvFuture.text = "${it.future}"
                    val tvLuckeyStone = mView.findViewById<TextView>(R.id.tvLuckeyStone)
                    tvLuckeyStone.text = "运气：${it.luckyStone}"
                    val tvMima = mView.findViewById<TextView>(R.id.tvMima)
                    tvMima.text = "年度总结：${it.mima.text[0]}"
                    val tvInfo = mView.findViewById<TextView>(R.id.tvInfo)
                    tvInfo.text = "${it.mima.info}"
                }
            }

            override fun onFailure(call: Call<YearData>, t: Throwable) {
                Toast.makeText(activity, "加载${ name }年运势失败", Toast.LENGTH_LONG).show()
            }
        })
    }
}