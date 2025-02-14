package fr.xania.questjournal.entries.action

import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Query
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.core.entries.Ref
import com.typewritermc.engine.paper.entry.*
import com.typewritermc.engine.paper.entry.entries.ActionEntry
import com.typewritermc.engine.paper.entry.entries.ActionTrigger
import com.typewritermc.engine.paper.entry.entries.LinesEntry
import com.typewritermc.engine.paper.logger
import com.typewritermc.engine.paper.plugin
import com.typewritermc.engine.paper.snippets.snippet
import com.typewritermc.engine.paper.utils.asMini
import com.typewritermc.quest.ObjectiveEntry
import com.typewritermc.quest.QuestEntry
import com.typewritermc.quest.QuestStatus
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

val miniMessage = MiniMessage.miniMessage()
// Snippets
val mainMenuTitleSnippet: String by snippet("journal.menu.main.title", "Journal de quêtes")
val mainMenuTitlecomponent: Component = miniMessage.deserialize(mainMenuTitleSnippet)
val mainMenuTitle = LegacyComponentSerializer.legacySection().serialize(mainMenuTitlecomponent)

// Titres des menus de quêtes
val questMenuActiveTitleSnippet: String by snippet("journal.menu.active.title", "Quêtes Actives")
val questMenuActiveTitlecomponent: Component = miniMessage.deserialize(questMenuActiveTitleSnippet)
val questMenuActiveTitle = LegacyComponentSerializer.legacySection().serialize(questMenuActiveTitlecomponent)

val questMenuInactiveTitleSnippet: String by snippet("journal.menu.inactive.title", "Quêtes Inactives")
val questMenuInactiveTitlecomponent: Component = miniMessage.deserialize(questMenuInactiveTitleSnippet)
val questMenuInactiveTitle = LegacyComponentSerializer.legacySection().serialize(questMenuInactiveTitlecomponent)

val questMenuCompletedTitleSnippet: String by snippet("journal.menu.completed.title", "Quêtes Complétées")
val questMenuCompletedTitlecomponent: Component = miniMessage.deserialize(questMenuCompletedTitleSnippet)
val questMenuCompletedTitle = LegacyComponentSerializer.legacySection().serialize(questMenuCompletedTitlecomponent)

// Boutons du menu Principal
val mainMenuButtonsActiveName: String by snippet(
    "journal.menu.main.buttons.active.name", "<green>Voir les quêtes actives"
)
val mainMenuButtonsActiveType: String by snippet("journal.menu.main.buttons.active.type", "GREEN_BANNER")
val mainMenuButtonsActiveModelData: Int by snippet("journal.menu.main.buttons.active.model-data", 0)
val mainMenuButtonsActivePlace: Int by snippet("journal.menu.main.buttons.active.place", 20)

val mainMenuButtonsInactiveName: String by snippet(
    "journal.menu.main.buttons.inactive.name", "<yellow>Voir les quêtes inactives"
)
val mainMenuButtonsInactiveType: String by snippet("journal.menu.main.buttons.inactive.type", "YELLOW_BANNER")
val mainMenuButtonsInactiveModelData: Int by snippet("journal.menu.main.buttons.inactive.model-data", 0)
val mainMenuButtonsInactivePlace: Int by snippet("journal.menu.main.buttons.inactive.place", 22)

val mainMenuButtonsCompletedName: String by snippet(
    "journal.menu.main.buttons.completed.name", "<gray>Voir les quêtes complétées"
)
val mainMenuButtonsCompletedType: String by snippet("journal.menu.main.buttons.completed.type", "GRAY_BANNER")
val mainMenuButtonsCompletedModelData: Int by snippet("journal.menu.main.buttons.completed.model-data", 0)
val mainMenuButtonsCompletedPlace: Int by snippet("journal.menu.main.buttons.completed.place", 24)

// Autres snippets
val questMenuButtonQuestType: String by snippet("journal.menu.quest.buttons.quest.type", "WRITTEN_BOOK")
val questMenuButtonQuestModelData: Int by snippet("journal.menu.quest.buttons.quest.model-data", 0)

