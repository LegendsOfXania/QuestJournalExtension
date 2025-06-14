package fr.xania.questjournal.inventories

import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import com.typewritermc.quest.QuestStatus
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.utils.asMiniWithoutItalic
import org.bukkit.Material
import org.bukkit.entity.Player

fun mainJournalInventory(player: Player, doesQuestTrackedOnClick: Boolean) {

    val mainJournal = Gui.gui()
        .title(mainMenuTitle.parsePlaceholders(player).asMini())
        .rows(mainMenuRows)
        .disableAllInteractions()
        .create()

    if (mainMenuButtonLeaveIsEnabled) {
        val leaveButton = ItemBuilder
            .from(Material.getMaterial(mainMenuButtonLeaveMaterial) ?: Material.BARRIER)
            .name(mainMenuButtonLeaveName.parsePlaceholders(player).asMiniWithoutItalic())
            .lore(mainMenuButtonLeaveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
            .model(mainMenuButtonLeaveCMD)
            .asGuiItem { event -> mainJournal.close(player) }

        mainJournal.setItem(mainMenuButtonLeaveSlot, leaveButton)
    }
    if (mainMenuButtonActiveIsEnabled) {
        val activeButton = ItemBuilder
            .from(Material.getMaterial(mainMenuButtonActiveMaterial) ?: Material.GREEN_BANNER)
            .name(mainMenuButtonActiveName.parsePlaceholders(player).asMiniWithoutItalic())
            .lore(mainMenuButtonActiveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
            .model(mainMenuButtonActiveCMD)
            .asGuiItem { event -> questJournalInventory(player, QuestStatus.ACTIVE, doesQuestTrackedOnClick) }

        mainJournal.setItem(mainMenuButtonActiveSlot, activeButton)
    }
    if (mainMenuButtonInactiveIsEnabled) {
        val inactiveButton = ItemBuilder
            .from(Material.getMaterial(mainMenuButtonInactiveMaterial) ?: Material.RED_BANNER)
            .name(mainMenuButtonInactiveName.parsePlaceholders(player).asMiniWithoutItalic())
            .lore(mainMenuButtonInactiveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
            .model(mainMenuButtonInactiveCMD)
            .asGuiItem { event -> questJournalInventory(player, QuestStatus.INACTIVE, doesQuestTrackedOnClick) }

        mainJournal.setItem(mainMenuButtonInactiveSlot, inactiveButton)
    }
    if (mainMenuButtonCompletedIsEnabled) {
        val completedButton = ItemBuilder
            .from(Material.getMaterial(mainMenuButtonCompletedMaterial) ?: Material.YELLOW_BANNER)
            .name(mainMenuButtonCompletedName.parsePlaceholders(player).asMiniWithoutItalic())
            .lore(mainMenuButtonCompletedLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
            .model(mainMenuButtonCompletedCMD)
            .asGuiItem { event -> questJournalInventory(player, QuestStatus.COMPLETED, doesQuestTrackedOnClick) }

        mainJournal.setItem(mainMenuButtonCompletedSlot, completedButton)
    }
    mainJournal.open(player)
}