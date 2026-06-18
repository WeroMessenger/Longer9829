package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engine.GameEngine
import com.example.model.DesktopApp
import com.example.ui.apps.DossierApp
import com.example.ui.apps.InvestigationBoardApp
import com.example.ui.components.WindowFrame

@Composable
fun DesktopScreen() {
    val activeApp = GameEngine.openedApp.value

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF001111))) {
        // Desktop wallpaper / matrix effect could go here
        
        // Active Window
        if (activeApp != null) {
            WindowFrame(title = activeApp.title, onClose = { GameEngine.closeApp() }) {
                when (activeApp) {
                    DesktopApp.DOSSIER -> DossierApp()
                    DesktopApp.BOARD -> InvestigationBoardApp()
                    DesktopApp.MAP -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("СИСТЕМА КАРТОГРАФИИ НЕДОСТУПНА", color = Color.Red) }
                    DesktopApp.MAIL -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("ВХОДЯЩИЕ: 0", color = Color.Green) }
                    DesktopApp.CHAT -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("СЕТЬ ОФФЛАЙН", color = Color.Gray) }
                }
            }
        }

        // Taskbar
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color(0xFF0A0A0E))
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TaskbarIcon(DesktopApp.DOSSIER, Icons.Default.Folder)
            Spacer(modifier = Modifier.width(24.dp))
            TaskbarIcon(DesktopApp.BOARD, Icons.Default.Hub)
            Spacer(modifier = Modifier.width(24.dp))
            TaskbarIcon(DesktopApp.MAP, Icons.Default.Map)
            Spacer(modifier = Modifier.width(24.dp))
            TaskbarIcon(DesktopApp.MAIL, Icons.Default.Email)
            Spacer(modifier = Modifier.width(24.dp))
            TaskbarIcon(DesktopApp.CHAT, Icons.Default.Chat)
        }
    }
}

@Composable
fun TaskbarIcon(app: DesktopApp, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    val isAppActive = GameEngine.openedApp.value == app
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { GameEngine.openApp(app) }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = app.title,
            tint = if (isAppActive) Color(0xFFFF0055) else Color(0xFF00FFCC),
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = app.title,
            color = if (isAppActive) Color(0xFFFF0055) else Color(0xFF00FFCC),
            fontSize = 10.sp,
            fontWeight = if (isAppActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}
