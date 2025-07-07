package fr.xania.questjournal

import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.plugin
import fr.xania.questjournal.listener.JournalListener
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.PluginManager

@Singleton
object JournalInitializer : Initializable {
    private val clickListener = JournalListener()

    override suspend fun initialize() {
        val manager: PluginManager = Bukkit.getPluginManager()
        manager.registerEvents(clickListener, plugin)
    }

    override suspend fun shutdown() {
        InventoryClickEvent.getHandlerList().unregister(clickListener)
    }
}