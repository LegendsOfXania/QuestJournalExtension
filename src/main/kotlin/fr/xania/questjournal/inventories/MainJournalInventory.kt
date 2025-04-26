package fr.xania.questjournal.inventories

import com.typewritermc.engine.paper.plugin
import com.typewritermc.engine.paper.utils.asMini
import fr.xania.questjournal.entries.action.OpenJournal
import fr.xania.questjournal.inventoryHolder.MainJournalInventoryHolder
import fr.xania.questjournal.utils.createSimpleButton
import org.bukkit.Material
import org.bukkit.inventory.Inventory

fun createMainJournalInventory(openJournal: OpenJournal): Inventory {

    val menuTitle = openJournal.mainMenuTitle.asMini()
    val menu = plugin.server.createInventory(MainJournalInventoryHolder(), 54, menuTitle)

    createSimpleButton(menu,
        20, Material.EMERALD,
        "<green>Actives Quests",
        listOf("<gray>Click to open your quests", "<gray>and see your progress"),
        customModelData = 1)

    createSimpleButton(menu,
        22, Material.REDSTONE,
        "<green>Inactives Quests",
        listOf("<gray>Click to open your quests", "<gray>and see your progress"),
        customModelData = 2)

    createSimpleButton(menu,
        24, Material.DIAMOND,
        "<green>Completed Quests",
        listOf("<gray>Click to open your quests", "<gray>and see your progress"),
        customModelData = 3)

    return menu
}