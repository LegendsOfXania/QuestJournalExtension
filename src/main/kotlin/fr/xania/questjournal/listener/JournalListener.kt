package fr.xania.questjournal.listener

import com.typewritermc.quest.QuestStatus
import fr.xania.questjournal.entries.action.*
import fr.xania.questjournal.inventories.MainJournalInventory
import fr.xania.questjournal.inventories.QuestJournalInventory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class JournalListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        when (val holder = event.inventory.holder) {

            is MainJournalInventory -> {
                event.isCancelled = true

                when (event.slot) {
                    mainMenuButtonActiveSlot -> {
                        if (mainMenuButtonActiveIsEnabled) {
                            player.openInventory(
                                QuestJournalInventory(
                                    player,
                                    QuestStatus.ACTIVE
                                ).inventory
                            )
                        }
                    }
                    mainMenuButtonInactiveSlot -> {
                        if (mainMenuButtonInactiveIsEnabled) {
                            player.openInventory(
                                QuestJournalInventory(
                                    player,
                                    QuestStatus.INACTIVE
                                ).inventory
                            )
                        }
                    }
                    mainMenuButtonCompletedSlot -> {
                        if (mainMenuButtonCompletedIsEnabled) {
                            player.openInventory(
                                QuestJournalInventory(
                                    player,
                                    QuestStatus.COMPLETED
                                ).inventory
                            )
                        }
                    }
                }
            }

            is QuestJournalInventory -> {
                event.isCancelled = true

                when (event.slot) {
                    questMenuButtonPreviousSlot -> {
                        if (holder.currentPage > 0) {
                            holder.loadPage(holder.currentPage - 1)
                            player.openInventory(holder.inventory)
                        } else {
                            player.openInventory(MainJournalInventory(player).inventory)
                        }
                    }

                    questMenuButtonNextSlot -> {
                        val maxPage = (holder.quests.size - 1) / holder.maxQuestsPerPage
                        if (holder.currentPage < maxPage) {
                            holder.loadPage(holder.currentPage + 1)
                            player.openInventory(holder.inventory)
                        }
                    }
                }
            }
        }
    }
}
