plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")

    id("dev.kikugie.fletching-table")
}

val minecraft = stonecutter.current.version

architectury.common(stonecutter.tree.branches.mapNotNull {
    if (stonecutter.current.project !in it) null
    else it.project.prop("loom.platform")
})

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
}

loom {
    accessWidenerPath = rootProject.file("src/main/resources/${mod.id}.accesswidener")
}