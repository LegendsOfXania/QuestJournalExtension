package fr.xania.questjournal.inventoryHolder

import com.typewritermc.quest.QuestStatus
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class QuestsJournalInventoryHolder(val status: QuestStatus) : InventoryHolder {
    private lateinit var inventory: Inventory
    override fun getInventory(): Inventory {
        fun setInventory(inventory: Inventory) {
            this.inventory = inventory
        }
        return inventory
    }
}