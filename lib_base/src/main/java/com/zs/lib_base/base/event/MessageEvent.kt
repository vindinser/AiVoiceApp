package com.zs.lib_base.base.event

/**
 * 事件对象
 */
class MessageEvent(val type: Int) {
    var stringVal: String = ""
    var intValue: Int = 0
    var booleanVal: Boolean = false
}