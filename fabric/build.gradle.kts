import me.modmuss50.mpp.platforms.curseforge.CurseforgeOptions
import me.modmuss50.mpp.platforms.modrinth.ModrinthOptions
import net.fabricmc.loom.task.RemapJarTask
import net.fabricmc.loom.task.RemapSourcesJarTask

plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("me.modmuss50.mod-publish-plugin")
}

val loader = prop("loom.platform")!!
val minecraft: String = stonecutter.current.version
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
    fabric()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${common.mod.dep("fabric_api")}")

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
    val split = common.mod.prop("mc_targets").split(" ")
    return ">=${split[0]} <=${split[split.size-1]}"
}

tasks.processResources {
    properties(listOf("fabric.mod.json"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version,
        "description" to mod.prop("description"),
        "author" to mod.prop("author"),
        "license" to mod.prop("license"),
        "minecraft" to convertMinecraftTargets(),
        "group" to mod.group
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
    modLoaders.addAll("fabric", "quilt")
    type = STABLE
    displayName = "${common.mod.version} for Fabric $minecraft"

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