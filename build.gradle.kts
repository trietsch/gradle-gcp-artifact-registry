
val location = "europe-west4"
val googleCloudProject = "fill-out"

plugins {
    kotlin("jvm") version "1.4.21"
    id("com.google.cloud.artifactregistry.gradle-plugin") version "2.1.1"
}

buildscript {
    repositories {
        mavenCentral()
        maven("artifactregistry://$location-maven.pkg.dev/$googleCloudProject/snapshot")
        maven("artifactregistry://$location-maven.pkg.dev/$googleCloudProject/release")
    }

    dependencies {
        classpath("gradle.plugin.com.google.cloud.artifactregistry", "artifactregistry-gradle-plugin", "2.1.1")
        classpath("dev.trietsch", "some-codegen-tool-from-artifact-registry", "0.1.0")
    }
}

group = "dev.trietsch.gradle-artifact-registry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("artifactregistry://$location-maven.pkg.dev/$googleCloudProject/snapshot")
    maven("artifactregistry://$location-maven.pkg.dev/$googleCloudProject/release")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("dev.trietsch:dep-from-artifact-registry:0.1.0")
}

val jar: Jar by tasks

jar.apply {
    doFirst {
        manifest {
            attributes["Main-Class"] = "HelloWorld"
        }

        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }
}
