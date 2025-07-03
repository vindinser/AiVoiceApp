package com.zs.aivoiceapp.data

/**
 * Kotlin Data 数据
 */

// 主页列表数据
data class MainListData(
    val title: String,
    val color: Int,
    val icon: Int
)

/**
 * 对话窗口数据对象
 * @param {Int} type 会话类型
 * @param {String} text 文本
 */
data class ChatListData(
    val type: Int
) {
    lateinit var text: String
}