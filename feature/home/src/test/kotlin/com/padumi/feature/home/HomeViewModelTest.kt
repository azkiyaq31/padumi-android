package com.padumi.feature.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.padumi.core.domain.ObserveGreetingUseCase
import com.padumi.core.domain.RefreshGreetingUseCase
import com.padumi.core.model.Greeting
import com.padumi.core.testing.FakeGreetingRepository
import com.padumi.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeGreetingRepository(Greeting("Foundation ready"))

    @Test
    fun `initial state observes greeting and refreshes repository`() =
        runTest {
            val viewModel = createViewModel()

            advanceUntilIdle()

            assertThat(viewModel.state.value.greeting).isEqualTo("Foundation ready")
            assertThat(viewModel.state.value.isLoading).isFalse()
            assertThat(repository.refreshCount).isEqualTo(1)
        }

    @Test
    fun `refresh failure updates state and emits one-shot message`() =
        runTest {
            repository.refreshFailure = IllegalStateException("Refresh unavailable")
            val viewModel = createViewModel()

            viewModel.effects.test {
                advanceUntilIdle()

                assertThat(awaitItem()).isEqualTo(HomeEffect.ShowMessage("Refresh unavailable"))
                assertThat(viewModel.state.value.errorMessage).isEqualTo("Refresh unavailable")
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun createViewModel() =
        HomeViewModel(
            observeGreeting = ObserveGreetingUseCase(repository),
            refreshGreeting = RefreshGreetingUseCase(repository),
        )
}
