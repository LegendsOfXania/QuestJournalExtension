package fr.xania.questjournal.inventoryHolder

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class MainJournalInventoryHolder : InventoryHolder {
    private lateinit var inventory: Inventory
    override fun getInventory(): Inventory {
        fun setInventory(inventory: Inventory) {
            this.inventory = inventory
        }
        return inventory
    }
}