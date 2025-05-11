package fr.xania.questjournal.inventories

import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.plugin
import com.typewritermc.engine.paper.utils.asMini
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.inventoryHolder.MainJournalInventoryHolder
import fr.xania.questjournal.utils.createSimpleButton
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

fun createMainJournalInventory(player: Player): Inventory {

    val menuTitle = mainMenuTitleSnippet.parsePlaceholders(player).asMini()
    val menu = plugin.server.createInventory(MainJournalInventoryHolder(), 54, menuTitle)

    if (mainMenuButtonsActiveEnabled) {
        createSimpleButton(menu,
            mainMenuButtonsActivePlace, Material.getMaterial(mainMenuButtonsActiveType) ?: Material.GREEN_BANNER,
            mainMenuButtonsActiveName,
            mainMenuButtonsActiveLore,
            mainMenuButtonsActiveModelData)
    }

    if (mainMenuButtonsInactiveEnabled) {
        createSimpleButton(menu,
            mainMenuButtonsInactivePlace, Material.getMaterial(mainMenuButtonsInactiveType) ?: Material.YELLOW_BANNER,
            mainMenuButtonsInactiveName,
            mainMenuButtonsInactiveLore,
            mainMenuButtonsInactiveModelData)
    }

    if (mainMenuButtonsCompletedEnabled) {
        createSimpleButton(menu,
            mainMenuButtonsCompletedPlace, Material.getMaterial(mainMenuButtonsCompletedType) ?: Material.GRAY_BANNER,
            mainMenuButtonsCompletedName,
            mainMenuButtonsCompletedLore,
            mainMenuButtonsCompletedModelData)
    }

    return menu
}