val questMenuButtonLeaveTitle: String by snippet("journal.menu.quest.buttons.leave.title", "<red>Retour")
val questMenuButtonLeaveType: String by snippet("journal.menu.quest.buttons.leave.type", "BARRIER")
val questMenuButtonLeaveModelData: Int by snippet("journal.menu.quest.buttons.leave.model-data", 0)
val questMenuButtonLeavePlace: Int by snippet("journal.menu.quest.buttons.leave.place", 49)

val questMenuButtonNextTitle: String by snippet("journal.menu.quest.buttons.next.title", "<yellow>Suivant")
val questMenuButtonNextType: String by snippet("journal.menu.quest.buttons.next.type", "ARROW")
val questMenuButtonNextModelData: Int by snippet("journal.menu.quest.buttons.next.model-data", 0)
val questMenuButtonNextPlace: Int by snippet("journal.menu.quest.buttons.next.place", 53)

val questMenuButtonPreviousTitle: String by snippet("journal.menu.quest.buttons.previous.title", "<yellow>Précédent")
val questMenuButtonPreviousType: String by snippet("journal.menu.quest.buttons.previous.type", "ARROW")
val questMenuButtonPreviousModelData: Int by snippet("journal.menu.quest.buttons.previous.model-data", 0)
val questMenuButtonPreviousPlace: Int by snippet("journal.menu.quest.buttons.previous.place", 45)


