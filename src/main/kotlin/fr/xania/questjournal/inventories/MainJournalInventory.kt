package fr.xania.questjournal.inventories

import com.typewritermc.engine.paper.plugin
import fr.xania.questjournal.entries.action.OpenJournal
import fr.xania.questjournal.inventoryHolder.MainJournalInventoryHolder
import fr.xania.questjournal.utils.createSimpleButton
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.Inventory

fun createMainJournalInventory(openJournal: OpenJournal): Inventory {

    val menuTitle = Component.text(openJournal.mainMenuTitle)
    val menu = plugin.server.createInventory(MainJournalInventoryHolder(), 54, menuTitle)

    createSimpleButton(menu,
        20, Material.EMERALD,
        Component.text("<green>Actives Quests"),
        listOf(Component.text("<gray>Click to open your quests"), Component.text("<gray>and see your progress")),
        customModelData = 1)

    createSimpleButton(menu,
        22, Material.REDSTONE,
        Component.text("<green>Inactives Quests"),
        listOf(Component.text("<gray>Click to open your quests"), Component.text("<gray>and see your progress")),
        customModelData = 2)

    createSimpleButton(menu,
        24, Material.DIAMOND,
        Component.text("<green>Completed Quests"),
        listOf(Component.text("<gray>Click to open your quests"), Component.text("<gray>and see your progress")),
        customModelData = 3)

    return menu
}