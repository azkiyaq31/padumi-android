package com.padumi.core.data

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class PadumiDatabaseMigrationTest {
    private val databaseName = "padumi-migration-${System.nanoTime()}.db"

    @get:Rule
    val helper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            PadumiDatabase::class.java,
        )

    @Test
    fun `version 1 upgrades to version 2 keeping existing data and adding an empty wardrobe`() {
        helper.createDatabase(databaseName, 1).apply {
            execSQL("INSERT INTO greeting (id, message) VALUES (1, 'Saved before upgrade')")
            close()
        }

        helper.runMigrationsAndValidate(databaseName, 2, true).close()

        val database =
            Room
                .databaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    PadumiDatabase::class.java,
                    databaseName,
                ).allowMainThreadQueries()
                .build()
        try {
            runBlocking {
                assertThat(database.greetingDao().observeMessage().first()).isEqualTo("Saved before upgrade")
                assertThat(database.wardrobeItemDao().observeItems().first()).isEmpty()
            }
        } finally {
            database.close()
        }
    }
}
