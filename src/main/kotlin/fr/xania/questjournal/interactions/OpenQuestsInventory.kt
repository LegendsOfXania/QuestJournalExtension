package fr.xania.questjournal.interactions

import com.typewritermc.core.interaction.Interaction
import com.typewritermc.core.interaction.InteractionContext
import com.typewritermc.core.utils.ok
import com.typewritermc.core.utils.failure
import com.typewritermc.engine.paper.entry.entries.EventTrigger
import org.bukkit.entity.Player
import java.time.Duration

class ExampleInteraction(
    val player: Player,
    override val context: InteractionContext,
    override val priority: Int,
    val eventTriggers: List<EventTrigger>
) : Interaction {
    override suspend fun initialize(): Result<Unit> {
        if (Random.nextBoolean()) {
            return failure("Failed to initialize")
        }

        player.sendMessage("Starting interaction!")

        return ok(Unit)
    }

    override suspend fun tick(deltaTime: Duration) {
        if (shouldEnd()) {
            ExampleStopTrigger.triggerFor(player, context)
        }
    }

    override suspend fun teardown(force: Boolean) {
        player.sendMessage("Ending interaction!")
    }

    private fun shouldEnd(): Boolean = false
}

data class ExampleStartTrigger(
    val priority: Int,
    val eventTriggers: List<EventTrigger> = emptyList()
) : EventTrigger {
    override val id: String = "example.start"
}

data object ExampleStopTrigger : EventTrigger {
    override val id: String = "example.stop"
}