package fr.xania.questjournal.inventories

import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.plugin
import com.typewritermc.engine.paper.utils.asMini
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.utils.asMiniWithoutItalic
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class MainJournalInventory(
    private val player: Player,
) : InventoryHolder {

    private val inventory = plugin.server.createInventory(
        this,
        mainMenuRows,
        mainMenuTitle.parsePlaceholders(player).asMini()
    )

    init {

        if (mainMenuButtonActiveIsEnabled) {
            val activeButton = ItemStack(Material.getMaterial(mainMenuButtonActiveMaterial) ?: Material.BARRIER).apply {
                itemMeta = itemMeta.apply {
                    displayName(mainMenuButtonActiveName.parsePlaceholders(player).asMiniWithoutItalic())
                    lore(mainMenuButtonActiveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
                    customModelData.let { mainMenuButtonActiveCMD }
                }
            }
            inventory.setItem(mainMenuButtonActiveSlot, activeButton)
        }

        if (mainMenuButtonInactiveIsEnabled) {
            val inactiveButton = ItemStack(Material.getMaterial(mainMenuButtonInactiveMaterial) ?: Material.BARRIER).apply {
                itemMeta = itemMeta.apply {
                    displayName(mainMenuButtonInactiveName.parsePlaceholders(player).asMiniWithoutItalic())
                    lore(mainMenuButtonInactiveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
                    customModelData.let { mainMenuButtonInactiveCMD }
                }
            }
            inventory.setItem(mainMenuButtonInactiveSlot, inactiveButton)
        }

        if (mainMenuButtonCompletedIsEnabled) {
            val completedButton = ItemStack(Material.getMaterial(mainMenuButtonCompletedMaterial) ?: Material.BARRIER).apply {
                itemMeta = itemMeta.apply {
                    displayName(mainMenuButtonCompletedName.parsePlaceholders(player).asMiniWithoutItalic())
                    lore(mainMenuButtonCompletedLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
                    customModelData.let { mainMenuButtonCompletedCMD }
                }
            }
            inventory.setItem(mainMenuButtonCompletedSlot, completedButton)
        }
    }


    override fun getInventory() = inventory
}