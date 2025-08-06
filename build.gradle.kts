import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.5.0"
    id("jacoco")
}

group = property("projectGroup").toString()
version = property("projectVersion").toString()

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    implementation("io.github.haradakunihiko:php-json-deserializer-kt:${property("phpJsonDeserializerVersion")}")
    
    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    
    intellijPlatform {
        create("IC", property("intellijVersion").toString())
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add necessary plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = property("sinceBuild").toString()
            untilBuild = provider { null }
        }

        changeNotes = """
      Initial version
    """.trimIndent()
    }
    
    publishing {
        token = providers.gradleProperty("intellijPlatformPublishingToken")
        // Optional: specify release channels
        // channels = listOf("default") // or "beta", "alpha", etc.
    }
    
    pluginVerification {
        ides {
            recommended()
        }
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
    }

    test {
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)
        reports {
            xml.required = true
            csv.required = false
            html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
        }
    }
}
