plugins {
    id("starter.android.library")
    id("starter.android.hilt")
}

android {
    namespace = "com.padumi.core.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.robolectric)
}
