package com.padumi.core.data

import com.padumi.core.domain.GreetingRepository
import com.padumi.core.model.Greeting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultGreetingRepository
    @Inject
    constructor(
        private val localDataSource: GreetingLocalDataSource,
    ) : GreetingRepository {
        override fun observeGreeting(): Flow<Greeting> =
            localDataSource.observeGreeting().map { Greeting(it.orEmpty()) }

        override suspend fun refreshGreeting() {
            localDataSource.saveGreeting("Your Padumi data is stored locally.")
        }
    }
