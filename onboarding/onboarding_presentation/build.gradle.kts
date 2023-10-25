plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.example.onboarding_presentation"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreUI))
    implementation(project(Modules.onboardingDomain))

    // Accompanist Pager indicator - ONLY for Indicator -- DEPRECATED
    implementation("com.google.accompanist:accompanist-pager-indicators:0.21.2-beta")
}