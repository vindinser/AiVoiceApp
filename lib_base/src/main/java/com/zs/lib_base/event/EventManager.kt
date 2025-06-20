package com.zs.lib_base.event

import org.greenrobot.eventbus.EventBus
import java.util.Objects

/**
 * EventBus 管理
 */
object EventManager {

    // 注册
    fun register(subscriber:Any) {
        EventBus.getDefault().register(subscriber);
    }

    // 解绑
    fun unregister(subscriber:Any) {
        EventBus.getDefault().unregister(subscriber);
    }

    // 发送事件类
    private fun post(event: MessageEvent) {
        EventBus.getDefault().post(event)
    }

    // 发送事件
    fun post(type: Int) {
        post(MessageEvent(type))
    }

    // 发送类型携带String
    fun post(type: Int, string: String) {
        val event = MessageEvent(type)
        event.stringVal = string
        post(MessageEvent(type))
    }

    // 发送类型携带int
    fun post(type: Int, int: Int) {
        val event = MessageEvent(type)
        event.intValue = int
        post(MessageEvent(type))
    }

    // 发送类型携带boolean
    fun post(type: Int, boolean: Boolean) {
        val event = MessageEvent(type)
        event.booleanVal = boolean
        post(MessageEvent(type))
    }
}