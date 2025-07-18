package com.zs.module_weather

import android.graphics.Color
import android.graphics.DashPathEffect
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_network.HttpManager
import com.zs.lib_network.bean.WeatherData
import com.zs.module_weather.tools.WeatherIconTools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 天气
 */
@Route(path = ARouterHelper.PATH_WEATHER, description = "天气")
class WeatherActivity: BaseActivity() {

    private lateinit var mLineChart: LineChart

    private var currentCity = "北京"

    override fun getLayoutId(): Int {
        return R.layout.activity_weather
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_weather)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        intent.run {
            val city = getStringExtra("city")
            if(!TextUtils.isEmpty(city))  {
                currentCity = city!!
            }
            initChart()
            loadWeather(currentCity)
        }
    }


    /**
     * 加载城市天气数据
     * @param { string } city 加载天气城市name
     */
    private fun loadWeather(city: String) {

        // 设置标题
        supportActionBar?.title = city

        HttpManager.run {
            queryWeather(city, object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            // 填充数据
                            it.result.realtime.apply {
                                // 设置天气
                                val mInfo = findViewById<TextView>(R.id.mInfo).apply {
                                    text = info
                                }
                                // 设置图片
                                val mIvWid = findViewById<ImageView>(R.id.mIvWid).apply {
                                    setImageResource(WeatherIconTools.getIcon(wid))
                                }
                                // 设置温度
                                findViewById<TextView>(R.id.mTemperature).apply {
                                    text = String.format("%s%s", temperature, getString(R.string.app_weather_t))
                                }
                                // 设置湿度
                                findViewById<TextView>(R.id.mHumidity).apply {
                                    text = String.format("%s\t%s", getString(R.string.app_weather_humidity), humidity)
                                }
                                // 设置风向
                                findViewById<TextView>(R.id.mDirect).apply {
                                    text = String.format("%s\t%s", getString(R.string.app_weather_direct), direct)
                                }
                                // 设置风力
                                findViewById<TextView>(R.id.mSpeed).apply {
                                    text = String.format("%s\t%s", getString(R.string.app_weather_power), power)
                                }
                                // 设置空气质量
                                findViewById<TextView>(R.id.mAirQuality).apply {
                                    text = String.format("%s\t%s", getString(R.string.app_weather_aqi), aqi)
                                }
                            }

                            val data = ArrayList<Entry>()
                            // 绘制图表
                            it.result.future.forEachIndexed{ index, future ->
                                val temp = future.temperature.substring(0, 2)
                                data.add(Entry((index + 1).toFloat(), temp.toFloat()))
                            }
                            setLineChartData(data)
                        }
                    } else {
                        queryWeatherError(city)
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    queryWeatherError(city)
                }
            })
        }
    }

    /**
     * 查询天气失败
     * @param { string } city 加载天气城市name
     */
    private fun queryWeatherError(city: String) {
        Toast.makeText(this@WeatherActivity, "${ city }天气请求失败", Toast.LENGTH_LONG).show()
    }

    // 初始化折线图
    private fun initChart() {
        mLineChart = findViewById<LineChart>(R.id.mLineChart).apply {
            /**
             * ================ 基本配置 ================
             */
            // 后台绘制
            setDrawGridBackground(true)
            // 描述文本
            description.isEnabled = true
            description.text = "数据由聚合数据提供"
            // 触摸手势
            setTouchEnabled(true)
            // 支持缩放
            setScaleEnabled(true)
            // 拖拽
            isDragEnabled = true
            // 扩展缩放
            setPinchZoom(true)

            /**
             * ================ 轴配置 ================
             */

            // 平均线
            val xLimitLine = LimitLine(10f, "标记")
            // 线宽
            xLimitLine.lineWidth = 4f
            // 基础线
            xLimitLine.enableDashedLine(10f, 10f, 0f)
            // 位置（右下角）
            xLimitLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
            // 文本
            xLimitLine.textSize = 10f

            // 分隔线
            xAxis.enableAxisLineDashedLine(10f, 10f, 10f)
            // 最大值
            xAxis.mAxisMaximum = 5f
            // 最小值
            xAxis.mAxisMinimum = 1f

            // 左边的Y轴
            axisLeft.enableAxisLineDashedLine(10f, 10f, 10f)
            axisLeft.mAxisMaximum = 40f
            axisLeft.mAxisMinimum = -20f

            // 禁止右边的Y轴
            axisRight.isEnabled = false
        }
    }

    /**
     * 设置图表数据
     * @param {ArrayList<Entry>} values 图表的数据
     */
    private fun setLineChartData(values: ArrayList<Entry>) {
        mLineChart.apply {
            if(data != null && data.dataSetCount > 0) {
                // 获取数据容器
                val set = data.getDataSetByIndex(0) as LineDataSet
                set.values = values
                data.notifyDataChanged()
                notifyDataSetChanged()
            } else {
                // 没有数据
                val set = LineDataSet(values, "${ currentCity }未来五天温度")
                set.enableDashedLine(10f, 10f, 0f)
                set.setColor(Color.BLACK)
                set.lineWidth = 1f
                // 圆角
                set.circleRadius = 3f
                // 是否是空心的圆
                set.setDrawCircleHole(false)
                set.valueTextSize = 10f
                set.formLineWidth = 1f
                set.setDrawFilled(true)

                set.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
                set.formSize = 15f

                set.fillColor = Color.YELLOW

                // 设置数据
                val dataSet = ArrayList<LineDataSet>()
                dataSet.add(set)
                data = LineData(dataSet as List<ILineDataSet>?)
            }

            /**
             * ================ UI配置 ================
             */
            // X轴动画
            animateX(2000)
            // 刷新
            invalidate()
            // 页眉
            legend.form = Legend.LegendForm.LINE
        }
    }
}