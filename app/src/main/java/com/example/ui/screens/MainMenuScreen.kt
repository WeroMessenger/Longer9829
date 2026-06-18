package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engine.GameEngine

@Composable
fun MainMenuScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "OSINT:\nПОСЛЕДНИЙ СЛЕД",
                color = Color(0xFF00FFCC),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 56.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ИНИЦИАЛИЗАЦИЯ СИСТЕМЫ...",
                color = Color(0xFFFF0055),
                fontSize = 16.sp,
                letterSpacing = 2.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            MenuButton("НОВАЯ ИГРА") { GameEngine.startGame() }
            Spacer(modifier = Modifier.height(16.dp))
            MenuButton("ПРОДОЛЖИТЬ") { GameEngine.startGame() }
            Spacer(modifier = Modifier.height(16.dp))
            MenuButton("НАСТРОЙКИ") { }
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier.width(250.dp)
    ) {
        Text(text = "[ $text ]", color = Color(0xFF33FF00), fontSize = 20.sp, letterSpacing = 2.sp)
    }
}
