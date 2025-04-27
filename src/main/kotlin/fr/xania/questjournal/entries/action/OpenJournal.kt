package fr.xania.questjournal.entries.action

import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.priority
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.Criteria
import com.typewritermc.engine.paper.entry.Modifier
import com.typewritermc.engine.paper.entry.TriggerableEntry
import com.typewritermc.engine.paper.entry.entries.ActionEntry
import com.typewritermc.engine.paper.entry.entries.ActionTrigger
import com.typewritermc.engine.paper.entry.entries.EventTrigger
import fr.xania.questjournal.interaction.JournalStartTrigger
import java.util.Collections.emptyList

@Entry("open_journal", "The base of the Quests Journal.", Colors.RED, "mdi-light:book-multiple")
/**
 * The `Open Journal` is an action that open the Quest Journal to the player.
 * You can configure everything in the panel.
 *
 * ## How could this be used?
 * Open the Journal after Interact with a block or an entity.
 */
class OpenJournal(
    override val id: String = "",
    override val name: String = "",
    override val criteria: List<Criteria> = emptyList(),
    override val modifiers: List<Modifier> = emptyList(),
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
    val mainMenuTitle: String = "<aqua>Quest Journal",
    val questsMenuTitle: String = "<aqua>Quest Journal",
) : ActionEntry {
    override val eventTriggers: List<EventTrigger>
        get() = listOf(
            JournalStartTrigger(
                this.priority,
                super.eventTriggers
            )
        )
    override fun ActionTrigger.execute() {}
}