plugins {
    id("starter.android.library")
    id("starter.android.compose")
}

android {
    namespace = "com.padumi.core.designsystem"
}

dependencies {
    testImplementation(libs.truth)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
