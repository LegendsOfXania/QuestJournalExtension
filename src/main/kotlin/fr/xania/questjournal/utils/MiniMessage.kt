package fr.xania.questjournal.utils

import com.typewritermc.engine.paper.utils.asMini
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

fun String.asMiniWithoutItalic(): Component =
    this.asMini().decorations(setOf(TextDecoration.ITALIC), false)