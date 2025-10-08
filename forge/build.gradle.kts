plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("me.modmuss50.mod-publish-plugin")

    id("dev.kikugie.fletching-table.lexforge")
}

var minecraft: String = stonecutter.current.version
val common: Project = requireNotNull(stonecutter.node.sibling("")) {
    "No common project for $project"
}.project

architectury {
    platformSetupLoomIde()
    forge()
}

repositories {
    maven("https://maven.minecraftforge.net")
}

dependencies {
    "forge"("net.minecraftforge:forge:$minecraft-${common.mod.dep("forge_loader")}")
}

fun convertMinecraftTargets(): String {
    return "[" + common.mod.prop("mc_targets").split(" ").joinToString(", ") + "]"
}

tasks.processResources {
    properties(listOf("META-INF/mods.toml", "pack.mcmeta"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version,
        "description" to mod.prop("description"),
        "author" to mod.prop("author"),
        "license" to mod.prop("license"),
        "minecraft" to convertMinecraftTargets()
    )
}

publishMods {
    modLoaders.add("forge")
    displayName = "${common.mod.version} for Forge $minecraft"
}