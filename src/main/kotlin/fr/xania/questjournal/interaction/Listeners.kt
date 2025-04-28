package fr.xania.questjournal.interaction

import com.typewritermc.core.interaction.InteractionContext
import com.typewritermc.engine.paper.entry.entries.EventTrigger
import com.typewritermc.quest.QuestStatus
import fr.xania.questjournal.entries.action.OpenJournal
import fr.xania.questjournal.inventories.createMainJournalInventory
import fr.xania.questjournal.inventories.createQuestsJournalInventory
import fr.xania.questjournal.inventoryHolder.MainJournalInventoryHolder
import fr.xania.questjournal.inventoryHolder.QuestsJournalInventoryHolder
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import java.util.*

class JournalListener : Listener {

    private val pages: MutableMap<UUID, Int> = mutableMapOf()
    private val openJournal = OpenJournal()

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return

        event.isCancelled = true
        when (val holder = event.inventory.holder) {
            is MainJournalInventoryHolder -> {
                when (event.slot) {
                    20 -> {
                        pages[player.uniqueId] = 1
                        val inventory = createQuestsJournalInventory(player, QuestStatus.ACTIVE, openJournal, pages)
                        player.openInventory(inventory)
                    }

                    22 -> {
                        pages[player.uniqueId] = 1
                        val inventory = createQuestsJournalInventory(player, QuestStatus.INACTIVE, openJournal, pages)
                        player.openInventory(inventory)
                    }

                    24 -> {
                        pages[player.uniqueId] = 1
                        val inventory = createQuestsJournalInventory(player, QuestStatus.COMPLETED, openJournal, pages)
                        player.openInventory(inventory)
                    }
                }
            }

            is QuestsJournalInventoryHolder -> {
                val questStatus = holder.status
                when (event.slot) {
                    45 -> {
                        if ((pages[player.uniqueId] ?: 1) <= 1) {
                            return
                        } else {
                            pages[player.uniqueId] = (pages[player.uniqueId] ?: 1) - 1
                            val inventory = createQuestsJournalInventory(player, questStatus, openJournal, pages)
                            player.openInventory(inventory)
                        }
                    }

                    49 -> {
                        val inventory = createMainJournalInventory(openJournal)
                        player.openInventory(inventory)
                    }

                    53 -> {
                        pages[player.uniqueId] = (pages[player.uniqueId] ?: 1) + 1
                        val inventory = createQuestsJournalInventory(player, questStatus, openJournal, pages)
                        player.openInventory(inventory)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {

        if (event.inventory.holder is MainJournalInventoryHolder || event.inventory.holder is QuestsJournalInventoryHolder) {


            val player = event.player as? Player ?: return
            val context = InteractionContext(mapOf())
            val priority = 1
            val openJournal = OpenJournal()
            val eventTriggers = emptyList<EventTrigger>()

            val journalInteraction = JournalInteraction(player, context, priority, openJournal, eventTriggers)
            journalInteraction.closedJournal = true
        }
    }
}