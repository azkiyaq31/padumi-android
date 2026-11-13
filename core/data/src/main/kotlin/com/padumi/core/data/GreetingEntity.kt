package com.padumi.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "greeting")
internal data class GreetingEntity(
    @PrimaryKey val id: Int = 1,
    val message: String,
)