// Définition de l'action pour ouvrir le journal
@Entry("open_journal_action", "Open the Quest Journal.", Colors.RED, "mdi-light:book-multiple")
class OpenJournalEntry(
    override val id: String = "",
    override val name: String = "",
    override val criteria: List<Criteria> = emptyList(),
    override val modifiers: List<Modifier> = emptyList(),
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
) : ActionEntry {

    override fun ActionTrigger.execute() {
        object : BukkitRunnable() {
            override fun run() {
                try {
                    Journal.openMainMenu(player)
                } catch (e: Exception) {
                    logger.warning("An error occurred when opening the Quest Journal. Please contact the author.")
                }
            }
        }.runTask(plugin)
    }

    object Journal : Listener {

        // Ouvre le menu principal
        fun openMainMenu(player: Player) {
            val gui = Bukkit.createInventory(null, 54, mainMenuTitle)

            gui.setItem(
                mainMenuButtonsActivePlace,
                menuItem(mainMenuButtonsActiveType, mainMenuButtonsActiveName, mainMenuButtonsActiveModelData)
            )
            gui.setItem(
                mainMenuButtonsInactivePlace,
                menuItem(mainMenuButtonsInactiveType, mainMenuButtonsInactiveName, mainMenuButtonsInactiveModelData)
            )
            gui.setItem(
                mainMenuButtonsCompletedPlace,
                menuItem(mainMenuButtonsCompletedType, mainMenuButtonsCompletedName, mainMenuButtonsCompletedModelData)
            )

            player.openInventory(gui)
        }


        private fun openQuestMenu(player: Player, status: QuestStatus, title: String, page: Int = 1) {
            val quests = Query.find<QuestEntry>().filter { it.questStatus(player) == status }
            val gui = Bukkit.createInventory(null, 54, title)

            val questsList = quests.toList()
            val startIndex = (page - 1) * 45
            val endIndex = minOf(startIndex + 45, questsList.size)

            if (startIndex < questsList.size) {
                questsList.subList(startIndex, endIndex).forEachIndexed { index, quest ->

                    val loreComponents = buildList<Component> {
                        val objectives = quest.children.descendants(ObjectiveEntry::class)
                            .mapNotNull { it.get() }

                        val displayObjectives = objectives.filter { objective ->
                            player.inAudience(objective)
                        }

                        if (displayObjectives.isNotEmpty()) {
                            displayObjectives.forEach { line ->
                                val component = line.display(player)
                                val wrappedLines = wrapComponentText(component.asMini(), 30)

                                wrappedLines.forEach { add(it) }
                            }
                        } else {
                            quest.children.descendants(LinesEntry::class)
                                .mapNotNull { it.get()?.lines(player) }
                                .flatMap { it.lines().asIterable() }
                                .forEach { line ->
                                    val component = MiniMessage.miniMessage().deserialize("<gray>$line")
                                    val wrappedLines = wrapComponentText(component, 30, defaultColor = "<gray>")

                                    wrappedLines.forEach { add(it) }
                                }
                        }
                    }

                    gui.setItem(index, questItem(player, quest, loreComponents))
                }
            }

            addNavigationButtons(player, page, gui, status)
            val leaveItem = menuItem(questMenuButtonLeaveType, questMenuButtonLeaveTitle, questMenuButtonLeaveModelData)
            gui.setItem(questMenuButtonLeavePlace, leaveItem)

            player.openInventory(gui)
        }


        // Fonction pour découper un texte tout en conservant la dernière couleur
        fun wrapComponentText(component: Component, maxLength: Int, defaultColor: String? = null): List<Component> {
            val text = MiniMessage.miniMessage().serialize(component).trim()
            val words = text.split(" ")
            val result = mutableListOf<String>()
            var currentLine = StringBuilder()
            var currentColor = defaultColor ?: "" // Définit la couleur par défaut si non spécifiée

            for (word in words) {
                val colorMatch = Regex("<[^>]+>").find(word)
                if (colorMatch != null) {
                    currentColor = colorMatch.value
                    val colorWord = word.replace(colorMatch.value, "")
                    if (currentLine.isNotEmpty() && currentLine.length + colorWord.length + 1 > maxLength) {
                        result.add(currentLine.toString())
                        currentLine = StringBuilder()
                    }
                    currentLine.append(currentColor).append(colorWord)
                } else {
                    if (currentLine.isNotEmpty() && currentLine.length + word.length + 1 > maxLength) {
                        result.add(currentLine.toString())
                        currentLine = StringBuilder()
                    }
                    if (currentLine.isNotEmpty()) currentLine.append(" ")
                    currentLine.append(word)
                }
            }

            if (currentLine.isNotEmpty()) {
                result.add(currentLine.toString()) // Ajoute la dernière ligne construite
            }

            return result.filter { it.isNotEmpty() }
                .map { MiniMessage.miniMessage().deserialize(it) }
        }


        private fun questItem(player: Player, quest: QuestEntry, lore: List<Component>): ItemStack {
            val material = Material.getMaterial(questMenuButtonQuestType) ?: Material.WRITTEN_BOOK

            return ItemStack(material).apply {
                itemMeta = itemMeta?.apply {
                    val miniMessage = MiniMessage.miniMessage()
                    val component: Component = miniMessage.deserialize(quest.displayName.get(player))
                    val formattedName = LegacyComponentSerializer.legacySection().serialize(component)
                    setDisplayName(formattedName)
                    setCustomModelData(questMenuButtonQuestModelData)
                    lore(lore)
                }
            }
        }


        private fun menuItem(materialName: String, name: String, customModelData: Int = 0): ItemStack {
            val material = Material.getMaterial(materialName) ?: Material.BARRIER

            return ItemStack(material).apply {
                itemMeta = itemMeta?.apply {
                    val miniMessage = MiniMessage.miniMessage()
                    val component: Component = miniMessage.deserialize(name)
                    val formattedName = LegacyComponentSerializer.legacySection().serialize(component)
                    setDisplayName(formattedName)
                    if (customModelData > 0) {
                        setCustomModelData(customModelData)
                    }
                }
            }
        }

        private fun addNavigationButtons(
            player: Player,
            page: Int,
            gui: org.bukkit.inventory.Inventory,
            status: QuestStatus
        ) {
            val nextPageItem = menuItem(questMenuButtonNextType, questMenuButtonNextTitle, questMenuButtonNextModelData)
            val prevPageItem =
                menuItem(questMenuButtonPreviousType, questMenuButtonPreviousTitle, questMenuButtonPreviousModelData)

            gui.setItem(questMenuButtonPreviousPlace, prevPageItem)
            gui.setItem(questMenuButtonNextPlace, nextPageItem)
        }

        // Gestion des clics dans l'inventaire
        @EventHandler
        fun onInventoryClick(event: InventoryClickEvent) {
            val player = event.whoClicked as? Player ?: return
            val title = event.view.title
            val clickedItem = event.currentItem ?: return

            event.isCancelled = true

            when {
                title.equals(mainMenuTitle, ignoreCase = true) -> {
                    handleMainMenuClick(player, clickedItem, title)
                }

                title.equals(questMenuActiveTitle, ignoreCase = true) ||
                        title.equals(questMenuInactiveTitle, ignoreCase = true) ||
                        title.equals(questMenuCompletedTitle, ignoreCase = true) -> {
                    handleQuestMenuClick(player, clickedItem, title)
                }

                else -> event.isCancelled = false
            }
        }

        // Traitement des clics dans le menu principal
        private fun handleMainMenuClick(player: Player, clickedItem: ItemStack, title: String) {
            val itemMeta = clickedItem.itemMeta ?: return
            val miniMessage = MiniMessage.miniMessage()

            val buttons = listOf(
                Triple(mainMenuButtonsActiveName, mainMenuButtonsActiveType, mainMenuButtonsActiveModelData),
                Triple(mainMenuButtonsInactiveName, mainMenuButtonsInactiveType, mainMenuButtonsInactiveModelData),
                Triple(mainMenuButtonsCompletedName, mainMenuButtonsCompletedType, mainMenuButtonsCompletedModelData)
            )

            buttons.forEach { (buttonName, buttonType, modelData) ->
                if (clickedItem.type == Material.getMaterial(buttonType) && itemMeta.hasDisplayName() && (modelData == 0 || (itemMeta.hasCustomModelData() && itemMeta.customModelData == modelData))) {
                    val component: Component = miniMessage.deserialize(buttonName)
                    component.let {
                        val formattedName = LegacyComponentSerializer.legacySection().serialize(it)
                        if (formattedName == itemMeta.displayName) {
                            val status = when (buttonName) {
                                mainMenuButtonsActiveName -> QuestStatus.ACTIVE
                                mainMenuButtonsInactiveName -> QuestStatus.INACTIVE
                                mainMenuButtonsCompletedName -> QuestStatus.COMPLETED
                                else -> return
                            }
                            openQuestMenu(player, status, getQuestMenuTitle(status))
                        }
                    }
                }
            }
        }

        // Traitement des clics dans le menu de quêtes
        private fun handleQuestMenuClick(player: Player, clickedItem: ItemStack, title: String) {
            val itemMeta = clickedItem.itemMeta ?: return
            val miniMessage = MiniMessage.miniMessage()

            if (clickedItem.type == Material.getMaterial(questMenuButtonLeaveType)) {
                openMainMenu(player)
            }

            // Vérification des boutons de pagination
            if (clickedItem.type == Material.getMaterial(questMenuButtonNextType) || clickedItem.type == Material.getMaterial(
                    questMenuButtonPreviousType
                )
            ) {
                val displayName = clickedItem.itemMeta?.displayName ?: return
                val page = when {
                    displayName.contains(questMenuButtonNextTitle) -> getNextPage(title)
                    displayName.contains(questMenuButtonPreviousTitle) -> getPreviousPage(title)
                    else -> return
                }

                when (title) {
                    questMenuActiveTitle -> openQuestMenu(player, QuestStatus.ACTIVE, questMenuActiveTitle, page)
                    questMenuInactiveTitle -> openQuestMenu(player, QuestStatus.INACTIVE, questMenuInactiveTitle, page)
                    questMenuCompletedTitle -> openQuestMenu(
                        player,
                        QuestStatus.COMPLETED,
                        questMenuCompletedTitle,
                        page
                    )
                }
            }
        }

        // Obtenir la page suivante
        private fun getNextPage(title: String): Int = 2

        // Obtenir la page précédente
        private fun getPreviousPage(title: String): Int = 1

        // Obtenir le titre du menu des quêtes selon leur statut
        private fun getQuestMenuTitle(status: QuestStatus): String {
            return when (status) {
                QuestStatus.ACTIVE -> questMenuActiveTitle
                QuestStatus.INACTIVE -> questMenuInactiveTitle
                QuestStatus.COMPLETED -> questMenuCompletedTitle
            }
        }
    }
}
