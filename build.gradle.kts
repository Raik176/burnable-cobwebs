import com.google.gson.JsonParser
import java.net.HttpURLConnection
import java.net.URL

//common
plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("me.modmuss50.mod-publish-plugin")
}

val minecraft = stonecutter.current.version

version = "${mod.version}+$minecraft"
group = "${mod.group}.common"
base {
    archivesName.set("${mod.id}-common")
}

architectury.common(stonecutter.tree.branches.mapNotNull {
    if (stonecutter.current.project !in it) null
    else it.project.prop("loom.platform")
})

tasks.jar {
    from(rootProject.file("LICENSE")) {
        rename { "${mod.id}_LICENSE.md" }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    "io.github.llamalad7:mixinextras-common:${mod.dep("mixin_extras")}".let {
        annotationProcessor(it)
        implementation(it)
    }
}

loom {
    accessWidenerPath = rootProject.file("src/main/resources/burnable_cobwebs.accesswidener")

    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }
}

java {
    withSourcesJar()
    val java = if (stonecutter.eval(minecraft, ">=1.20.5"))
        JavaVersion.VERSION_21 else JavaVersion.VERSION_17
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.build {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
}

fun fetchModrinthIcon(projectId: String): String? {
    val url = URL("https://api.modrinth.com/v2/project/$projectId")
    return try {
        (url.openConnection() as HttpURLConnection).run {
            requestMethod = "GET"
            connectTimeout = 5000
            readTimeout = 5000

            if (responseCode == 200) {
                inputStream.bufferedReader().use { reader ->
                    val jsonElement = JsonParser.parseReader(reader)
                    jsonElement.asJsonObject.get("icon_url")?.asString
                }
            } else {
                null
            }
        }
    } catch (e: Exception) {
        null
    }
}

publishMods {
    changelog = providers.fileContents(layout.projectDirectory.file("../../CHANGELOG.md")).asText.get()
    type = STABLE
    displayName = "${mod.version} for $minecraft"

    github {
        accessToken = providers.environmentVariable("GITHUB_TOKEN")
        repository = "Raik176/burnable-cobwebs"
        commitish = "master"
        tagName = "v${mod.version}"

        allowEmptyFiles = true
    }

    discord {
        style {
            look = "MODERN"
            link = "BUTTON"
            fetchModrinthIcon("burnable-cobwebs")?.let { thumbnailUrl = it }
            color = "#7B679A"
        }
        webhookUrl = providers.environmentVariable("DISCORD_WEBHOOK")
        dryRunWebhookUrl = providers.environmentVariable("DISCORD_WEBHOOK_DRY_RUN")
        content = changelog.map { "A new version of ${mod.id} has been released! \n" + it }
    }

    dryRun = providers.environmentVariable("PUBLISH_DRY_RUN").isPresent
}