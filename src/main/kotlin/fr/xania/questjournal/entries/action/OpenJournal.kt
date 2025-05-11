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
import com.typewritermc.engine.paper.snippets.snippet
import fr.xania.questjournal.interaction.JournalStartTrigger
import java.util.Collections.emptyList

val mainMenuTitleSnippet: String by snippet("journal.menu.main.title", "Quests Journal")
val questMenuActiveTitleSnippet: String by snippet("journal.menu.quest.title", "Quests")

val mainMenuButtonsActiveEnabled: Boolean by snippet("journal.menu.main.buttons.active.enabled", true)
val mainMenuButtonsActiveName: String by snippet("journal.menu.main.buttons.active.name", "<green>See active quests")
val mainMenuButtonsActiveType: String by snippet("journal.menu.main.buttons.active.type", "GREEN_BANNER")
val mainMenuButtonsActiveModelData: Int by snippet("journal.menu.main.buttons.active.model-data", 0)
val mainMenuButtonsActivePlace: Int by snippet("journal.menu.main.buttons.active.place", 20)
val mainMenuButtonsActiveLore: List<String> by snippet("journal.menu.main.buttons.active.lore", listOf("<gray>Click to open your quests", "<gray>and see your progress"))

val mainMenuButtonsInactiveEnabled: Boolean by snippet("journal.menu.main.buttons.inactive.enabled", true)
val mainMenuButtonsInactiveName: String by snippet("journal.menu.main.buttons.inactive.name", "<yellow>See inactive quests")
val mainMenuButtonsInactiveType: String by snippet("journal.menu.main.buttons.inactive.type", "YELLOW_BANNER")
val mainMenuButtonsInactiveModelData: Int by snippet("journal.menu.main.buttons.inactive.model-data", 0)
val mainMenuButtonsInactivePlace: Int by snippet("journal.menu.main.buttons.inactive.place", 22)
val mainMenuButtonsInactiveLore: List<String> by snippet("journal.menu.main.buttons.inactive.lore", listOf("<gray>Click to open your quests", "<gray>and see your progress"))

val mainMenuButtonsCompletedEnabled: Boolean by snippet("journal.menu.main.buttons.completed.enabled", true)
val mainMenuButtonsCompletedName: String by snippet("journal.menu.main.buttons.completed.name", "<gray>See completed quests")
val mainMenuButtonsCompletedType: String by snippet("journal.menu.main.buttons.completed.type", "GRAY_BANNER")
val mainMenuButtonsCompletedModelData: Int by snippet("journal.menu.main.buttons.completed.model-data", 0)
val mainMenuButtonsCompletedPlace: Int by snippet("journal.menu.main.buttons.completed.place", 24)
val mainMenuButtonsCompletedLore: List<String> by snippet("journal.menu.main.buttons.completed.lore", listOf("<gray>Click to open your quests", "<gray>and see your progress"))

val questMenuButtonQuestEnabled: Boolean by snippet("journal.menu.quest.buttons.quest.enabled", true)
val questMenuButtonQuestType: String by snippet("journal.menu.quest.buttons.quest.type", "WRITTEN_BOOK")
val questMenuButtonQuestModelData: Int by snippet("journal.menu.quest.buttons.quest.model-data", 0)
val questMenuButtonQuestLore: List<String> by snippet("journal.menu.quest.buttons.quest.lore", listOf("<red>No description available"))

val questMenuButtonLeaveTitle: String by snippet("journal.menu.quest.buttons.leave.title", "<red>Leave")
val questMenuButtonLeaveType: String by snippet("journal.menu.quest.buttons.leave.type", "BARRIER")
val questMenuButtonLeaveModelData: Int by snippet("journal.menu.quest.buttons.leave.model-data", 0)
val questMenuButtonLeavePlace: Int by snippet("journal.menu.quest.buttons.leave.place", 49)
val questMenuButtonLeaveLore: List<String> by snippet("journal.menu.quest.buttons.leave.lore", listOf("<red>Click to leave the menu"))

val questMenuButtonNextTitle: String by snippet("journal.menu.quest.buttons.next.title", "<yellow>Next")
val questMenuButtonNextType: String by snippet("journal.menu.quest.buttons.next.type", "ARROW")
val questMenuButtonNextModelData: Int by snippet("journal.menu.quest.buttons.next.model-data", 0)
val questMenuButtonNextPlace: Int by snippet("journal.menu.quest.buttons.next.place", 53)
val questMenuButtonNextLore: List<String> by snippet("journal.menu.quest.buttons.next.lore", listOf("<red>Click to go to the next page"))

val questMenuButtonPreviousTitle: String by snippet("journal.menu.quest.buttons.previous.title", "<yellow>Previous")
val questMenuButtonPreviousType: String by snippet("journal.menu.quest.buttons.previous.type", "ARROW")
val questMenuButtonPreviousModelData: Int by snippet("journal.menu.quest.buttons.previous.model-data", 0)
val questMenuButtonPreviousPlace: Int by snippet("journal.menu.quest.buttons.previous.place", 45)
val questMenuButtonPreviousLore: List<String> by snippet("journal.menu.quest.buttons.previous.lore", listOf("<red>Click to go to the previous page"))


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