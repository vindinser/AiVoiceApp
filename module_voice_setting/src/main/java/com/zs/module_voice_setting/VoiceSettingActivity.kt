package com.zs.module_voice_setting

import android.widget.Button
import android.widget.SeekBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import com.zs.lib_base.base.BaseActivity
import com.zs.lib_base.base.adapter.CommonAdapter
import com.zs.lib_base.base.adapter.CommonViewHolder
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_voice.manager.VoiceManager

/**
 * 语音设置
 */
@Route(path = ARouterHelper.PATH_VOICE_SETTING, description = "语音设置")
class VoiceSettingActivity: BaseActivity() {

    private val mList: ArrayList<String> = ArrayList()
    private var mTtsPeopleIndex: Array<String>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_setting_voice
    }

    override fun getTitleText(): String {
        return getString(com.zs.lib_base.R.string.app_title_voice_setting)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        initVoiceSpeed()

        initVoiceVolume()

        initPeopleData()
        initPeopleView()

        textBtnListener()
    }

    private fun textBtnListener() {
        var btnText: Button = findViewById(R.id.btn_text)
        btnText.setOnClickListener({
            VoiceManager.ttsStart("您好，我是您的语音助理！请问对您设置的满意吗？")
        })
    }

    // 设置语速
    private fun initVoiceSpeed() {
        var voiceSpeedBar: SeekBar = findViewById(R.id.bar_voice_speed)
        voiceSpeedBar.progress = 5
        voiceSpeedBar.max = 15

        voiceSpeedBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                voiceSpeedBar.progress = progress
                VoiceManager.setVoiceSpeed(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    // 设置音量
    private fun initVoiceVolume() {
        var voiceVolumeBar: SeekBar = findViewById(R.id.bar_voice_volume)
        voiceVolumeBar.progress = 15
        voiceVolumeBar.max = 15
        voiceVolumeBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                voiceVolumeBar.progress = progress
                VoiceManager.setVoiceVolume(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    //初始化发音人数据
    private fun initPeopleData() {
        val mTtsPeople = resources.getStringArray(R.array.TTSPeople)
        mTtsPeopleIndex = resources.getStringArray(R.array.TTSPeopleIndex)

        mTtsPeople.forEach { mList.add(it) }
    }
    // 设置发音人
    private fun initPeopleView() {
        // 布局管理器
        var rvVoicePeopleView: RecyclerView = findViewById(R.id.rv_voice_people)
        rvVoicePeopleView.layoutManager = LinearLayoutManager(this)

        // 分割线
        rvVoicePeopleView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // 适配器
        rvVoicePeopleView.adapter = CommonAdapter(mList, object: CommonAdapter.OnBindDataListener<String> {
            override fun onBindViewHolder(
                model: String,
                viewHolder: CommonViewHolder,
                type: Int,
                position: Int
            ) {
                viewHolder.setText(R.id.mTvTTSPeopleContent, model)
                viewHolder.itemView.setOnClickListener {
                    mTtsPeopleIndex?.let {
                        VoiceManager.setPeople(it[position])
                    }
                }
            }

            override fun getLayoutId(type: Int): Int {
                return R.layout.layout_tts_people_list
            }

        })
    }
}