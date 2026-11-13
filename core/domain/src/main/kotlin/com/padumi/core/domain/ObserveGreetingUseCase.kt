package com.padumi.core.domain

import com.padumi.core.model.Greeting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveGreetingUseCase
    @Inject
    constructor(
        private val repository: GreetingRepository,
    ) {
        operator fun invoke(): Flow<Greeting> = repository.observeGreeting()
    }
