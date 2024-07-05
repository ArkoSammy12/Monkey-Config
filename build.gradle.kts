plugins {
    kotlin("jvm") version "2.0.0"
}

group = property("maven_group")!!
version = property("version")!!
val archiveBaseNames = property("archive_base_name")!!

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.apache.logging.log4j:log4j-api:${property("slf4j_version")}")!!
    implementation("org.apache.logging.log4j:log4j-core:${property("slf4j_version")}")!!
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${property("slf4j_version")}")!!
    implementation("com.electronwill.night-config:core:${property("night_config_version")}")!!
    implementation("com.electronwill.night-config:toml:${property("night_config_version")}")!!
    implementation("com.electronwill.night-config:json:${property("night_config_version")}")!!
    implementation("com.electronwill.night-config:yaml:${property("night_config_version")}")!!
    implementation("com.electronwill.night-config:hocon:${property("night_config_version")}")!!

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}