package com.padumi.core.data

import com.padumi.core.domain.GreetingRepository
import com.padumi.core.model.Greeting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultGreetingRepository
    @Inject
    constructor(
        private val localDataSource: GreetingLocalDataSource,
    ) : GreetingRepository {
        override fun observeGreeting(): Flow<Greeting> = localDataSource.observeGreeting()

        override suspend fun refreshGreeting() = localDataSource.refreshGreeting()
    }
