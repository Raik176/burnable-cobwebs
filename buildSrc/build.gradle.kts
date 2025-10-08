plugins {
    `kotlin-dsl`
    kotlin("jvm") version "2.0.20"
}

repositories {
    mavenCentral()
}

java {
    val target = JavaVersion.VERSION_1_8
    targetCompatibility = target
    sourceCompatibility = target
}