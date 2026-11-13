package com.padumi.core.data

import com.google.common.truth.Truth.assertThat
import com.padumi.core.model.Greeting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class DefaultGreetingRepositoryTest {
    private val dataSource = FakeGreetingLocalDataSource()
    private val repository = DefaultGreetingRepository(dataSource)

    @Test
    fun `observe greeting delegates to local data source`() =
        runTest {
            assertThat(repository.observeGreeting().first().message).isEqualTo("Initial")
        }

    @Test
    fun `refresh greeting persists value to local data source`() =
        runTest {
            repository.refreshGreeting()

            assertThat(repository.observeGreeting().first().message).isEqualTo("Your Padumi data is stored locally.")
            assertThat(dataSource.savedMessages).containsExactly("Your Padumi data is stored locally.")
        }

    @Test
    fun `write failure propagates to caller`() =
        runTest {
            val failure = IOException("Local write failed")
            dataSource.writeFailure = failure

            val thrown = runCatching { repository.refreshGreeting() }.exceptionOrNull()

            assertThat(thrown).isSameInstanceAs(failure)
        }

    @Test
    fun `empty stored value is observable`() =
        runTest {
            dataSource.setMessage("")

            assertThat(repository.observeGreeting().first().message).isEmpty()
        }
}

private class FakeGreetingLocalDataSource : GreetingLocalDataSource {
    private val message = MutableStateFlow("Initial")
    val savedMessages = mutableListOf<String>()
    var writeFailure: Throwable? = null

    override fun observeGreeting(): Flow<String> = message

    override suspend fun saveGreeting(message: String) {
        writeFailure?.let { throw it }
        savedMessages += message
        this.message.value = message
    }

    fun setMessage(value: String) {
        message.value = value
    }
}
