package fr.xania.questjournal.entries.action

import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Query
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.*
import com.typewritermc.engine.paper.entry.entries.ActionEntry
import com.typewritermc.engine.paper.entry.entries.ActionTrigger
import com.typewritermc.engine.paper.entry.entries.LinesEntry
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.plugin
import com.typewritermc.engine.paper.snippets.snippet
import com.typewritermc.engine.paper.utils.asMini
import com.typewritermc.engine.paper.utils.asMiniWithResolvers
import com.typewritermc.engine.paper.utils.legacy
import com.typewritermc.engine.paper.utils.limitLineLength
import com.typewritermc.quest.ObjectiveEntry
import com.typewritermc.quest.QuestEntry
import com.typewritermc.quest.QuestStatus
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.Material
import org.bukkit.inventory.InventoryHolder
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID

val mainMenuTitleSnippet: String by snippet("journal.menu.main.title", "Quests Journal")
val questMenuActiveTitleSnippet: String by snippet("journal.menu.active.title", "Active Quests")
val questMenuInactiveTitleSnippet: String by snippet("journal.menu.inactive.title", "Inactive Quests")
val questMenuCompletedTitleSnippet: String by snippet("journal.menu.completed.title", "Completed Quests")

val mainMenuButtonsActiveName: String by snippet("journal.menu.main.buttons.active.name", "<green>See active quests")
val mainMenuButtonsActiveType: String by snippet("journal.menu.main.buttons.active.type", "GREEN_BANNER")
val mainMenuButtonsActiveModelData: Int by snippet("journal.menu.main.buttons.active.model-data", 0)
val mainMenuButtonsActivePlace: Int by snippet("journal.menu.main.buttons.active.place", 20)

val mainMenuButtonsInactiveName: String by snippet(
    "journal.menu.main.buttons.inactive.name",
    "<yellow>See inactive quests"
)
val mainMenuButtonsInactiveType: String by snippet("journal.menu.main.buttons.inactive.type", "YELLOW_BANNER")
val mainMenuButtonsInactiveModelData: Int by snippet("journal.menu.main.buttons.inactive.model-data", 0)
val mainMenuButtonsInactivePlace: Int by snippet("journal.menu.main.buttons.inactive.place", 22)

val mainMenuButtonsCompletedName: String by snippet(
    "journal.menu.main.buttons.completed.name",
    "<gray>See completed quests"
)
val mainMenuButtonsCompletedType: String by snippet("journal.menu.main.buttons.completed.type", "GRAY_BANNER")
val mainMenuButtonsCompletedModelData: Int by snippet("journal.menu.main.buttons.completed.model-data", 0)
val mainMenuButtonsCompletedPlace: Int by snippet("journal.menu.main.buttons.completed.place", 24)

val questMenuButtonQuestType: String by snippet("journal.menu.quest.buttons.quest.type", "WRITTEN_BOOK")
val questMenuButtonQuestModelData: Int by snippet("journal.menu.quest.buttons.quest.model-data", 0)

val questMenuButtonLeaveTitle: String by snippet("journal.menu.quest.buttons.leave.title", "<red>Leave")
val questMenuButtonLeaveType: String by snippet("journal.menu.quest.buttons.leave.type", "BARRIER")
val questMenuButtonLeaveModelData: Int by snippet("journal.menu.quest.buttons.leave.model-data", 0)
val questMenuButtonLeavePlace: Int by snippet("journal.menu.quest.buttons.leave.place", 49)

val questMenuButtonNextTitle: String by snippet("journal.menu.quest.buttons.next.title", "<yellow>Next")
val questMenuButtonNextType: String by snippet("journal.menu.quest.buttons.next.type", "ARROW")
val questMenuButtonNextModelData: Int by snippet("journal.menu.quest.buttons.next.model-data", 0)
val questMenuButtonNextPlace: Int by snippet("journal.menu.quest.buttons.next.place", 53)

val questMenuButtonPreviousTitle: String by snippet("journal.menu.quest.buttons.previous.title", "<yellow>Previous")
val questMenuButtonPreviousType: String by snippet("journal.menu.quest.buttons.previous.type", "ARROW")
val questMenuButtonPreviousModelData: Int by snippet("journal.menu.quest.buttons.previous.model-data", 0)
val questMenuButtonPreviousPlace: Int by snippet("journal.menu.quest.buttons.previous.place", 45)

