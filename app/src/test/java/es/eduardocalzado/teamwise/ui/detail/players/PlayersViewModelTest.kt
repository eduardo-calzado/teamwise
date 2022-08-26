package es.eduardocalzado.teamwise.ui.detail.players

import es.eduardocalzado.teamwise.samplePlayer
import es.eduardocalzado.teamwise.testrules.CoroutinesTestRule
import es.eduardocalzado.teamwise.usecases.FindPlayersByTeamUseCase
import es.eduardocalzado.teamwise.usecases.RequestPlayersByTeamUseCase
import es.eduardocalzado.teamwise.usecases.SearchPlayersUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PlayersViewModelTest {

    @get: Rule val coroutinesTestRule = CoroutinesTestRule()

    @Mock lateinit var requestPlayersByTeamUseCase: RequestPlayersByTeamUseCase
    @Mock lateinit var findPlayersByTeamUseCase: FindPlayersByTeamUseCase
    @Mock lateinit var searchPlayersUseCase: SearchPlayersUseCase
    private lateinit var vm: PlayersViewModel
    private var players = listOf(samplePlayer.copy(id = 1))

    @Before
    fun setUp() {
        whenever(findPlayersByTeamUseCase(team = 1)).thenReturn(flowOf(players))
        vm = PlayersViewModel(1, -1, -1, requestPlayersByTeamUseCase, findPlayersByTeamUseCase, searchPlayersUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately` () = runTest {
        // 1s method. UiState will have multiple values during execution. In this method, we'll
        // be assure that the latest event is the one asserted.
        val results  = mutableListOf<PlayersViewModel.UiState>()
        val job = launch { vm.state.toList(results) }
        runCurrent()
        job.cancel() // if not, the state loop will never finishes

        assertEquals(PlayersViewModel.UiState(players = players), results[0])
    }
}