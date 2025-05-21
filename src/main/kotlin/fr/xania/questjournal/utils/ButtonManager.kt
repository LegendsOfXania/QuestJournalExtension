package fr.xania.questjournal.utils

import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.quest.QuestEntry
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun createSimpleButton(
    menu: Inventory,
    slot: Int,
    material: Material,
    name: String,
    lore: List<String>,
    customModelData: Float? = null
) {
    val simpleButton = ItemStack(material).apply {
        itemMeta = itemMeta?.apply {
            displayName(name.asMiniwithoutItalic())
            customModelDataComponent.floats.add(customModelData)
            lore(lore.map { it.asMiniwithoutItalic() })
        }
    }
    menu.setItem(slot, simpleButton)
}

fun createQuestButton(
    player: Player,
    quest: QuestEntry,
    material: Material,
    lore: List<Component>,
    customModelData: Float? = null
) = ItemStack(material).apply {
    itemMeta = itemMeta?.apply {
        displayName(quest.displayName.get(player).parsePlaceholders(player).asMiniwithoutItalic())
        customModelDataComponent.floats.add(customModelData)
        lore(lore)
    }
}
