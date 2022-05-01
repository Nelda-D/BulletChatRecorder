import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    kotlin("jvm") version "1.5.10"
    kotlin("jvm") version "1.6.0"
    application
    kotlin("plugin.serialization") version "1.6.0"
}

group = "me.administrator"
version = "1.0-SNAPSHOT"
val brotliVersion = "0.2.0"

repositories {
//    maven { url 'https://maven.aliyun.com/nexus/content/groups/public/'}
    google()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
//    compile group: 'net.dongliu', name: 'requests', version: '4.14.1'
//    compile group: 'org.jsoup', name: 'jsoup', version: '1.11.2'
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    api("com.nixxcode.jvmbrotli:jvmbrotli:$brotliVersion")
    api("com.nixxcode.jvmbrotli:jvmbrotli-win32-x86:$brotliVersion")
    api("com.nixxcode.jvmbrotli:jvmbrotli-win32-x86-amd64:$brotliVersion")
//    api("com.nixxcode.jvmbrotli:jvmbrotli-darwin-x86-amd64:$brotliVersion")
//    api("com.nixxcode.jvmbrotli:jvmbrotli-linux-x86-amd64:$brotliVersion")
    api("org.java-websocket:Java-WebSocket:1.3.7")
    implementation ("com.squareup.retrofit2:retrofit:2.6.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.1")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    api("com.squareup.okhttp3:okhttp:4.9.3")
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}