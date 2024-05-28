import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.compose.desktop.application.dsl.TargetFormat



plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("org.jetbrains.compose") version "1.6.20-dev1646"

}

group = "dev.bluemethyst.strlnkipscn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.6")

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
}

compose.desktop {
    application {
        mainClass = "dev.bluemethyst.strlnkipscn.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "Koquestor"
            packageVersion = "1.0.0"
            /*macOS {
                iconFile.set(project.file("koquestor.icns"))
            }
            windows {
                iconFile.set(project.file("koquestor.ico"))
            }
            linux {
                iconFile.set(project.file("koquestor.png"))
            }*/
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("${project.name}-fat")
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "dev.bluemethyst.strlnkipscn.MainKt"
    }
}

tasks.build {
    dependsOn(tasks.withType<ShadowJar>())
}