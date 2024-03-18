import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    java
    id("org.openjfx.javafxplugin") version "0.1.0"
    kotlin("jvm") version "1.9.22"
    application
    id("org.beryx.jlink") version "3.0.1"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.openjdk.nashorn:nashorn-core:15.4")
}

group = "com.github.lure0xaos.lilithslegacy"
version = "0.5.0"
description = "Lilith's Throne"

javafx {
    version = "21.0.2"
    modules = listOf("javafx.base", "javafx.fxml", "javafx.graphics", "javafx.controls", "javafx.media", "javafx.web")
}

application {
    mainModule.set("liliths.legacy")
    mainClass.set("com.lilithslegacy.Launcher")
}

java {
    toolchain {
        this.languageVersion.set(JavaLanguageVersion.of(21))
    }
    sourceCompatibility = JavaVersion.VERSION_21
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = "UTF-8"
}

tasks.register<Jar>("zipResources") {
    exclude("com/lilithslegacy/main/logging.properties")
    from(sourceSets.main.get().resources)
    destinationDirectory.set(layout.buildDirectory.dir("resources/main").get().dir("com/lilithslegacy/utils/fs"))
    archiveFileName.set("res.jar")
}

tasks.processResources {
    include("com/lilithslegacy/main/logging.properties", "com/lilithslegacy/main/loading.png")
    finalizedBy(tasks.named("zipResources"))
    tasks.named("zipResources").get().mustRunAfter(this)
}

tasks.jar {
    dependsOn(tasks.named("zipResources"))
}

tasks.run.configure { dependsOn(tasks.named("zipResources")) }

jlink {
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "Lilith's Legacy"
        jvmArgs = listOf()
        addExtraDependencies(
            "javafx.base",
            "javafx.fxml",
            "javafx.graphics",
            "javafx.controls",
            "javafx.media",
            "javafx.web"
        )
        noConsole = true
    }
    jpackage {
        val os: OperatingSystem = DefaultNativePlatform.getCurrentOperatingSystem()
        icon = "src/main/resources/com/lilithslegacy/res/images/windowIcon32" + when {
            os.isWindows -> ".ico"
            else -> ".png"
        }
        vendor = "Lure of Chaos"
        installerOptions = when {
            os.isWindows -> listOf(
                "--win-per-user-install",
                "--win-dir-chooser",
                "--win-menu",
                "--win-menu-group",
                "Lilith's Legacy",
                "--win-shortcut",
                "--win-shortcut-prompt"
            )

            os.isLinux -> listOf(
                "--linux-menu-group",
                "Lilith's Legacy",
                "--linux-shortcut"
            )

            else -> listOf()
        }
    }
}

tasks.jlink {
    dependsOn(tasks.build)
}

private fun File.toLinkedString(): String =
    toURI().toURL().toExternalForm().replace("file:/", "file:///")

private fun java.nio.file.Path.toLinkedString(): String =
    toFile().toURI().toURL().toExternalForm().replace("file:/", "file:///")

tasks.jpackage {
    doLast {
        println("$name output is: ${jpackageData.imageOutputDir.toLinkedString()}")
    }
}
