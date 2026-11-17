plugins {
    id("starter.android.library")
    id("starter.android.hilt")
    alias(libs.plugins.room)
}

android {
    namespace = "com.padumi.core.data"

    // Exported Room schemas are needed by migration tests running on the JVM.
    sourceSets.getByName("test").assets.directories.add("$projectDir/schemas")
}

room {
    schemaDirectory("$projectDir/schemas")
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
    testImplementation(libs.room.testing)
}
