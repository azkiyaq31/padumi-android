package com.padumi.core.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

internal interface GreetingLocalDataSource {
    fun observeGreeting(): Flow<String?>

    suspend fun saveGreeting(message: String)
}

@Singleton
internal class RoomGreetingLocalDataSource
    @Inject
    constructor(
        private val database: PadumiDatabase,
    ) : GreetingLocalDataSource {
        private val dao get() = database.greetingDao()

        override fun observeGreeting(): Flow<String?> = dao.observeMessage()

        override suspend fun saveGreeting(message: String) {
            dao.upsert(GreetingEntity(message = message))
        }
    }
