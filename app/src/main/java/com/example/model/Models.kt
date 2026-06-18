package com.example.model

data class Person(
    val id: String,
    val name: String,
    val age: Int,
    val job: String,
    val description: String,
    val clues: List<Clue>
)

data class Clue(
    val id: String,
    val title: String,
    val content: String,
    val unlockedByDefault: Boolean = false,
    val triggersClueId: String? = null
)

data class ChatMessage(
    val sender: String,
    val text: String,
    val isPlayer: Boolean = false
)

enum class DesktopApp(val title: String) {
    DOSSIER("Досье"),
    MAP("Карта города"),
    CHAT("Чат"),
    MAIL("Почта"),
    BOARD("Доска расследования")
}

data class NodeContent(
    val id: String,
    val title: String,
    var x: Float = 0f,
    var y: Float = 0f
)
