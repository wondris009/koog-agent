plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

group = "cz.sg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.koog.agents)
    implementation(libs.koog.agents.additions)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

application {
    mainClass.set("cz.sg.MainKt")
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}
