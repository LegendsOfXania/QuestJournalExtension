package fr.xania.questjournal

import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.plugin
import dev.triumphteam.gui.TriumphGui

@Singleton
object JournalInitializer : Initializable {
    override suspend fun initialize() {
        TriumphGui.init(plugin)
    }

    override suspend fun shutdown() { }
}