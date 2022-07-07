package es.eduardocalzado.teamwise.ui.main.teams

import app.cash.turbine.test
import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.testrules.CoroutinesTestRule
import es.eduardocalzado.teamwise.ui.*
import es.eduardocalzado.teamwise.ui.main.teams.MainViewModel
import es.eduardocalzado.teamwise.ui.main.teams.MainViewModel.*
import es.eduardocalzado.teamwise.usecases.GetTeamsUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamsByRegionUseCase
import es.eduardocalzado.teamwise.usecases.sampleTeam
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = defaultFakeTeams // or any values like listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        val vm = buildViewModelWith(remoteData = remoteData)
        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(teams = emptyList()), awaitItem())
            assertEquals(UiState(teams = emptyList(), loading = true), awaitItem())
            assertEquals(UiState(teams = emptyList(), loading = false), awaitItem())
            assertEquals(UiState(teams = remoteData, loading = false), awaitItem())
        }
    }

    @Test
    fun `data is loaded from local source when available` () = runTest {
        val remoteData = listOf(sampleTeam.copy(1), sampleTeam.copy(1))
        val localData = listOf(sampleTeam.copy(10), sampleTeam.copy(11))
        val vm = buildViewModelWith(localData = localData, remoteData = remoteData)
        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(teams = localData), awaitItem())
        }
    }

    private fun buildViewModelWith(
        localData: List<Team> = emptyList(),
        remoteData: List<Team> = emptyList()
    ) : MainViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)
        val localDataSource = FakeLocalDataSource().apply { teams.value = localData }
        val remoteDataSource = FakeRemoteDataSource().apply { teams = remoteData }
        val teamRepository = TeamRepository(regionRepository, localDataSource, remoteDataSource)
        val getTeamsUseCase = GetTeamsUseCase(teamRepository)
        val requestTeamsUseCase = RequestTeamsByRegionUseCase(teamRepository)
        return MainViewModel(getTeamsUseCase, requestTeamsUseCase)
    }
}
