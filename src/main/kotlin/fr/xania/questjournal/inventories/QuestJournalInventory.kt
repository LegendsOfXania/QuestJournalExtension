package fr.xania.questjournal.inventories

import com.typewritermc.core.entries.Query
import com.typewritermc.engine.paper.entry.descendants
import com.typewritermc.engine.paper.entry.entries.LinesEntry
import com.typewritermc.engine.paper.entry.inAudience
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import com.typewritermc.engine.paper.utils.limitLineLength
import com.typewritermc.engine.paper.utils.splitComponents
import com.typewritermc.quest.ObjectiveEntry
import com.typewritermc.quest.QuestEntry
import com.typewritermc.quest.QuestStatus
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.utils.asMiniWithoutItalic
import org.bukkit.entity.Player

fun questJournalInventory(player: Player, status: QuestStatus) {

    val questsJournal = Gui.paginated()
        .title(
            when (status) {
                QuestStatus.ACTIVE -> questMenuTitleActive.parsePlaceholders(player).asMini()
                QuestStatus.INACTIVE -> questMenuTitleInactive.parsePlaceholders(player).asMini()
                QuestStatus.COMPLETED -> questMenuTitleCompleted.parsePlaceholders(player).asMini()
            }
        )
        .rows(questMenuRows)
        .create()

    val previousButton = ItemBuilder
        .from(questMenuButtonPreviousMaterial)
        .name(questMenuButtonPreviousName.parsePlaceholders(player).asMiniWithoutItalic())
        .lore(questMenuButtonPreviousLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
        .model(questMenuButtonPreviousCMD)
        .asGuiItem { event -> questsJournal.previous()}

    val leaveButton = ItemBuilder
        .from(questMenuButtonLeaveMaterial)
        .name(questMenuButtonLeaveName.parsePlaceholders(player).asMiniWithoutItalic())
        .lore(questMenuButtonLeaveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
        .model(questMenuButtonLeaveCMD)
        .asGuiItem { event -> questsJournal.close(player) }

    val nextButton = ItemBuilder
        .from(questMenuButtonNextMaterial)
        .name(questMenuButtonNextName.parsePlaceholders(player).asMiniWithoutItalic())
        .lore(questMenuButtonNextLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
        .model(questMenuButtonNextCMD)
        .asGuiItem { event -> questsJournal.next()}

    questsJournal.setItem(questMenuButtonPreviousSlot, previousButton)
    questsJournal.setItem(questMenuButtonLeaveSlot, leaveButton)
    questsJournal.setItem(questMenuButtonNextSlot, nextButton)

    val quests = Query.find<QuestEntry>().filter { it.questStatus(player) == status }.toList()
    quests.forEachIndexed { index, quest ->
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
                    questMenuButtonQuestLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() }
                )
            }
        }
        val questButton = ItemBuilder
            .from(questMenuButtonQuestMaterial)
            .name(quest.displayName.get(player).parsePlaceholders(player).asMiniWithoutItalic())
            .lore(lore)
            .model(questMenuButtonQuestCMD)
            .asGuiItem()

        questsJournal.addItem(questButton)
    }
}