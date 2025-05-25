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
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.utils.asMiniwithoutItalic
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.util.*

fun createQuestsJournalInventory(player: Player, status: QuestStatus, pages: MutableMap<UUID, Int>): Inventory {

    val menuTitle = questMenuActiveTitleSnippet.parsePlaceholders(player).asMini()
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

                    val loreDescription = description.joinToString("\n") { it.lines(player) }
                    addAll(
                        loreDescription.parsePlaceholders(player).limitLineLength(40).splitComponents()
                    )
                } else {
                    addAll(
                        questMenuButtonQuestLore.map { it.parsePlaceholders(player).asMiniwithoutItalic() }
                    )
                }
            }
            menu.setItem(
                index,
                createQuestButton(player, quest, Material.getMaterial(questMenuButtonQuestType) ?: Material.WRITTEN_BOOK, lore, questMenuButtonQuestModelData.toFloat())
            )
        }
    }

    createSimpleButton(menu,
        questMenuButtonPreviousPlace, Material.getMaterial(questMenuButtonPreviousType) ?: Material.ARROW,
        questMenuButtonPreviousTitle,
        questMenuButtonPreviousLore,
        questMenuButtonPreviousModelData.toFloat()
    )

    createSimpleButton(menu,
        questMenuButtonLeavePlace, Material.getMaterial(questMenuButtonLeaveType) ?: Material.BARRIER,
        questMenuButtonLeaveTitle,
        questMenuButtonLeaveLore,
        questMenuButtonLeaveModelData.toFloat()
    )

    createSimpleButton(menu,
        questMenuButtonNextPlace, Material.getMaterial(questMenuButtonNextType) ?: Material.ARROW,
        questMenuButtonNextTitle,
        questMenuButtonNextLore,
        questMenuButtonNextModelData.toFloat()
    )

    return menu
}