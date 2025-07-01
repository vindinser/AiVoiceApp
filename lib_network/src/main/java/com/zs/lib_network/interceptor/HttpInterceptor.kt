package com.zs.lib_network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

/**
 * 拦截器
 * 重定向、请求头、加密/解密
 */
class HttpInterceptor: Interceptor {

    private val tag = "HTTP"

    override fun intercept(chain: Interceptor.Chain): Response {
        // 请求参数
        val request = chain.request()

        // 记录请求信息
        Log.i(tag, "================== REQUEST ======================")
        Log.i(tag, "URL: ${request.url}")
        Log.i(tag, "Method: ${request.method}")

        // 记录请求头
        request.headers.forEach { (name, value) ->
            Log.i(tag, "[Header] $name: $value")
        }

        // 记录请求体内容（仅限非GET请求）
        if (request.method != "GET") {
            val requestBody = request.body
            if (requestBody != null) {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val content = buffer.readUtf8()
                Log.i(tag, "Request Body:\n${content.take(2000)}") // 限制输出长度
            }
        }

        // 执行请求
        val response = chain.proceed(request)

        // 记录响应信息
        Log.i(tag, "================== RESPONSE ======================")
        Log.i(tag, "URL: ${response.request.url}")
        Log.i(tag, "Status: ${response.code} ${response.message}")

        // 记录响应头
        response.headers.forEach { (name, value) ->
            Log.i(tag, "[Header] $name: $value")
        }

        // 关键：正确记录响应体内容
        val responseBody = response.body
        if (responseBody != null) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // 缓存整个响应体
            val buffer = source.buffer

            // 克隆缓冲以免消耗原始响应
            val responseBodyCopy = buffer.clone()

            // 确定内容类型
            val contentType = responseBody.contentType()
            val content = when {
                contentType?.toString()?.contains("json") == true -> responseBodyCopy.readUtf8()
                contentType?.toString()?.contains("text") == true -> responseBodyCopy.readUtf8()
                else -> "(二进制内容，大小: ${responseBody.contentLength()} 字节)"
            }

            Log.i(tag, "Response Body:\n${content.take(2000)}") // 限制输出长度
        }

        // 返回原始响应（避免污染数据）
        return response
    }
}