import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.bluemethyst.strlnkipscn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.6")
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