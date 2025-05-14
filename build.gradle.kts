repositories {}
dependencies {
    implementation("com.typewritermc:QuestExtension:0.8.0")
}

plugins {
    kotlin("jvm") version "2.0.21"
    id("com.typewritermc.module-plugin") version "1.2.0"
}

group = "fr.xania"
version = "0.8.0"

typewriter {
    namespace = "legendsofxania"

    extension {
        name = "Journal"
        shortDescription = "Create a Quest journal in TypeWriter"
        description = """
            |A quest journal for Typewriter that allows players to view and manage their quests
            |in a single menu, organized by status and tracking progress.
            |Create by the Legends of Xania.
            """.trimMargin()
        engineVersion = "0.9.0-beta-159"
        channel = com.typewritermc.moduleplugin.ReleaseChannel.BETA

        dependencies {
            dependency("typewritermc", "Quest")
            paper()
        }
    }
}
kotlin {
    jvmToolchain(21)
}