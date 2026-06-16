plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.9.4")
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val commonMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.koin)
            implementation(libs.datetime)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(project(":shared"))
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.9.3")
                implementation(libs.firebase.bom)
                implementation(libs.firebase.auth.android)
                implementation(libs.firebase.firestore.android)
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "com.notepad.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.notepad.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

compose.desktop {
    application {
        mainClass = "com.notepad.MainKt"
        jvmArgs += listOf(
            "-Dskiko.renderApi=SOFTWARE",
            "-Dskiko.windowManager.type=AWT"
        )
    }
}
