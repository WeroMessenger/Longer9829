package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.engine.GameEngine
import com.example.ui.screens.DesktopScreen
import com.example.ui.screens.MainMenuScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                GameApp()
            }
        }
    }
}

@Composable
fun GameApp() {
    if (GameEngine.isGameStarted.value) {
        DesktopScreen()
    } else {
        MainMenuScreen()
    }
}
