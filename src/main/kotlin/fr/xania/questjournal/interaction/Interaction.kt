package fr.xania.questjournal.interaction

import com.typewritermc.core.interaction.Interaction
import com.typewritermc.core.interaction.InteractionContext
import com.typewritermc.core.utils.ok
import com.typewritermc.engine.paper.entry.entries.EventTrigger
import com.typewritermc.engine.paper.entry.triggerFor
import com.typewritermc.engine.paper.plugin
import fr.xania.questjournal.inventories.createMainJournalInventory
import fr.xania.questjournal.inventoryHolder.MainJournalInventoryHolder
import fr.xania.questjournal.inventoryHolder.QuestsJournalInventoryHolder
import org.bukkit.entity.Player
import java.time.Duration

class JournalInteraction(
    private val player: Player,
    override val context: InteractionContext,
    override val priority: Int,
    val eventTriggers: List<EventTrigger>
) : Interaction {

    override suspend fun initialize(): Result<Unit> {
        plugin.server.scheduler.runTask(plugin, Runnable {
            player.openInventory(createMainJournalInventory(player))
            player.sendMessage("Starting Journal interaction")
        })
            return ok(Unit)
    }

    override suspend fun tick(deltaTime: Duration) {
        player.sendMessage("Ticking Journal Interaction")

        plugin.server.scheduler.runTaskLater(plugin,
            Runnable {
                if (shouldEnd()) {
                    JournalStopTrigger.triggerFor(player, context)
                }
            },
            1L
        )
    }

    override suspend fun teardown(force: Boolean) {
        player.sendMessage("Ending Journal interaction")
    }

    private fun shouldEnd(): Boolean {
        val inventory = player.openInventory.topInventory
        val holder = inventory.holder
        return holder !is MainJournalInventoryHolder && holder !is QuestsJournalInventoryHolder
    }
}