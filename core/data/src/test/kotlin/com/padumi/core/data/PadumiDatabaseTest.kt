package com.padumi.core.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class PadumiDatabaseTest {
    private val databaseName = "padumi-test-${System.nanoTime()}.db"
    private lateinit var context: android.content.Context
    private lateinit var database: PadumiDatabase

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = openDatabase()
    }

    @After
    fun tearDown() {
        database.close()
        context.getDatabasePath(databaseName).delete()
    }

    @Test
    fun `greeting starts empty`() =
        runBlocking {
            assertThat(database.greetingDao().observeMessage().first()).isNull()
        }

    @Test
    fun `saved greeting can be read from local database`() =
        runBlocking {
            database.greetingDao().upsert(GreetingEntity(message = "Padumi is offline-first"))

            assertThat(database.greetingDao().observeMessage().first()).isEqualTo("Padumi is offline-first")
        }

    @Test
    fun `greeting survives database recreation`() =
        runBlocking {
            database.greetingDao().upsert(GreetingEntity(message = "Saved locally"))
            database.close()

            database = openDatabase()

            assertThat(database.greetingDao().observeMessage().first()).isEqualTo("Saved locally")
        }

    private fun openDatabase(): PadumiDatabase =
        Room
            .databaseBuilder(context, PadumiDatabase::class.java, databaseName)
            .allowMainThreadQueries()
            .build()
}
