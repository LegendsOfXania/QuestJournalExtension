package fr.xania.questjournal.interaction

import com.typewritermc.engine.paper.entry.entries.EventTrigger

data class JournalStartTrigger(
    val priority: Int,
    val eventTriggers: List<EventTrigger> = emptyList()
) : EventTrigger {
    override val id: String = "journal.start"
}

data object JournalStopTrigger : EventTrigger {
    override val id: String = "journal.stop"
}