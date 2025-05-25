package fr.xania.questjournal.entries.action

import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.Criteria
import com.typewritermc.engine.paper.entry.Modifier
import com.typewritermc.engine.paper.entry.TriggerableEntry
import com.typewritermc.engine.paper.entry.entries.ActionEntry
import com.typewritermc.engine.paper.entry.entries.ActionTrigger
import com.typewritermc.engine.paper.utils.ThreadType.SYNC
import com.typewritermc.engine.paper.snippets.snippet
import fr.xania.questjournal.inventories.mainJournalInventory
import org.bukkit.Material
import java.util.Collections.emptyList

val mainMenuTitle: String by snippet("journal.menu.main.title", "Quests Journal")
val mainMenuRows: Int by snippet("journal.menu.main.rows", 6)

val mainMenuButtonLeaveName: String by snippet("journal.menu.main.button.leave.name", "Leave")
val mainMenuButtonLeaveMaterial: Material by snippet("journal.menu.main.button.leave.material", Material.BARRIER)
val mainMenuButtonLeaveLore: List<String> by snippet("journal.menu.main.button.leave.lore", emptyList<String>())
val mainMenuButtonLeaveSlot: Int by snippet("journal.menu.main.button.leave.slot", 0)
val mainMenuButtonLeaveCMD: Int by snippet("journal.menu.main.button.leave.custom-model-data", 0)

val mainMenuButtonActiveName: String by snippet("journal.menu.main.button.active.name", "Active Quests")
val mainMenuButtonActiveMaterial: Material by snippet("journal.menu.main.button.active.material", Material.GREEN_BANNER)
val mainMenuButtonActiveLore: List<String> by snippet("journal.menu.main.button.active.lore", emptyList<String>())
val mainMenuButtonActiveSlot: Int by snippet("journal.menu.main.button.active.slot", 20)
val mainMenuButtonActiveCMD: Int by snippet("journal.menu.main.button.active.custom-model-data", 0)

val mainMenuButtonInactiveName: String by snippet("journal.menu.main.button.inactive.name", "Inactive Quests")
val mainMenuButtonInactiveMaterial: Material by snippet("journal.menu.main.button.inactive.material", Material.RED_BANNER)
val mainMenuButtonInactiveLore: List<String> by snippet("journal.menu.main.button.inactive.lore", emptyList<String>())
val mainMenuButtonInactiveSlot: Int by snippet("journal.menu.main.button.inactive.slot", 22)
val mainMenuButtonInactiveCMD: Int by snippet("journal.menu.main.button.inactive.custom-model-data", 0)

val mainMenuButtonCompletedName: String by snippet("journal.menu.main.button.completed.name", "Completed Quests")
val mainMenuButtonCompletedMaterial: Material by snippet("journal.menu.main.button.completed.material", Material.YELLOW_BANNER)
val mainMenuButtonCompletedLore: List<String> by snippet("journal.menu.main.button.completed.lore", emptyList<String>())
val mainMenuButtonCompletedSlot: Int by snippet("journal.menu.main.button.completed.slot", 24)
val mainMenuButtonCompletedCMD: Int by snippet("journal.menu.main.button.completed.custom-model-data", 0)

val questMenuTitleActive: String by snippet("journal.menu.quest.title", "Actives Quests")
val questMenuTitleInactive: String by snippet("journal.menu.quest.inactive.title", "Inactives Quests")
val questMenuTitleCompleted: String by snippet("journal.menu.quest.completed.title", "Completed Quests")
val questMenuRows: Int by snippet("journal.menu.quest.rows", 6)

val questMenuButtonPreviousName: String by snippet("journal.menu.quest.button.previous.name", "Previous Page")
val questMenuButtonPreviousMaterial: Material by snippet("journal.menu.quest.button.previous.material", Material.ARROW)
val questMenuButtonPreviousLore: List<String> by snippet("journal.menu.quest.button.previous.lore", emptyList<String>())
val questMenuButtonPreviousSlot: Int by snippet("journal.menu.quest.button.previous.slot", 45)
val questMenuButtonPreviousCMD: Int by snippet("journal.menu.quest.button.previous.custom-model-data", 0)

val questMenuButtonLeaveName: String by snippet("journal.menu.quest.button.leave.name", "Leave")
val questMenuButtonLeaveMaterial: Material by snippet("journal.menu.quest.button.leave.material", Material.BARRIER)
val questMenuButtonLeaveLore: List<String> by snippet("journal.menu.quest.button.leave.lore", emptyList<String>())
val questMenuButtonLeaveSlot: Int by snippet("journal.menu.quest.button.leave.slot", 0)
val questMenuButtonLeaveCMD: Int by snippet("journal.menu.quest.button.leave.custom-model-data", 0)

val questMenuButtonNextName: String by snippet("journal.menu.quest.button.next.name", "Next Page")
val questMenuButtonNextMaterial: Material by snippet("journal.menu.quest.button.next.material", Material.ARROW)
val questMenuButtonNextLore: List<String> by snippet("journal.menu.quest.button.next.lore", emptyList<String>())
val questMenuButtonNextSlot: Int by snippet("journal.menu.quest.button.next.slot", 53)
val questMenuButtonNextCMD: Int by snippet("journal.menu.quest.button.next.custom-model-data", 0)

val questMenuButtonQuestMaterial: Material by snippet("journal.menu.quest.button.quest.material", Material.WRITTEN_BOOK)
val questMenuButtonQuestLore: List<String> by snippet("journal.menu.quest.button.quest.lore", emptyList<String>(), "The lore of the quest button if the quest has not any description or objectives.")
val questMenuButtonQuestCMD: Int by snippet("journal.menu.quest.button.quest.custom-model-data", 0)

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
    override fun ActionTrigger.execute() {
        SYNC.launch {
            mainJournalInventory(player)
        }
    }
}