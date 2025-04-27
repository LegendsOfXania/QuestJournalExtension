package fr.xania.questjournal.interaction

import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.core.interaction.Interaction
import com.typewritermc.engine.paper.entry.entries.Event
import com.typewritermc.engine.paper.interaction.TriggerContinuation
import com.typewritermc.engine.paper.interaction.TriggerHandler
import com.typewritermc.engine.paper.plugin
import fr.xania.questjournal.entries.action.OpenJournal

@Singleton
object JournalInitializer : Initializable {

    override suspend fun initialize() {
        plugin.server.pluginManager.registerEvents(JournalListener(), plugin)
    }
    override suspend fun shutdown() {}
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