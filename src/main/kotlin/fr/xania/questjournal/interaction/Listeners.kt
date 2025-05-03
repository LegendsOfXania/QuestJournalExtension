package fr.xania.questjournal.interaction

import com.typewritermc.quest.QuestStatus
import fr.xania.questjournal.inventories.createMainJournalInventory
import fr.xania.questjournal.inventories.createQuestsJournalInventory
import fr.xania.questjournal.inventoryHolder.MainJournalInventoryHolder
import fr.xania.questjournal.inventoryHolder.QuestsJournalInventoryHolder
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

class JournalListener : Listener {

    private val pages: MutableMap<UUID, Int> = mutableMapOf()

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return

        when (val holder = event.inventory.holder) {
            is MainJournalInventoryHolder -> {
                event.isCancelled = true
                when (event.slot) {
                    20 -> {
                        pages[player.uniqueId] = 1
                        val inventory = createQuestsJournalInventory(player, QuestStatus.ACTIVE, pages)
                        player.openInventory(inventory)
                    }

                    22 -> {
                        pages[player.uniqueId] = 1
                        val inventory = createQuestsJournalInventory(player, QuestStatus.INACTIVE, pages)
                        player.openInventory(inventory)
                    }

                    24 -> {
                        pages[player.uniqueId] = 1
                        val inventory = createQuestsJournalInventory(player, QuestStatus.COMPLETED, pages)
                        player.openInventory(inventory)
                    }
                }
            }

            is QuestsJournalInventoryHolder -> {
                val questStatus = holder.status
                event.isCancelled = true
                when (event.slot) {
                    45 -> {
                        if ((pages[player.uniqueId] ?: 1) <= 1) {
                            return
                        } else {
                            pages[player.uniqueId] = (pages[player.uniqueId] ?: 1) - 1
                            val inventory = createQuestsJournalInventory(player, questStatus, pages)
                            player.openInventory(inventory)
                        }
                    }

                    49 -> {
                        val inventory = createMainJournalInventory(player)
                        player.openInventory(inventory)
                    }

                    53 -> {
                        pages[player.uniqueId] = (pages[player.uniqueId] ?: 1) + 1
                        val inventory = createQuestsJournalInventory(player, questStatus, pages)
                        player.openInventory(inventory)
                    }
                }
            }
        }
    }
}