import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
    java
    `java-library`

    `maven-publish`

    id("io.papermc.paperweight.userdev") version "1.7.3"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("io.github.goooler.shadow") version "8.1.8"
}

description = " A plugin to make the flow of time in your Minecraft worlds a bit more accurate."

group = "dev.enderman"
version = "0.0.1"

val projectGroupString = group.toString()
val projectVersionString = version.toString()

val javaVersion = 21
val javaVersionEnumMember = JavaVersion.valueOf("VERSION_$javaVersion")

val paperApiMinecraftVersion = "1.21.1"
val paperApiVersion = "$paperApiMinecraftVersion-R0.1-SNAPSHOT"

java {
    sourceCompatibility = javaVersionEnumMember
    targetCompatibility = javaVersionEnumMember

    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
}

repositories {
    mavenCentral()
    maven("https://www.jitpack.io")
}

dependencies {
    paperweight.paperDevBundle(paperApiVersion)

    implementation("dev.jorel", "commandapi-bukkit-shade-mojang-mapped", "9.5.3")
    implementation("net.lingala.zip4j", "zip4j", "2.11.5")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.release.set(javaVersion)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}

bukkitPluginYaml {
    name = "Chronos"
    description = project.description

    authors = listOfNotNull("Esoteric Enderman")

    setVersion(project.version)

    apiVersion = paperApiMinecraftVersion
    main = "${project.group}.minecraft.plugins.time.chronos.ChronosPlugin"

    load = BukkitPluginYaml.PluginLoadOrder.STARTUP
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = projectGroupString
            artifactId = rootProject.name
            version = projectVersionString
        }
    }
}

tasks.named("publishMavenJavaPublicationToMavenLocal") {
    dependsOn(tasks.named("build"))
}
