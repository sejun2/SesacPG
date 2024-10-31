plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.code.gson:gson:2.11.0")
    testImplementation("io.mockk:mockk:1.13.13")
<<<<<<< Updated upstream
=======
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
>>>>>>> Stashed changes
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}