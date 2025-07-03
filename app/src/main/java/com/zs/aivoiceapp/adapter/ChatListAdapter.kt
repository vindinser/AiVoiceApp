package com.zs.aivoiceapp.adapter

import com.zs.aivoiceapp.R
import com.zs.aivoiceapp.data.ChatListData
import com.zs.aivoiceapp.entity.AppConstants
import com.zs.lib_base.base.adapter.CommonAdapter
import com.zs.lib_base.base.adapter.CommonViewHolder

/**
 * 对话列表适配器
 */
class ChatListAdapter(
    mList: List<ChatListData>
) : CommonAdapter<ChatListData>(mList, object : OnMoreBindDataListener<ChatListData> {

    override fun onBindViewHolder(
        model: ChatListData,
        viewHolder: CommonViewHolder,
        type: Int,
        position: Int
    ) {
        when(type) {
            AppConstants.TYPE_MINE_TEXT -> viewHolder.setText(R.id.tv_mine_text, model.text)
            AppConstants.TYPE_AI_TEXT -> viewHolder.setText(R.id.tv_ai_text, model.text)
            AppConstants.TYPE_CONSTELLATION_TEXT -> {}
            AppConstants.TYPE_JOKE_TEXT -> {}
            AppConstants.TYPE_MAP_TEXT -> {}
            AppConstants.TYPE_WEATHER_TEXT -> {}
            else -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mList[position].type
    }

    override fun getLayoutId(type: Int): Int {
        return when(type) {
            AppConstants.TYPE_MINE_TEXT -> R.layout.layout_mine_type
            AppConstants.TYPE_AI_TEXT -> R.layout.layout_ai_type
            AppConstants.TYPE_CONSTELLATION_TEXT -> R.layout.layout_constellation_type
            AppConstants.TYPE_JOKE_TEXT -> R.layout.layout_joke_type
            AppConstants.TYPE_MAP_TEXT -> R.layout.layout_map_type
            AppConstants.TYPE_WEATHER_TEXT -> R.layout.layout_weather_type
            else -> 0
        }
    }

}) {


}