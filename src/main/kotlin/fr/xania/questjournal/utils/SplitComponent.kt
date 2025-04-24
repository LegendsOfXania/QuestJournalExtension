package fr.xania.questjournal.utils

import com.typewritermc.engine.paper.utils.asMiniWithResolvers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

fun String.splitComponents(vararg resolvers: TagResolver): List<Component> {
    val components = split("\n").map { it.asMiniWithResolvers(*resolvers) }.toMutableList()

    for (i in 1 until components.size) {
        val previous = components[i - 1]
        val current = components[i]

        val mergedStyle = current.style().merge(previous.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)
        components[i] = current.style(mergedStyle)
    }
    return components
}