package fr.xania.questjournal.inventories

import com.typewritermc.core.entries.Query
import com.typewritermc.engine.paper.entry.descendants
import com.typewritermc.engine.paper.entry.entries.LinesEntry
import com.typewritermc.engine.paper.entry.inAudience
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.plugin
import com.typewritermc.engine.paper.utils.asMini
import com.typewritermc.engine.paper.utils.limitLineLength
import com.typewritermc.quest.ObjectiveEntry
import com.typewritermc.quest.QuestEntry
import com.typewritermc.quest.QuestStatus
import com.typewritermc.engine.paper.utils.splitComponents
import fr.xania.questjournal.entries.action.OpenJournal
import fr.xania.questjournal.inventoryHolder.QuestsJournalInventoryHolder
import fr.xania.questjournal.utils.createQuestButton
import fr.xania.questjournal.utils.createSimpleButton
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.util.*

fun createQuestsJournalInventory(player: Player, status: QuestStatus, openJournal: OpenJournal, pages: MutableMap<UUID, Int>): Inventory {

    val menuTitle = openJournal.questsMenuTitle.asMini()
    val menu = plugin.server.createInventory(QuestsJournalInventoryHolder(status), 54, menuTitle)
    val page = pages[player.uniqueId] ?: 1

    val quest = Query.find<QuestEntry>().filter { it.questStatus(player) == status }.toList()

    val sIndex = (page - 1) * 45
    val eIndex = minOf(sIndex + 45, quest.size)

    if (sIndex < quest.size) {
        quest.subList(sIndex, eIndex).forEachIndexed { index, quest ->

            val lore = buildList {

                val objectives = quest.children.descendants(ObjectiveEntry::class).mapNotNull { it.get() }
                val displayedObjective = objectives.filter { player.inAudience(it) }

                val description = quest.children.descendants(LinesEntry::class).mapNotNull { it.get() }

                if (displayedObjective.isNotEmpty()) {
                    displayedObjective.forEach {
                        addAll(
                            it.display(player).parsePlaceholders(player).limitLineLength(40).splitComponents(),
                        )
                    }
                } else if (description.isNotEmpty()) {

                    val loreDescritpion = description.joinToString("\n") { it.lines(player) }
                    addAll(
                        loreDescritpion.parsePlaceholders(player).limitLineLength(40).splitComponents()
                    )
                } else {
                    add(Component.text("<gray>No description available"))
                }
            }
            menu.setItem(
                index,
                createQuestButton(player, quest, Material.WRITTEN_BOOK, lore, customModelData = 0)
            )
        }
    }

    createSimpleButton(menu,
        45, Material.ARROW,
        "<green>Back",
        listOf("<gray>Click to go to the main page."),
        customModelData = 0
    )

    createSimpleButton(menu,
        49, Material.BARRIER,
        "<green>Next Page",
        listOf("<gray>Click to go to the next page."),
        customModelData = 0
    )

    createSimpleButton(menu,
        53, Material.ARROW,
        "<green>Next Page",
        listOf("<gray>Click to go to the next page."),
        customModelData = 0
    )

    return menu
}