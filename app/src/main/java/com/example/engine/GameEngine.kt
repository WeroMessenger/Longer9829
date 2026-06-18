package com.example.engine

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.model.Clue
import com.example.model.DesktopApp
import com.example.model.NodeContent
import com.example.model.Person

object GameEngine {
    val persons = listOf(
        Person(
            "anna", "Анна Лебедева", 21, "Студентка-журналист",
            "Расследовала деятельность завода. Пропала 3 дня назад на окраине.",
            listOf(
                Clue("c_anna_photo", "Фото завода", "Снимок заброшенного завода ночью. В окне виден силуэт.", true),
                Clue("c_anna_chat", "Переписка с Ghost_13", "Ghost_13: «Ты слишком глубоко копаешь, Анна.»"),
                Clue("c_anna_ticket", "Билет на автобус", "Билет до Железнодорожной станции на 23:00.")
            )
        ),
        Person(
            "dmitry", "Дмитрий Крылов", 34, "Водитель такси",
            "Найдена пустая машина с включенным таксометром. Дверь открыта.",
            listOf(
                Clue("c_dmitry_history", "История поездок", "Последний заказ был к Заброшенному складу.", true),
                Clue("c_dmitry_cam", "Камеры наблюдения", "Размытое Видео: Крылов выходит из машины и идет за человеком в капюшоне."),
                Clue("c_dmitry_receipt", "Чек с заправки", "Найден странный набор цифр на чеке: 44-89-13.")
            )
        ),
        Person(
            "marina", "Марина Соколова", 28, "Учитель информатики",
            "Последний раз логинилась в скрытый даркнет-форум.",
            listOf(
                Clue("c_marina_email", "Зашифрованные письма", "Письмо: «Эксперимент начнется завтра. Приходи.»", true),
                Clue("c_marina_browser", "История браузера", "Поиск чертежей городского Метро."),
                Clue("c_marina_pass", "Пароли и заметки", "Заметка: Ghost_13 это...")
            )
        )
    )

    var isGameStarted = mutableStateOf(false)
    var openedApp = mutableStateOf<DesktopApp?>(null)
    
    // Unlocked clues IDs
    val unlockedClues = mutableStateListOf<String>("c_anna_photo", "c_dmitry_history", "c_marina_email")
    
    // Active connections for the investigation board
    val boardNodes = mutableStateListOf<NodeContent>()
    val boardConnections = mutableStateListOf<Pair<String, String>>()

    init {
        // Initialize board with known clues
        val initialX = 100f
        var currentY = 100f
        unlockedClues.forEach { clueId ->
            val clueTitle = findClueById(clueId)?.title ?: clueId
            boardNodes.add(NodeContent(clueId, clueTitle, initialX, currentY))
            currentY += 150f
        }
    }

    fun startGame() {
        isGameStarted.value = true
    }

    fun openApp(app: DesktopApp) {
        openedApp.value = app
    }

    fun closeApp() {
        openedApp.value = null
    }

    fun unlockClue(clueId: String) {
        if (!unlockedClues.contains(clueId)) {
            unlockedClues.add(clueId)
            val clueTitle = findClueById(clueId)?.title ?: clueId
            boardNodes.add(NodeContent(clueId, clueTitle, (200..600).random().toFloat(), (100..400).random().toFloat()))
        }
    }

    fun findClueById(id: String): Clue? {
        persons.forEach { person ->
            person.clues.find { it.id == id }?.let { return it }
        }
        return null
    }

    fun connectNodes(node1Id: String, node2Id: String) {
        val existing = boardConnections.find { 
             (it.first == node1Id && it.second == node2Id) || (it.first == node2Id && it.second == node1Id) 
        }
        if (existing == null && node1Id != node2Id) {
            boardConnections.add(Pair(node1Id, node2Id))
            checkWinCondition()
        }
    }

    private fun checkWinCondition() {
        // Simple win condition logic: connect key evidence
        val hasGhostConnection = boardConnections.any { 
            (it.first == "c_anna_chat" && it.second == "c_marina_pass") ||
            (it.second == "c_anna_chat" && it.first == "c_marina_pass")
        }
        if (hasGhostConnection) {
            // Trigger ending
        }
    }
}
