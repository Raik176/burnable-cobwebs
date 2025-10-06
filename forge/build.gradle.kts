import me.modmuss50.mpp.platforms.curseforge.CurseforgeOptions
import me.modmuss50.mpp.platforms.modrinth.ModrinthOptions
import net.fabricmc.loom.task.RemapJarTask
import net.fabricmc.loom.task.RemapSourcesJarTask
import org.gradle.kotlin.dsl.withType

plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("me.modmuss50.mod-publish-plugin")
}

val loader = prop("loom.platform")!!
var minecraft: String = stonecutter.current.version
val root: Project = project(":")
val common: Project = requireNotNull(stonecutter.node.sibling("")) {
    "No common project for $project"
}.project

version = "${mod.version}+$minecraft"
group = "${mod.group}.$loader"
base {
    archivesName.set("${mod.id}-$loader")
}
architectury {
    platformSetupLoomIde()
    forge()
}

repositories {
    maven("https://maven.minecraftforge.net")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.officialMojangMappings())
    "forge"("net.minecraftforge:forge:$minecraft-${common.mod.dep("forge_loader")}")
    "io.github.llamalad7:mixinextras-forge:${mod.dep("mixin_extras")}".let {
        implementation(it)
        include(it)
    }

    implementation(project(common.path, "namedElements"))?.let {
        include(it)
    }
}

loom {
    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }

    forge.convertAccessWideners = true
    forge.mixinConfigs(
        "${mod.id}-common.mixins.json"
    )

    runConfigs.all {
        isIdeConfigGenerated = false
        runDir = project.layout.projectDirectory.asFile.toPath().toAbsolutePath()
            .relativize(rootProject.layout.projectDirectory.file("run").asFile.toPath())
            .toString()
        vmArgs("-Dmixin.debug.export=true")
    }
}

java {
    withSourcesJar()
    val java = when {
        stonecutter.eval(minecraft, ">=1.20.5") -> JavaVersion.VERSION_21
        stonecutter.eval(minecraft, ">=1.17") -> JavaVersion.VERSION_17
        else -> JavaVersion.VERSION_1_8
    }
    targetCompatibility = java
    sourceCompatibility = java
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

tasks.withType<RemapJarTask> {
    destinationDirectory = rootProject.layout.buildDirectory.dir("libs/${mod.version}/$loader")
}
tasks.withType<RemapSourcesJarTask> {
    destinationDirectory = rootProject.layout.buildDirectory.dir("libs/${mod.version}/$loader")
}

publishMods {
    file = tasks.remapJar.get().archiveFile
    changelog = rootProject.extra["changelog"] as String
    modLoaders.add("forge")
    type = STABLE
    displayName = "${common.mod.version} for Forge $minecraft"

    modrinth {
        @Suppress("UNCHECKED_CAST")
        (rootProject.extra["configureModrinth"] as (ModrinthOptions) -> Unit)(this)
    }
    curseforge {
        @Suppress("UNCHECKED_CAST")
        (rootProject.extra["configureCurseforge"] as (CurseforgeOptions) -> Unit)(this)
    }
    github {
        accessToken = providers.environmentVariable("GITHUB_TOKEN")

        parent(rootProject.tasks.named("publishGithub"))
    }

    dryRun = providers.environmentVariable("PUBLISH_DRY_RUN").isPresent
}