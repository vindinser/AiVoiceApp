package com.zs.aivoiceapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zs.aivoiceapp.ui.theme.AiVoiceAppTheme
import com.zs.lib_base.base.event.EventManager
import com.zs.lib_base.base.event.MessageEvent
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
                }
            }
        }

        // 注册EventBus
        EventManager.register(this)

        EventManager.post(111)
        // EventManager.post(111, "helloWorld")

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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AiVoiceAppTheme {
        Greeting("Android")
    }
}