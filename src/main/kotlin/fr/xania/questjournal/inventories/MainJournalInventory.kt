package fr.xania.questjournal.inventories

import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.plugin
import com.typewritermc.engine.paper.utils.asMini
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.utils.asMiniWithoutItalic
import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.CustomModelData
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
                }
                setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addFloat(mainMenuButtonActiveCMD.toFloat()))
            }
            inventory.setItem(mainMenuButtonActiveSlot, activeButton)
        }

        if (mainMenuButtonInactiveIsEnabled) {
            val inactiveButton = ItemStack(Material.getMaterial(mainMenuButtonInactiveMaterial) ?: Material.BARRIER).apply {
                itemMeta = itemMeta.apply {
                    displayName(mainMenuButtonInactiveName.parsePlaceholders(player).asMiniWithoutItalic())
                    lore(mainMenuButtonInactiveLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
                }
                setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addFloat(mainMenuButtonInactiveCMD.toFloat()))
            }
            inventory.setItem(mainMenuButtonInactiveSlot, inactiveButton)
        }

        if (mainMenuButtonCompletedIsEnabled) {
            val completedButton = ItemStack(Material.getMaterial(mainMenuButtonCompletedMaterial) ?: Material.BARRIER).apply {
                itemMeta = itemMeta.apply {
                    displayName(mainMenuButtonCompletedName.parsePlaceholders(player).asMiniWithoutItalic())
                    lore(mainMenuButtonCompletedLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
                }
                setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addFloat(mainMenuButtonCompletedCMD.toFloat()))
            }
            inventory.setItem(mainMenuButtonCompletedSlot, completedButton)
        }
    }


    override fun getInventory() = inventory
}