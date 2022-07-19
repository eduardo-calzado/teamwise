package es.eduardocalzado.teamwise.ui.main.teams

import app.cash.turbine.test
import es.eduardocalzado.teamwise.testrules.CoroutinesTestRule
import es.eduardocalzado.teamwise.usecases.GetTeamsUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamsByRegionUseCase
import es.eduardocalzado.teamwise.sampleTeam
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get: Rule val coroutinesTestRule = CoroutinesTestRule()

    @Mock lateinit var getTeamsUseCase: GetTeamsUseCase
    @Mock lateinit var requestTeamsByRegionUseCase: RequestTeamsByRegionUseCase
    private lateinit var vm: MainViewModel
    private var teams = listOf(sampleTeam.copy(id = 1))

    @Before
    fun setUp() {
        whenever(getTeamsUseCase()).thenReturn(flowOf(teams))
        vm = MainViewModel(getTeamsUseCase, requestTeamsByRegionUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately` () = runTest {
        // 1s method. UiState will have multiple values during execution. In this method, we'll
        // be assure that the latest event is the one asserted.
        val results  = mutableListOf<MainViewModel.UiState>()
        val job = launch { vm.state.toList(results) }
        runCurrent()
        job.cancel() // if not, the state loop will never finishes

        assertEquals(MainViewModel.UiState(teams = teams), results[0])

    }

    @Test
    fun `Progress is shown when screen start and hidden when it finishes requesting teams`() = runTest {
        // 2nd method. UiState will have multiple values during execution. In this method, we'll
        // check all the possible values.
        vm.onUiReady()

        vm.state.test {
            // awaitItem(). if you don't want to test some intermediate states
            assertEquals(MainViewModel.UiState(), awaitItem())
            assertEquals(MainViewModel.UiState(teams = teams), awaitItem())
            assertEquals(MainViewModel.UiState(teams = teams, loading = true), awaitItem())
            assertEquals(MainViewModel.UiState(teams = teams, loading = false), awaitItem())
            cancel() // don't block the tests. recommended.
        }
    }
}