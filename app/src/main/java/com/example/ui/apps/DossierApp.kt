package com.example.ui.apps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.engine.GameEngine
import com.example.model.Person

@Composable
fun DossierApp() {
    var selectedPerson by remember { mutableStateOf<Person?>(null) }

    Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Left pane: List of persons
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(GameEngine.persons) { person ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedPerson == person) Color(0xFF00FFCC).copy(alpha = 0.2f) else Color.Transparent
                    ),
                    onClick = { selectedPerson = person }
                ) {
                    Text(
                        text = "ИСК №: ${person.id.uppercase()}\n${person.name}",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF00FFCC)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Right pane: Person details
        Card(
            modifier = Modifier.weight(2f).fillMaxHeight(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24))
        ) {
            if (selectedPerson != null) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = selectedPerson!!.name, style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Text(text = "Возраст: ${selectedPerson!!.age}", color = Color.Gray)
                    Text(text = "Профессия: ${selectedPerson!!.job}", color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = selectedPerson!!.description, color = Color.LightGray)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "СОБРАННЫЕ УЛИКИ:", fontWeight = FontWeight.Bold, color = Color(0xFFFF0055))
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    selectedPerson!!.clues.forEach { clue ->
                        val isUnlocked = GameEngine.unlockedClues.contains(clue.id)
                        if (isUnlocked) {
                            Text(text = "- ${clue.title}: ${clue.content}", color = Color(0xFF33FF00), modifier = Modifier.padding(vertical = 4.dp))
                        } else {
                            Text(text = "- [ДАННЫЕ ЗАСЕКРЕЧЕНЫ]", color = Color.DarkGray, modifier = Modifier.padding(vertical = 4.dp))
                            // Reveal trigger button for simulation purposes
                            Button(onClick = { GameEngine.unlockClue(clue.id) }, modifier = Modifier.padding(bottom = 8.dp)) {
                                Text("ВЗЛОМАТЬ ДАННЫЕ")
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text(text = "ВЫБЕРИТЕ ДЕЛО ДЛЯ ПРОСМОТРА", color = Color.DarkGray)
                }
            }
        }
    }
}