@Entry("open_journal_action", "Open the Quest Journal", Colors.RED, "mdi-light:book-multiple")
class OpenJournal(
    override val id: String = "",
    override val name: String = "",
    override val criteria: List<Criteria> = emptyList(),
    override val modifiers: List<Modifier> = emptyList(),
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
) : ActionEntry {

    override fun ActionTrigger.execute() {
        disableAutomaticTriggering()
        triggerManually()

        object : BukkitRunnable() {
            override fun run() {
                val player = player
                Journal.playerPages[player.uniqueId] = 1
                player.openInventory(createMainJournalInventory(player))
            }
        }.runTask(plugin)
    }

    class QuestsJournalInventoryHolder(val status: QuestStatus) : InventoryHolder {
        private lateinit var inventory: Inventory
        override fun getInventory() = inventory
        fun setInventory(inventory: Inventory) {
            this.inventory = inventory
        }
    }

    class MainJournalInventoryHolder : InventoryHolder {
        private lateinit var inventory: Inventory
        override fun getInventory() = inventory
        fun setInventory(inventory: Inventory) {
            this.inventory = inventory
        }
    }

    private fun String.splitComponents(vararg resolvers: TagResolver): List<Component> {
        val components = split("\n").map { it.asMiniWithResolvers(*resolvers) }.toMutableList()

        for (i in 1 until components.size) {
            val previous = components[i - 1]
            val current = components[i]

            val mergedStyle = current.style().merge(previous.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)
            components[i] = current.style(mergedStyle)
        }
        return components
    }

    private fun createSimpleButton(
        menu: Inventory,
        material: Material,
        name: String,
        slot: Int,
        customModelData: Int? = null
    ) {
        val item = ItemStack(material).apply {
            itemMeta = itemMeta?.apply {
                setDisplayName(name.asMini().legacy())
                customModelData?.let { setCustomModelData(it) }
            }
        }
        menu.setItem(slot, item)
    }

    private fun createQuestButton(
        player: Player,
        quest: QuestEntry,
        material: Material,
        lore: List<Component>,
        customModelData: Int? = null
    ) = ItemStack(material).apply {
        itemMeta = itemMeta?.apply {
            setDisplayName("<white>${quest.displayName.get(player)}".parsePlaceholders(player).asMini().legacy())
            customModelData?.let { setCustomModelData(it) }
            lore(lore)
        }
    }

    private fun createMainJournalInventory(player: Player): Inventory {
        val holder = MainJournalInventoryHolder()
        val mainMenu =
            plugin.server.createInventory(holder, 54, mainMenuTitleSnippet.parsePlaceholders(player).asMini())
        holder.setInventory(mainMenu)

        createSimpleButton(
            mainMenu,
            Material.valueOf(mainMenuButtonsActiveType),
            mainMenuButtonsActiveName,
            mainMenuButtonsActivePlace,
            mainMenuButtonsActiveModelData
        )
        createSimpleButton(
            mainMenu,
            Material.valueOf(mainMenuButtonsInactiveType),
            mainMenuButtonsInactiveName,
            mainMenuButtonsInactivePlace,
            mainMenuButtonsInactiveModelData
        )
        createSimpleButton(
            mainMenu,
            Material.valueOf(mainMenuButtonsCompletedType),
            mainMenuButtonsCompletedName,
            mainMenuButtonsCompletedPlace,
            mainMenuButtonsCompletedModelData
        )

        return mainMenu
    }

    private fun createQuestsJournalInventory(player: Player, status: QuestStatus, title: String): Inventory {
        val holder = QuestsJournalInventoryHolder(status)
        val questsMenu = plugin.server.createInventory(holder, 54, title.parsePlaceholders(player).asMini())
        holder.setInventory(questsMenu)

        val currentPage = Journal.playerPages[player.uniqueId] ?: 1
        val quests = Query.find<QuestEntry>().filter { it.questStatus(player) == status }.toList()
        val startIndex = (currentPage - 1) * 45
        val endIndex = minOf(startIndex + 45, quests.size)

        if (startIndex < quests.size) {
            quests.subList(startIndex, endIndex).forEachIndexed { index, quest ->
                val loreComponents = buildList<Component> {
                    val objectives = quest.children.descendants(ObjectiveEntry::class).mapNotNull { it.get() }
                    val desc = quest.children.descendants(LinesEntry::class).mapNotNull { it.get() }
                    val displayObjectives = objectives.filter { player.inAudience(it) }

                    if (displayObjectives.isNotEmpty()) {
                        displayObjectives.forEach {
                            addAll(
                                it.display(player).parsePlaceholders(player).limitLineLength(30).splitComponents()
                            )
                        }
                    } else if (desc.isNotEmpty()) {
                        val description = desc.joinToString("\n") { it.lines(player).parsePlaceholders(player) }
                        addAll(description.parsePlaceholders(player).limitLineLength(30).splitComponents())
                    } else {
                        add(Component.text("<red>No description available.".asMini().legacy()))
                    }
                }
                questsMenu.setItem(
                    index,
                    createQuestButton(
                        player,
                        quest,
                        Material.valueOf(questMenuButtonQuestType),
                        loreComponents,
                        questMenuButtonQuestModelData
                    )
                )
            }
        }

        createSimpleButton(
            questsMenu,
            Material.valueOf(questMenuButtonNextType),
            questMenuButtonNextTitle,
            questMenuButtonNextPlace,
            questMenuButtonNextModelData
        )
        createSimpleButton(
            questsMenu,
            Material.valueOf(questMenuButtonLeaveType),
            questMenuButtonLeaveTitle,
            questMenuButtonLeavePlace,
            questMenuButtonLeaveModelData
        )
        createSimpleButton(
            questsMenu,
            Material.valueOf(questMenuButtonPreviousType),
            questMenuButtonPreviousTitle,
            questMenuButtonPreviousPlace,
            questMenuButtonPreviousModelData
        )

        return questsMenu
    }

    object Journal : Listener {
        val playerPages: MutableMap<UUID, Int> = mutableMapOf()
        private val openJournalInstance = OpenJournal()

        @EventHandler
        fun onInventoryClick(event: InventoryClickEvent) {
            val player = event.whoClicked as? Player ?: return
            when (val holder = event.inventory.holder) {
                is MainJournalInventoryHolder -> {
                    event.isCancelled = true
                    when (event.slot) {
                        mainMenuButtonsActivePlace -> {
                            playerPages[player.uniqueId] = 1
                            player.openInventory(
                                openJournalInstance.createQuestsJournalInventory(
                                    player,
                                    QuestStatus.ACTIVE,
                                    questMenuActiveTitleSnippet
                                )
                            )
                        }

                        mainMenuButtonsInactivePlace -> {
                            playerPages[player.uniqueId] = 1
                            player.openInventory(
                                openJournalInstance.createQuestsJournalInventory(
                                    player,
                                    QuestStatus.INACTIVE,
                                    questMenuInactiveTitleSnippet
                                )
                            )
                        }

                        mainMenuButtonsCompletedPlace -> {
                            playerPages[player.uniqueId] = 1
                            player.openInventory(
                                openJournalInstance.createQuestsJournalInventory(
                                    player,
                                    QuestStatus.COMPLETED,
                                    questMenuCompletedTitleSnippet
                                )
                            )
                        }
                    }
                }

                is QuestsJournalInventoryHolder -> {
                    event.isCancelled = true
                    val questStatus = holder.status
                    when (event.slot) {
                        questMenuButtonNextPlace -> {
                            playerPages[player.uniqueId] = (playerPages[player.uniqueId] ?: 1) + 1
                            player.openInventory(
                                openJournalInstance.createQuestsJournalInventory(
                                    player,
                                    questStatus,
                                    event.view.title
                                )
                            )
                        }

                        questMenuButtonLeavePlace -> player.openInventory(
                            openJournalInstance.createMainJournalInventory(
                                player
                            )
                        )

                        questMenuButtonPreviousPlace -> {
                            val currentPage = playerPages[player.uniqueId] ?: 1
                            if (currentPage > 1) {
                                playerPages[player.uniqueId] = currentPage - 1
                                player.openInventory(
                                    openJournalInstance.createQuestsJournalInventory(
                                        player,
                                        questStatus,
                                        event.view.title
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
