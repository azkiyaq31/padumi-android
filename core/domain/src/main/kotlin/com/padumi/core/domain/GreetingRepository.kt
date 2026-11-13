package com.padumi.core.domain

import com.padumi.core.model.Greeting
import kotlinx.coroutines.flow.Flow

interface GreetingRepository {
    fun observeGreeting(): Flow<Greeting>

    suspend fun refreshGreeting()
}
