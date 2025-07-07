package fr.xania.questjournal.inventories

import com.typewritermc.core.entries.Query
import com.typewritermc.engine.paper.entry.descendants
import com.typewritermc.engine.paper.entry.entries.LinesEntry
import com.typewritermc.engine.paper.entry.inAudience
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.plugin
import com.typewritermc.engine.paper.utils.asMini
import com.typewritermc.engine.paper.utils.limitLineLength
import com.typewritermc.engine.paper.utils.splitComponents
import com.typewritermc.quest.ObjectiveEntry
import com.typewritermc.quest.QuestEntry
import com.typewritermc.quest.QuestStatus
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.utils.asMiniWithoutItalic
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class QuestJournalInventory(
    val player: Player,
    val status: QuestStatus
) : InventoryHolder {


    private val inventory: Inventory = plugin.server.createInventory(
        this,
        questMenuRows,
        questMenuTitleActive.parsePlaceholders(player).asMini()
    )

    val quests = Query.find<QuestEntry>().filter { it.questStatus(player) == status }.toList()
    val maxQuestsPerPage = inventory.size - 2 // exclude navigation buttons

    var currentPage = 0

    init {
        setupNavigationButtons()
        loadPage(currentPage)
    }

    fun loadPage(page: Int) {
        inventory.clear()
        setupNavigationButtons()

        val availableSlots = (0 until inventory.size).filter {
            it != questMenuButtonPreviousSlot && it != questMenuButtonNextSlot
        }

        val startIndex = page * maxQuestsPerPage
        val endIndex = (startIndex + maxQuestsPerPage).coerceAtMost(quests.size)
        val questsToDisplay = quests.subList(startIndex, endIndex)

        for ((quest, slot) in questsToDisplay.zip(availableSlots)) {
            val lore = buildList {
                val objectives = quest.children.descendants(ObjectiveEntry::class).mapNotNull { it.get() }
                val displayedObjective = objectives.filter { player.inAudience(it) }
                val description = quest.children.descendants(LinesEntry::class).mapNotNull { it.get() }

                if (displayedObjective.isNotEmpty()) {
                    displayedObjective.forEach {
                        addAll(it.display(player).parsePlaceholders(player).limitLineLength(40).splitComponents())
                    }
                } else if (description.isNotEmpty()) {
                    val loreDescription = description.joinToString("\n") { it.lines(player) }
                    addAll(loreDescription.parsePlaceholders(player).limitLineLength(40).splitComponents())
                } else {
                    add(questMenuButtonQuestLore.asMiniWithoutItalic())
                }
            }

            val questButton = ItemStack(Material.getMaterial(questMenuButtonQuestMaterial) ?: Material.BARRIER).apply {
                itemMeta = itemMeta.apply {
                    displayName(quest.displayName.get(player).parsePlaceholders(player).asMiniWithoutItalic())
                    lore(lore)
                    customModelDataComponent.floats.let { questMenuButtonQuestCMD.toFloat() }
                }
            }

            inventory.setItem(slot, questButton)
        }
    }

    private fun setupNavigationButtons() {
        val previousButton = ItemStack(Material.getMaterial(questMenuButtonPreviousMaterial) ?: Material.BARRIER).apply {
            itemMeta = itemMeta.apply {
                displayName(questMenuButtonPreviousName.parsePlaceholders(player).asMiniWithoutItalic())
                lore(questMenuButtonPreviousLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
                customModelDataComponent.floats.let { questMenuButtonPreviousCMD.toFloat() }
            }
        }
        inventory.setItem(questMenuButtonPreviousSlot, previousButton)

        val nextButton = ItemStack(Material.getMaterial(questMenuButtonNextMaterial) ?: Material.BARRIER).apply {
            itemMeta = itemMeta.apply {
                displayName(questMenuButtonNextName.parsePlaceholders(player).asMiniWithoutItalic())
                lore(questMenuButtonNextLore.map { it.parsePlaceholders(player).asMiniWithoutItalic() })
                customModelDataComponent.floats.let { questMenuButtonQuestCMD.toFloat() }
            }
        }
        inventory.setItem(questMenuButtonNextSlot, nextButton)
    }

    override fun getInventory(): Inventory = inventory
}