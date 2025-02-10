package fr.xania.questjournal

import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.logger
import com.typewritermc.engine.paper.plugin
import fr.xania.questjournal.entries.action.OpenJournalEntry

@Singleton
object Initializer : Initializable {
    override suspend fun initialize() {

        plugin.server.pluginManager.registerEvents(OpenJournalEntry.Journal, plugin)
        logger.warning("QuestJournalExtension is under development, some bugs may occur.")
    }

    override suspend fun shutdown() {
        // Do something when the extension is shutdown
    }
}
