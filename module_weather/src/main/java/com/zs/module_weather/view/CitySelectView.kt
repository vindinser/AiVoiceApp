package com.zs.module_weather.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * 城市导航
 */
class CitySelectView: View {

    // view宽
    private var viewWidth = 0
    // view高
    private var viewHeight = 0

    // 画笔
    private val mPaint by lazy {
        Paint()
    }

    // 数据源
    private val mList = ArrayList<String>()

    // 间隔的高
    private var itemHeight = 0

    // 选中的下标
    private var checkIndex = -1

    // 选中的文本大小
    private val checkTextSize = 28f
    // 未选中的文本大小
    private val unCheckTextSize = 18f
    // 选中的文本颜色
    private val checkTextColor = Color.RED
    // 未选中的文本颜色
    private val unCheckTextColor = Color.BLACK

    constructor(context: Context?) : super(context) {
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
    }

    // 初始化view
    private fun initView() {
        // 抗锯齿
        mPaint.isAntiAlias = true
    }

    // 绘制
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.let {
            drawCity(it)
        }
    }

    /**
     * 绘制城市
     * @param {Canvas} canvas 画布
     */
    private fun drawCity(canvas: Canvas) {
        val size = mList.size
        if(size > 0) {
            // 城市文本高度
            itemHeight = viewHeight / size

            mList.forEachIndexed { index, text ->
                if(checkIndex == index) {
                    // 选中
                    mPaint.color = checkTextColor
                    mPaint.textSize = checkTextSize
                } else {
                    // 未选中
                    mPaint.color = unCheckTextColor
                    mPaint.textSize = unCheckTextSize
                }
                // 绘制文本
                val textX = (viewWidth - mPaint.measureText(text)) / 2
                val textY = (itemHeight * index + itemHeight).toFloat()
                canvas.drawText(text, textX, textY, mPaint)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(it.action) {
                MotionEvent.ACTION_DOWN -> mOnViewResultListener?.uiChange(true)
                MotionEvent.ACTION_MOVE -> {
                    val oldCheck = checkIndex
                    val check = (it.y / viewHeight * mList.size).toInt()
                    if(oldCheck != check) {
                        mOnViewResultListener?.valueInput(mList[check])
                        checkIndex = check
                        invalidate()
                    } else {

                    }
                }
                MotionEvent.ACTION_UP -> mOnViewResultListener?.uiChange(false)
                else -> {}
            }
        }

        return super.dispatchTouchEvent(event)
    }

    /**
     * 设置数据源
     * @param {List<String>} list 数据源
     */
    fun setCity(list: List<String>) {
        if(mList.size > 0) {
            mList.clear()
        }
        mList.addAll(list)
        // 刷新View
        invalidate()
    }

    /**
     * 设置选中
     * @param {Int} index 选中的额索引
     */
    fun setCheckIndex(index: Int) {
        checkIndex = index
        invalidate()
    }

    interface OnViewResultListener {
        // 外部控件 显示 隐藏
        fun uiChange(uiShow: Boolean)

        // 值传递
        fun valueInput(city: String)
    }

    private var mOnViewResultListener: OnViewResultListener? = null

    fun setOnViewResultListener(mOnViewResultListener: OnViewResultListener) {
        this.mOnViewResultListener = mOnViewResultListener
    }
}