plugins {
    java
    `maven-publish`
    alias(libs.plugins.org.openjfx.javafxplugin)
    application
    alias(libs.plugins.org.beryx.runtime)
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(libs.org.openjfx.javafx.base)
    implementation(libs.org.openjfx.javafx.fxml)
    implementation(libs.org.openjfx.javafx.web)
    implementation(libs.org.openjfx.javafx.graphics)
    implementation(libs.org.openjfx.javafx.controls)
    implementation(libs.org.openjfx.javafx.media)
    implementation(libs.org.openjdk.nashorn.nashorn.core)
    implementation(libs.junit.jupiter)
}

group = "innoxia"
version = "0.4.11.3"
description = "Lilith's Throne"
java.sourceCompatibility = JavaVersion.VERSION_11

private fun File.asLink() =
    toPath().toUri().toURL().toExternalForm().replace("'", "%27")

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

sourceSets {
    main {
        java {
            srcDir("src")
        }
        resources {
            srcDirs(listOf("res", "srcres", "extres"))
        }
    }
}

javafx {
    version = libs.versions.org.openjfx.javafx.get()
    modules = listOf(
        "javafx.base",
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics",
        "javafx.media",
        "javafx.web",
    )
}

application {
    mainClass = "com.lilithsthrone.Launcher"
    applicationDefaultJvmArgs = listOf("--enable-native-access=javafx.graphics,javafx.web")
}

runtime {
    modules = listOf(
        "java.xml",
        "jdk.xml.dom",
        "java.desktop",
        "java.scripting",
        "jdk.jsobject",
        "jdk.dynalink",
        "jdk.unsupported"
    )
    options = listOf("--no-header-files", "--no-man-pages", "--strip-debug")
    launcher {
        jvmArgs = project.extensions.getByType<JavaApplication>().applicationDefaultJvmArgs.toList()
    }
    jpackage {
        imageName = project.description
        jpackageHome = org.gradle.internal.jvm.Jvm.current().javaHome.path
        installerName = project.description
        installerType = project.findProperty("installerType")?.toString() ?: "msi"
        when (installerType) {
            in listOf("exe", "msi") -> {
                imageOptions = listOf("--icon", "icon.ico")
                installerOptions = listOf(
                    "--name", project.description,
                    "--description", project.description,
                    "--copyright", project.group.toString(),
                    "--win-menu",
                    "--win-shortcut",
                    "--win-shortcut-prompt",
                    "--win-menu-group", project.group.toString(),
                    "--win-per-user-install", "--win-dir-chooser",
                )
            }

            in listOf("deb", "rpm") -> {
                imageOptions = listOf("--icon", "icon.png")
                installerOptions = listOf(
                    "--name", project.description,
                    "--description", project.description,
                    "--copyright", project.group.toString(),
                    "--linux-app-category", "Game",
                    "--linux-menu-group", project.group.toString(),
                    "--linux-shortcut"
                )
            }

            in listOf("pkg", "dmg") -> {
                imageOptions = listOf("--icon", "icon.icns")
                installerOptions = listOf(
                    "--name", project.description,
                    "--description", project.description,
                    "--copyright", project.group.toString(),
                    "--mac-package-name", project.description,
                )
            }
        }
    }
}

private val createResourcesPropertiesFileTask = tasks.register<WriteProperties>("createResourcesPropertiesFileTask") {
    group = tasks.processResources.get().group
    destinationFile.set(project.layout.buildDirectory.file("generated/properties/META-INF/resources.properties"))
    project.sourceSets.main.get().resources.let { resources ->
        (resources.files to resources.sourceDirectories.map { f -> f.path }).let { (files, sourceDirectories) ->
            files.asSequence().map {
                sourceDirectories.fold(it.path) { resPath, prefix ->
                    resPath.removePrefix(prefix).removePrefix(File.separator)
                }.replace(File.separatorChar, '/')
            }.filterNot { it.startsWith("META-INF/") }
                .flatMap { generateSequence(it) { p -> if ('/' in p) p.substringBeforeLast('/') else null } }
                .distinct()
                .sorted()
                .groupBy { it.substringBeforeLast('/') }
                .mapValues { (key, value) -> value.joinToString(File.pathSeparator) }.toSortedMap()
                .forEach { (key, value) -> property(key, value) }
        }
    }
}

private val createMetaPropertiesFileTask = tasks.register<WriteProperties>("createMetaPropertiesFileTask") {
    group = tasks.processResources.get().group
    destinationFile.set(project.layout.buildDirectory.file("generated/properties/META-INF/meta.properties"))
    property("name", project.name)
    property("group", project.group)
    property("description", project.description ?: "")
    property("version", project.version)
    project.extra.properties.forEach { (key, value) ->
        if (listOf("org.gradle.").none { key.startsWith(it) })
            property(key, value)
    }
}

sourceSets {
    main {
        resources {
            srcDirs(
                setOf(
                    createMetaPropertiesFileTask.map { it.destinationFile.get().asFile.parentFile.parentFile },
                    createResourcesPropertiesFileTask.map { it.destinationFile.get().asFile.parentFile.parentFile },
                )
            )
        }
    }
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to project.extensions.getByType<JavaApplication>().mainClass
        )
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    doLast {
        println("JAR created at ${destinationDirectory.get().asFile.asLink()} as ${archiveFile.get().asFile.asLink()}")
    }
}

tasks.register<JavaExec>("runJar") {
    group = tasks.jar.get().group
    dependsOn(tasks.jar)
    classpath = project.files(tasks.jar)
    workingDir = file(System.getProperty("user.home") ?: error("no user"))
}

tasks.jpackage {
    doLast {
        val jPackageData =
            project.extensions.getByType<org.beryx.runtime.data.RuntimePluginExtension>().jpackageData.get()
        val outDirFile = project.layout.buildDirectory.dir(jPackageData.outputDir).get().asFile
        println(
            "INSTALLER created at ${outDirFile.asLink()} as " + outDirFile.resolve(
                "${jPackageData.installerNameOrDefault}-${jpackageData.appVersion ?: project.version}.${jpackageData.installerType}"
            ).asLink()
        )
    }
}