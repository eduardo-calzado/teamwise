package es.eduardocalzado.teamwise.ui.detail.players

import app.cash.turbine.test
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.testrules.CoroutinesTestRule
import es.eduardocalzado.teamwise.ui.buildPlayerRepositoryWith
import es.eduardocalzado.teamwise.ui.detail.players.PlayersViewModel.UiState
import es.eduardocalzado.teamwise.ui.fakes.defaultFakePlayers
import es.eduardocalzado.teamwise.usecases.FindPlayersByTeamUseCase
import es.eduardocalzado.teamwise.usecases.RequestPlayersByTeamUseCase
import es.eduardocalzado.teamwise.usecases.SearchPlayersUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayersIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `players are loaded initially from local source`() = runTest {
        val localData = defaultFakePlayers
        val expectedState = defaultFakePlayers.filter { it.team == 1 }
        val vm = buildViewModelWith(teamId = 1, localData = localData)

        runCurrent()
        vm.state.test {
            assertEquals(UiState(loading = false, players = expectedState, error = null), awaitItem())
            cancel()
        }
    }

    @Test
    fun `search players from local source`() = runTest {
        val localData = defaultFakePlayers
        val expectedState = defaultFakePlayers.filter { it.team == 1 && it.name.contains("m") }
        val vm = buildViewModelWith(teamId = 1, localData = localData)

        vm.searchPlayers("Ney")
        runCurrent()
        vm.state.test {
            assertEquals(UiState(players = expectedState), awaitItem())
            cancel()
        }
    }

    @Test
    fun `players are loaded from remote when local source don't have players for that team`() = runTest {
        val localData = emptyList<Player>()
        val remoteData = defaultFakePlayers
        val expectedState = defaultFakePlayers.filter { it.team == 1 }
        val vm = buildViewModelWith(teamId = 1, seasonId = 1, localData = localData, remoteData = remoteData)

        vm.onUiReady()
        runCurrent()
        vm.state.test {
            assertEquals(UiState(loading = false, players = expectedState, error = null), awaitItem())
            cancel()
        }
    }

    private fun buildViewModelWith(
        teamId: Int,
        leagueId: Int = -1,
        seasonId: Int = -1,
        localData: List<Player> = emptyList(),
        remoteData: List<Player> = emptyList()
    ): PlayersViewModel {
        val playerRepository = buildPlayerRepositoryWith(localData, remoteData)
        val requestPlayersByTeamUseCase = RequestPlayersByTeamUseCase(playerRepository)
        val findPlayersByTeamUseCase = FindPlayersByTeamUseCase(playerRepository)
        val searchPlayersUseCase = SearchPlayersUseCase(playerRepository)
        return PlayersViewModel(teamId, leagueId, seasonId, requestPlayersByTeamUseCase, findPlayersByTeamUseCase, searchPlayersUseCase)
    }
}
