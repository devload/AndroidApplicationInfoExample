package io.devload.applicationinfoexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.devload.applicationinfoexample.ui.theme.ApplicationInfoExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApplicationInfoExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name! Click me to trigger ANR.",
        modifier = modifier
            .padding(16.dp)
            .clickable {
                // ANR을 유발하기 위해 긴 작업을 수행
                performLongTask()
            }
    )
}

private fun performLongTask() {
    // 메인 스레드에서 30초 동안 작업을 수행하여 ANR 발생
    val endTime = System.currentTimeMillis() + 30_000 // 30초 후
    while (System.currentTimeMillis() < endTime) {
        // 빈 루프: 아무것도 하지 않음
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApplicationInfoExampleTheme {
        Greeting("Android")
    }
}
