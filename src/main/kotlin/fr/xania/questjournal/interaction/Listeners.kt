package fr.xania.questjournal.interaction

import com.typewritermc.quest.QuestStatus
import fr.xania.questjournal.entries.action.*
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
                    mainMenuButtonsActivePlace -> {
                        pages[player.uniqueId] = 1
                        val inventory = createQuestsJournalInventory(player, QuestStatus.ACTIVE, pages)
                        player.openInventory(inventory)
                    }

                    mainMenuButtonsInactivePlace -> {
                        pages[player.uniqueId] = 1
                        val inventory = createQuestsJournalInventory(player, QuestStatus.INACTIVE, pages)
                        player.openInventory(inventory)
                    }

                    mainMenuButtonsCompletedPlace -> {
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
                    questMenuButtonPreviousPlace -> {
                        if ((pages[player.uniqueId] ?: 1) <= 1) {
                            return
                        } else {
                            pages[player.uniqueId] = (pages[player.uniqueId] ?: 1) - 1
                            val inventory = createQuestsJournalInventory(player, questStatus, pages)
                            player.openInventory(inventory)
                        }
                    }

                    questMenuButtonLeavePlace -> {
                        val inventory = createMainJournalInventory(player)
                        player.openInventory(inventory)
                    }

                    questMenuButtonNextPlace -> {
                        pages[player.uniqueId] = (pages[player.uniqueId] ?: 1) + 1
                        val inventory = createQuestsJournalInventory(player, questStatus, pages)
                        player.openInventory(inventory)
                    }
                }
            }
        }
    }
}