import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.usuario"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.h2database:h2:2.1.214")
    implementation("org.slf4j:slf4j-nop:2.0.7")
    implementation ("com.zaxxer:HikariCP:5.0.0")
    implementation("io.kotest:kotest-assertions-core-jvm:5.6.1")
    testImplementation("io.mockk:mockk:1.13.5")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}