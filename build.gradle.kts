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
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.0")
    testImplementation(kotlin("test"))
    implementation("com.h2database:h2:2.1.214")
    implementation("org.slf4j:slf4j-nop:2.0.7")
    implementation ("com.zaxxer:HikariCP:5.0.0")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.6.21")
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