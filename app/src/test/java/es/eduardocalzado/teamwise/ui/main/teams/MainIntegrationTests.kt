package es.eduardocalzado.teamwise.ui.main.teams

import app.cash.turbine.test
import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.TeamLeague
import es.eduardocalzado.teamwise.testrules.CoroutinesTestRule
import es.eduardocalzado.teamwise.ui.fakes.*
import es.eduardocalzado.teamwise.ui.main.teams.MainViewModel.UiState
import es.eduardocalzado.teamwise.usecases.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty in first execution`() = runTest {
        val remoteData = defaultFakeTeams
        val vm = buildViewModelWith(remoteData = remoteData)

        vm.onSubmitClicked("England", TeamLeague.PREMIER_LEAGUE.id, 2021)
        runCurrent() // run all the pending functions needed

        vm.state.test {
            assertEquals(UiState(loading = false, teams = remoteData, error = null), awaitItem())
            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available` () = runTest {
        val remoteData = defaultFakeTeams
        val localData = defaultFakeTeams
        val vm = buildViewModelWith(localData = localData, remoteData = remoteData)

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(teams = localData), awaitItem())
            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<Team> = emptyList(),
        remoteData: List<Team> = emptyList()
    ) : MainViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)
        val localDataSource = FakeTeamLocalDataSource().apply { teams.value = localData }
        val remoteDataSource = FakeTeamRemoteDataSource().apply { teams = remoteData }
        val teamRepository = TeamRepository(regionRepository, localDataSource, remoteDataSource)
        val getTeamsUseCase = GetTeamsUseCase(teamRepository)
        val requestTeamsUseCase = RequestTeamsUseCase(teamRepository)
        val searchTeamsUseCase = SearchTeamsUseCase(teamRepository)
        val deleteTeamsUseCase = DeleteTeamsUseCase(teamRepository)
        val getRegionRepositoryUseCase = GetRegionRepositoryUseCase(teamRepository)
        return MainViewModel(getTeamsUseCase, requestTeamsUseCase, deleteTeamsUseCase, searchTeamsUseCase, getRegionRepositoryUseCase)
    }
}
