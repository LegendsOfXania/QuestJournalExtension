package fr.xania.questjournal

import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.logger

@Singleton
object Initializer : Initializable {

    override suspend fun initialize() {
        logger.info("JournalExtension initialized.")
    }

    override suspend fun shutdown() {
        logger.info("JournalExtension shutdown.")
    }
}
