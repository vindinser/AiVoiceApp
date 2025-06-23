package com.zs.aivoiceapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zs.aivoiceapp.ui.theme.AiVoiceAppTheme
import com.zs.lib_base.event.EventManager
import com.zs.lib_base.event.MessageEvent
import com.zs.lib_base.helper.ARouterHelper
import com.zs.lib_base.utils.L
import com.zs.lib_base.utils.SpUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AiVoiceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "First Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    TextBtn()
                }
            }
        }

        // 注册EventBus
        EventManager.register(this)

        EventManager.post(111)
        // EventManager.post(111, "helloWorld")

        val k: String = "sharepreferences"
        SpUtils.putString(k, "text")
        val sp = SpUtils.getString(k)
        k?.let {
            L.i(it)
            L.e(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 解绑EventBus
        EventManager.unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {
        Log.i("TestApp", "111")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun TextBtn(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 按钮组件
        Button(
            onClick = {
                ARouterHelper.startActivity(ARouterHelper.PATH_SETTING)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("路由跳转")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AiVoiceAppTheme {
        Greeting("Android")
    }
}