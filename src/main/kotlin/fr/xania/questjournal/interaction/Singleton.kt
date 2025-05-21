package fr.xania.questjournal.interaction

import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.plugin

@Singleton
object JournalInitializer : Initializable {

    override suspend fun initialize() {
        plugin.server.pluginManager.registerEvents(JournalListener(), plugin)
    }
    override suspend fun shutdown() {}
}