package com.zs.lib_network.bean

/**
 * 笑话列表
 */
data class JokeListData(
    val error_code: Int,
    val reason: String,
    val result: Results
)

data class Results(
    val `data`: List<Results>
)

data class Data(
    val content: String,
    val hashId: String,
    val unixtime: Int,
    val updatetime: String
)