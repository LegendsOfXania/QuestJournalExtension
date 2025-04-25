package fr.xania.questjournal.inventories

import fr.xania.questjournal.entries.action.OpenJournal
import java.net.http.WebSocket.Listener
import java.util.UUID

object Journal: Listener {
    val Pages: MutableMap<UUID, Int> = mutableMapOf()
    val JournalInstance = OpenJournal()

}