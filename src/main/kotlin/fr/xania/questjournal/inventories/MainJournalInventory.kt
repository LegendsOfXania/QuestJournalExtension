package fr.xania.questjournal.inventories

import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import com.typewritermc.quest.QuestStatus
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.utils.asMiniWithoutItalic
import org.bukkit.entity.Player


fun mainJournalInventory(player: Player) {
    val mainJournal = Gui.gui()
        .title(mainMenuTitle.parsePlaceholders(player).asMini())
        .rows(mainMenuRows)
        .create()

    val leaveButton = ItemBuilder
        .from(mainMenuButtonLeaveMaterial)
        .name(mainMenuButtonLeaveName.parsePlaceholders(player).asMiniWithoutItalic())
        .lore(mainMenuButtonLeaveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
        .model(mainMenuButtonLeaveCMD)
        .asGuiItem { event -> mainJournal.close(player) }

    val activeButton = ItemBuilder
        .from(mainMenuButtonActiveMaterial)
        .name(mainMenuButtonActiveName.parsePlaceholders(player).asMiniWithoutItalic())
        .lore(mainMenuButtonActiveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
        .model(mainMenuButtonActiveCMD)
        .asGuiItem { event -> questJournalInventory(player, QuestStatus.ACTIVE) }

    val inactiveButton = ItemBuilder
        .from(mainMenuButtonInactiveMaterial)
        .name(mainMenuButtonInactiveName.parsePlaceholders(player).asMiniWithoutItalic())
        .lore(mainMenuButtonInactiveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
        .model(mainMenuButtonInactiveCMD)
        .asGuiItem { event -> questJournalInventory(player, QuestStatus.INACTIVE) }

    val completedButton = ItemBuilder
        .from(mainMenuButtonCompletedMaterial)
        .name(mainMenuButtonCompletedName.parsePlaceholders(player).asMiniWithoutItalic())
        .lore(mainMenuButtonCompletedLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
        .model(mainMenuButtonCompletedCMD)
        .asGuiItem { event -> questJournalInventory(player, QuestStatus.COMPLETED) }

    mainJournal.setItem(mainMenuButtonLeaveSlot, leaveButton)

    mainJournal.setItem(mainMenuButtonActiveSlot, activeButton)
    mainJournal.setItem(mainMenuButtonInactiveSlot, inactiveButton)
    mainJournal.setItem(mainMenuButtonCompletedSlot, completedButton)

    mainJournal.open(player)
}
