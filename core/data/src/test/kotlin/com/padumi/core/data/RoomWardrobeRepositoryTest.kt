package com.padumi.core.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.padumi.core.model.Availability
import com.padumi.core.model.ClothingCategory
import com.padumi.core.model.NewWardrobeItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class RoomWardrobeRepositoryTest {
    private val databaseName = "padumi-wardrobe-${System.nanoTime()}.db"
    private val now = Instant.parse("2026-11-17T08:00:00Z")
    private val clock = Clock.fixed(now, ZoneOffset.UTC)
    private lateinit var context: android.content.Context
    private lateinit var database: PadumiDatabase
    private lateinit var repository: RoomWardrobeRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        openDatabase()
    }

    @After
    fun tearDown() {
        database.close()
        context.getDatabasePath(databaseName).delete()
    }

    @Test
    fun `wardrobe starts empty`() =
        runBlocking<Unit> {
            assertThat(repository.observeItems().first()).isEmpty()
        }

    @Test
    fun `added item is stored as available with its details and timestamps`() =
        runBlocking<Unit> {
            val id = repository.addItem(NewWardrobeItem("Blue shirt", ClothingCategory.TOP, "photos/a.jpg"))

            val item = repository.observeItems().first().single()
            assertThat(item.id).isEqualTo(id)
            assertThat(item.name).isEqualTo("Blue shirt")
            assertThat(item.category).isEqualTo(ClothingCategory.TOP)
            assertThat(item.availability).isEqualTo(Availability.AVAILABLE)
            assertThat(item.photoPath).isEqualTo("photos/a.jpg")
            assertThat(item.createdAt).isEqualTo(now)
            assertThat(item.updatedAt).isEqualTo(now)
        }

    @Test
    fun `every category round-trips through the database`() =
        runBlocking<Unit> {
            ClothingCategory.entries.forEach { category ->
                repository.addItem(NewWardrobeItem(category.name, category, "photos/${category.name}.jpg"))
            }

            val stored = repository.observeItems().first().map { it.category }
            assertThat(stored).containsExactlyElementsIn(ClothingCategory.entries)
        }

    @Test
    fun `items are listed newest first`() =
        runBlocking<Unit> {
            repository.addItem(NewWardrobeItem("First", ClothingCategory.TOP, "photos/1.jpg"))
            repository.addItem(NewWardrobeItem("Second", ClothingCategory.BOTTOM, "photos/2.jpg"))

            assertThat(repository.observeItems().first().map { it.name })
                .containsExactly("Second", "First")
                .inOrder()
        }

    @Test
    fun `items survive closing and reopening the database`() =
        runBlocking<Unit> {
            repository.addItem(NewWardrobeItem("Coat", ClothingCategory.OUTERWEAR, "photos/coat.jpg"))
            database.close()

            openDatabase()

            assertThat(repository.observeItems().first().map { it.name }).containsExactly("Coat")
        }

    @Test
    fun `categories and statuses are stored as stable text codes`() =
        runBlocking<Unit> {
            repository.addItem(NewWardrobeItem("Dress", ClothingCategory.ONE_PIECE, "photos/dress.jpg"))

            database.query("SELECT category, availability FROM wardrobe_item", null).use { cursor ->
                cursor.moveToFirst()
                assertThat(cursor.getString(0)).isEqualTo("one_piece")
                assertThat(cursor.getString(1)).isEqualTo("available")
            }
        }

    private fun openDatabase() {
        database =
            Room
                .databaseBuilder(context, PadumiDatabase::class.java, databaseName)
                .allowMainThreadQueries()
                .build()
        repository = RoomWardrobeRepository(database.wardrobeItemDao(), clock)
    }
}
