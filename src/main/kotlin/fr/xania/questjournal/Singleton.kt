package fr.xania.questjournal

import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.logger
import com.typewritermc.engine.paper.plugin
import fr.xania.questjournal.entries.action.OpenJournal

@Singleton
object Initializer : Initializable {
    override suspend fun initialize() {
        plugin.server.pluginManager.registerEvents(OpenJournal.Journal, plugin)
        logger.info("JournalExtension initialized.")
    }

    override suspend fun shutdown() {
        logger.info("JournalExtension shutdown.")
    }
}
