package fr.xania.questjournal.inventories

import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import com.typewritermc.quest.QuestStatus
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import fr.xania.questjournal.entries.action.*
import org.bukkit.Material
import org.bukkit.entity.Player


fun mainJournalInventory(player: Player) {
    val mainJournal = Gui.gui()
        .title(mainMenuTitle.parsePlaceholders(player).asMini())
        .rows(mainMenuRows)
        .create()

    val leaveButton = ItemBuilder
        .from(Material.getMaterial(mainMenuButtonLeaveMaterial) ?: Material.BARRIER)
        .name(mainMenuButtonLeaveName.parsePlaceholders(player).asMini())
        .lore(mainMenuButtonLeaveLore.map { it.parsePlaceholders(player).asMini() })
        .model(mainMenuButtonLeaveCMD)
        .asGuiItem { event -> mainJournal.close(player) }

    val activeButton = ItemBuilder
        .from(Material.getMaterial(mainMenuButtonActiveMaterial) ?: Material.GREEN_BANNER)
        .name(mainMenuButtonActiveName.parsePlaceholders(player).asMini())
        .lore(mainMenuButtonActiveLore.map { it.parsePlaceholders(player).asMini() })
        .model(mainMenuButtonActiveCMD)
        .asGuiItem { event -> questJournalInventory(player, QuestStatus.ACTIVE) }

    val inactiveButton = ItemBuilder
        .from(Material.getMaterial(mainMenuButtonInactiveMaterial) ?:Material.RED_BANNER)
        .name(mainMenuButtonInactiveName.parsePlaceholders(player).asMini())
        .lore(mainMenuButtonInactiveLore.map { it.parsePlaceholders(player).asMini() })
        .model(mainMenuButtonInactiveCMD)
        .asGuiItem { event -> questJournalInventory(player, QuestStatus.INACTIVE) }

    val completedButton = ItemBuilder
        .from(Material.getMaterial(mainMenuButtonCompletedMaterial) ?: Material.YELLOW_BANNER)
        .name(mainMenuButtonCompletedName.parsePlaceholders(player).asMini())
        .lore(mainMenuButtonCompletedLore.map { it.parsePlaceholders(player).asMini() })
        .model(mainMenuButtonCompletedCMD)
        .asGuiItem { event -> questJournalInventory(player, QuestStatus.COMPLETED) }

    mainJournal.setItem(mainMenuButtonLeaveSlot, leaveButton)

    mainJournal.setItem(mainMenuButtonActiveSlot, activeButton)
    mainJournal.setItem(mainMenuButtonInactiveSlot, inactiveButton)
    mainJournal.setItem(mainMenuButtonCompletedSlot, completedButton)

    mainJournal.open(player)
}
