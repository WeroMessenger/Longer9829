package com.example.ui.apps

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engine.GameEngine
import com.example.model.NodeContent

@Composable
fun InvestigationBoardApp() {
    val nodes = GameEngine.boardNodes
    val connections = GameEngine.boardConnections

    var connectionStartNode by remember { mutableStateOf<NodeContent?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF141419))) {
        // Draw lines
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw confirmed connections
            connections.forEach { pair ->
                val node1 = nodes.find { it.id == pair.first }
                val node2 = nodes.find { it.id == pair.second }
                if (node1 != null && node2 != null) {
                    drawLine(
                        color = Color.Red,
                        start = Offset(node1.x, node1.y),
                        end = Offset(node2.x, node2.y),
                        strokeWidth = 4f
                    )
                }
            }

            // Draw active drag line
            val startNode = connectionStartNode
            val dragPos = currentDragPosition
            if (startNode != null && dragPos != null) {
                drawLine(
                    color = Color.Red.copy(alpha = 0.5f),
                    start = Offset(startNode.x, startNode.y),
                    end = dragPos,
                    strokeWidth = 4f
                )
            }
        }

        // Draw nodes
        nodes.forEach { node ->
            Box(
                modifier = Modifier
                    .offset(x = node.x.dp, y = node.y.dp)
                    .size(120.dp, 80.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                // If touching the edge, maybe start connection. Let's just use drag for moving nodes.
                                // For simplicity, normal drag moves, long press + drag connects? No, let's keep it simple:
                                // drag moves node.
                            },
                            onDragEnd = {
                                connectionStartNode = null
                                currentDragPosition = null
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                node.x += dragAmount.x
                                node.y += dragAmount.y
                                // force recomposition
                                val idx = nodes.indexOf(node)
                                if (idx != -1) {
                                    nodes[idx] = node.copy(x = node.x, y = node.y)
                                }
                            }
                        )
                    }
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = node.title,
                            color = Color(0xFF00FFCC),
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                            maxLines = 2
                        )
                    }
                }
                
                // Connection dots
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(16.dp)
                        .offset(x = 8.dp)
                        .background(Color.Red, shape = androidx.compose.foundation.shape.CircleShape)
                        .pointerInput(node) {
                            detectDragGestures(
                                onDragStart = { connectionStartNode = node },
                                onDragEnd = {
                                    // check if dropped on another node
                                    val start = connectionStartNode
                                    val endPos = currentDragPosition
                                    if (start != null && endPos != null) {
                                        val droppedOn = nodes.find {
                                            endPos.x >= it.x && endPos.x <= it.x + 120 &&
                                            endPos.y >= it.y && endPos.y <= it.y + 80
                                        }
                                        if (droppedOn != null && droppedOn.id != start.id) {
                                            GameEngine.connectNodes(start.id, droppedOn.id)
                                        }
                                    }
                                    connectionStartNode = null
                                    currentDragPosition = null
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    currentDragPosition = change.position
                                }
                            )
                        }
                )
            }
        }
    }
}
