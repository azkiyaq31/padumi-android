package com.padumi.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Category and availability are stored as stable lowercase text codes (see [WardrobeCodes]),
 * so renaming a Kotlin enum constant can never silently corrupt saved data.
 */
@Entity(tableName = "wardrobe_item")
internal data class WardrobeItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,
    val availability: String,
    @ColumnInfo(name = "photo_path") val photoPath: String,
    @ColumnInfo(name = "created_at") val createdAtEpochMillis: Long,
    @ColumnInfo(name = "updated_at") val updatedAtEpochMillis: Long,
)
