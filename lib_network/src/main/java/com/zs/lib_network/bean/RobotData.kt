package com.zs.lib_network.bean

data class RobotData(
    val intent: IntentData,
    val results: List<ResultData>
) {
    data class IntentData(
        val code: Int,
        val intentName: String,
        val actionName: String,
        val parameters: Map<String, String>
    )

    data class ResultData(
        val groupType: Int,
        val resultType: String,
        val values: Map<String, String>
    )
}