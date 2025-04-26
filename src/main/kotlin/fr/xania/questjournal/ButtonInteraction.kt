package fr.xania.questjournal

import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.core.interaction.Interaction
import com.typewritermc.core.interaction.InteractionContext
import com.typewritermc.core.utils.ok
import com.typewritermc.engine.paper.entry.entries.Event
import com.typewritermc.engine.paper.entry.entries.EventTrigger
import com.typewritermc.engine.paper.entry.triggerFor
import com.typewritermc.engine.paper.interaction.TriggerContinuation
import com.typewritermc.engine.paper.interaction.TriggerHandler
import com.typewritermc.engine.paper.plugin
import fr.xania.questjournal.entries.action.OpenJournal
import fr.xania.questjournal.inventories.createMainJournalInventory
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.time.Duration

class JournalInteraction(private val player: Player,
                         override val context: InteractionContext,
                         override val priority: Int,
                         val openJournal: OpenJournal,
                         val eventTriggers: List<EventTrigger>) : Interaction {

    override suspend fun initialize(): Result<Unit> {

            object : BukkitRunnable() {
                override fun run() {
                    val inventory = createMainJournalInventory(openJournal)
                    player.openInventory(inventory)
                }
            }.runTask(plugin)

        player.sendMessage("Starting Journal interaction")

        return ok(Unit)
    }

    override suspend fun tick(deltaTime: Duration) {

        if (shouldEnd()) {
            JournalStopTrigger.triggerFor(player, context)
        }
    }

    override suspend fun teardown(force: Boolean) {
        player.sendMessage("Ending Journal interaction")
    }

    private fun shouldEnd(): Boolean = false
}


data class JournalStartTrigger(
    val priority: Int,
    val eventTriggers: List<EventTrigger> = emptyList()
) : EventTrigger {
    override val id: String = "journal.start"
}

data object JournalStopTrigger : EventTrigger {
    override val id: String = "journal.stop"
}

@Singleton
class JournalTriggerHandler : TriggerHandler {

    override suspend fun trigger(event: Event, currentInteraction: Interaction?): TriggerContinuation {
        if (JournalStopTrigger in event && currentInteraction is JournalInteraction) {
            return TriggerContinuation.Multi(
                TriggerContinuation.EndInteraction,
                TriggerContinuation.Append(Event(event.player, currentInteraction.context, currentInteraction.eventTriggers)),
            )
        }

        return tryStartJournalInteraction(event)
    }

    private fun tryStartJournalInteraction(
        event: Event
    ): TriggerContinuation {
        val triggers = event.triggers
            .filterIsInstance<JournalStartTrigger>()

        if (triggers.isEmpty()) return TriggerContinuation.Nothing

        val trigger = triggers.maxBy { it.priority }

        return TriggerContinuation.StartInteraction(
            JournalInteraction(
                event.player,
                event.context,
                trigger.priority,
                openJournal = OpenJournal(),
                trigger.eventTriggers
            )
        )
    }
}