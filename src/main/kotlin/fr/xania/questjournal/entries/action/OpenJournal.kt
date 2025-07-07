package fr.xania.questjournal.entries.action

import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.*
import com.typewritermc.core.utils.launch
import com.typewritermc.engine.paper.entry.*
import com.typewritermc.engine.paper.entry.entries.*
import com.typewritermc.engine.paper.snippets.snippet
import com.typewritermc.engine.paper.utils.Sync
import fr.xania.questjournal.inventories.MainJournalInventory
import kotlinx.coroutines.Dispatchers

val mainMenuTitle: String by snippet("journal.menu.main.title", "Quests Journal")
val mainMenuRows: Int by snippet("journal.menu.main.rows", 54, "The number of slot in the menu, need to be a multiple of 9.")

val mainMenuButtonActiveIsEnabled: Boolean by snippet("journal.menu.main.button.active.enabled", true)
val mainMenuButtonActiveName: String by snippet("journal.menu.main.button.active.name", "Active Quests")
val mainMenuButtonActiveMaterial: String by snippet("journal.menu.main.button.active.material", "GREEN_BANNER")
val mainMenuButtonActiveLore: List<String> by snippet("journal.menu.main.button.active.lore", emptyList<String>())
val mainMenuButtonActiveSlot: Int by snippet("journal.menu.main.button.active.slot", 20)
val mainMenuButtonActiveCMD: Int by snippet("journal.menu.main.button.active.custom-model-data", 0)

val mainMenuButtonInactiveIsEnabled: Boolean by snippet("journal.menu.main.button.inactive.enabled", true)
val mainMenuButtonInactiveName: String by snippet("journal.menu.main.button.inactive.name", "Inactive Quests")
val mainMenuButtonInactiveMaterial: String by snippet("journal.menu.main.button.inactive.material", "RED_BANNER")
val mainMenuButtonInactiveLore: List<String> by snippet("journal.menu.main.button.inactive.lore", emptyList<String>())
val mainMenuButtonInactiveSlot: Int by snippet("journal.menu.main.button.inactive.slot", 22)
val mainMenuButtonInactiveCMD: Int by snippet("journal.menu.main.button.inactive.custom-model-data", 0)

val mainMenuButtonCompletedIsEnabled: Boolean by snippet("journal.menu.main.button.completed.enabled", true)
val mainMenuButtonCompletedName: String by snippet("journal.menu.main.button.completed.name", "Completed Quests")
val mainMenuButtonCompletedMaterial: String by snippet("journal.menu.main.button.completed.material", "YELLOW_BANNER")
val mainMenuButtonCompletedLore: List<String> by snippet("journal.menu.main.button.completed.lore", emptyList<String>())
val mainMenuButtonCompletedSlot: Int by snippet("journal.menu.main.button.completed.slot", 24)
val mainMenuButtonCompletedCMD: Int by snippet("journal.menu.main.button.completed.custom-model-data", 0)

val questMenuTitleActive: String by snippet("journal.menu.quest.title", "Quests Journal")
val questMenuRows: Int by snippet("journal.menu.quest.rows", 54, "The number of slot in the menu, need to be a multiple of 9.")

val questMenuButtonPreviousName: String by snippet("journal.menu.quest.button.previous.name", "Previous Page")
val questMenuButtonPreviousMaterial: String by snippet("journal.menu.quest.button.previous.material", "ARROW")
val questMenuButtonPreviousLore: List<String> by snippet("journal.menu.quest.button.previous.lore", emptyList<String>())
val questMenuButtonPreviousSlot: Int by snippet("journal.menu.quest.button.previous.slot", 45)
val questMenuButtonPreviousCMD: Int by snippet("journal.menu.quest.button.previous.custom-model-data", 0)

val questMenuButtonNextName: String by snippet("journal.menu.quest.button.next.name", "Next Page")
val questMenuButtonNextMaterial: String by snippet("journal.menu.quest.button.next.material", "ARROW")
val questMenuButtonNextLore: List<String> by snippet("journal.menu.quest.button.next.lore", emptyList<String>())
val questMenuButtonNextSlot: Int by snippet("journal.menu.quest.button.next.slot", 53)
val questMenuButtonNextCMD: Int by snippet("journal.menu.quest.button.next.custom-model-data", 0)

val questMenuButtonQuestMaterial: String by snippet("journal.menu.quest.button.quest.material", "BOOK")
val questMenuButtonQuestLore: String by snippet("journal.menu.quest.button.quest.lore", "<red>No description available", "The lore of the quest button if the quest has not any description or objectives.")
val questMenuButtonQuestCMD: Int by snippet("journal.menu.quest.button.quest.custom-model-data", 0)


@Entry("open_journal", "The base of the Quests Journal.", Colors.RED, "mdi-light:book-multiple")
class OpenJournalEntry(
    override val id: String = "",
    override val name: String = "",
    override val criteria: List<Criteria> = emptyList(),
    override val modifiers: List<Modifier> = emptyList(),
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
) : ActionEntry {
    override fun ActionTrigger.execute() {
        Dispatchers.Sync.launch {
            player.openInventory(MainJournalInventory(player).inventory)
        }
    }
}