package com.zs.lib_network.bean

// 今日/明日运势
data class TodayData(
    val QFriend: String,       // 速配星座
    val all: String,           // 综合指数
    val color: String,         // 幸运色
    val date: Long,            // 日期数字 (格式：20140627)
    val datetime: String,      // 日期字符串 (格式：2014年06月27日)
    val error_code: Int,       // 返回码
    val health: String,        // 健康指数
    val love: String,          // 爱情指数
    val money: String,         // 财运指数
    val name: String,          // 星座名称
    val number: Int,           // 幸运数字
    val resultcode: String,    // 幸运数字
    val summary: String,       // 今日概述
    val work: String           // 工作指数
)

// 本周运势
data class WeekData(
    val name: String,          // 星座名称
    val date: String,          // 日期范围 (格式：2014年06月29日-2014年07月05日)
    val weekth: Int,           // 周数
    val health: String,        // 健康运势
    val job: String,           // 工作运势
    val love: String,          // 爱情运势
    val money: String,         // 财运运势
    val work: String,          // 工作运势(与job重复？保留两者)
    val resultcode: String,    // 结果码
    val error_code: Int        // 返回码
)

// 本月运势
data class MonthData(
    val name: String,          // 星座名称
    val date: String,          // 日期 (格式：2016年12月)
    val all: String,            // 综合运势
    val happyMagic: String,    // 快乐魔法(示例中为空)
    val health: String,        // 健康运势
    val love: String,          // 爱情运势
    val money: String,         // 财运运势
    val month: Int,            // 月份数字
    val work: String,          // 工作运势
    val resultcode: String,    // 结果码
    val error_code: Int        // 返回码
)

// 年度运势中的密码部分
data class YearMima(
    val info: String,          // 概述
    val text: List<String>     // 说明文本(数组形式)
)

// 本年运势
data class YearData(
    val career: List<String>,  // 事业运(数组)
    val date: String,          // 年份 (格式：2016年)
    val error_code: Int,       // 返回码
    val finance: List<String>, // 财运(数组)
    val future: String,        // 未来运势
    val health: List<String>,  // 健康运势
    val love: List<String>,    // 感情运(数组)
    val luckyStone: String,    // 运(数组)
    val mima: YearMima,        // 年度密码
    val name: String,          // 星座名称
    val resultcode: String,    // 结果码
    val year: Int              // 年份数字
)