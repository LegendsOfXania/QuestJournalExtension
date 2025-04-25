package fr.xania.questjournal.utils

import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
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
    name: Component,
    lore: List<Component>,
    customModelData: Int? = null
) {
    val simpleButton = ItemStack(material).apply {
        itemMeta = itemMeta?.apply {
            displayName(name)
            customModelData?.let { setCustomModelData(it) }
            lore(lore)
        }
    }
    menu.setItem(slot, simpleButton)
}

fun createQuestButton(
    player: Player,
    quest: QuestEntry,
    material: Material,
    lore: List<Component>,
    customModelData: Int? = null
) = ItemStack(material).apply {
    itemMeta = itemMeta?.apply {
        displayName(quest.displayName.get(player).parsePlaceholders(player).asMini())
        customModelData?.let { setCustomModelData(it) }
        lore(lore)
    }
}
