/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

// jvm target
val JVM = 16 // 1.8 for 8, 11 for 11

// output jar name root
val OUTPUT_JAR_NAME = "block-edit-lib"

// target will be set to minecraft version by cli input parameter
var target = ""

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()

    // paper
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JVM))
    }
}

configurations {
    create("resolvableImplementation") {
        isCanBeResolved = true
        isCanBeConsumed = true
    }
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // paper and spigot api depending on parameter version
    if ( project.hasProperty("1.12") == true ) {
        implementation(files("./lib/spigot-1.12.2.jar"))
        implementation("com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
        target = "1.12"
    } else if ( project.hasProperty("1.16") == true ) {
        implementation(files("./lib/spigot-1.16.5.jar"))
        implementation("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
        target = "1.16"
    } else if ( project.hasProperty("1.18") == true ) {
        implementation(files("./lib/spigot-1.18.2.jar"))
        compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
        target = "1.18"
    }
}

application {
    // Define the main class for the application.
    mainClassName = "phonon.blockedit.FastBlockEditSessionKt"
}


tasks {
    named<ShadowJar>("shadowJar") {
        // verify valid target minecraft version
        doFirst {
            val supportedMinecraftVersions = setOf("1.12", "1.16", "1.18")
            if ( !supportedMinecraftVersions.contains(target) ) {
                throw Exception("Invalid Minecraft version! Supported versions are: 1.12, 1.16, 1.18")
            }
        }

        // hides text appended to output jar
        classifier = ""

        configurations = mutableListOf(project.configurations.named("resolvableImplementation").get())
        // minimize() // FOR PRODUCTION USE MINIMIZE?
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    
    test {
        testLogging.showStandardStreams = true
    }
}

gradle.taskGraph.whenReady {
    tasks {
        named<ShadowJar>("shadowJar") {
            baseName = "${OUTPUT_JAR_NAME}-${target}"
        }
    }
}