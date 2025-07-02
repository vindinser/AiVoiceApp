package com.zs.aivoiceapp.converter

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

class ScaleInTransformer : ViewPager2.PageTransformer, ViewPager.PageTransformer {
    // 最小的缩放比例
    private val minScale = 0.85f

    override fun transformPage(page: View, position: Float) {
        when {
            // 当页面处于可见状态左侧（position < -1）
            position < -1 -> {
                // 页面完全离开屏幕左侧
                page.scaleX = minScale
                page.scaleY = minScale
            }
            // 当页面从左侧滑动到中心再到右侧（-1 ≤ position ≤ 1）
            position <= 1 -> {
                // 缩放因子：根据位置调整大小（从 minScale 到 1）
                val scaleFactor = minScale.coerceAtLeast(1 - Math.abs(position) * 0.15f)
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor

                // 透明度变化：当页面离开中心时逐渐变暗
                page.alpha = 0.7f + (1 - Math.abs(position)) * 0.3f
            }
            // 当页面处于可见状态右侧（position > 1）
            else -> {
                // 页面完全离开屏幕右侧
                page.scaleX = minScale
                page.scaleY = minScale
            }
        }
    }
}