plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.core"
}

dependencies {
    "kapt"(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
}