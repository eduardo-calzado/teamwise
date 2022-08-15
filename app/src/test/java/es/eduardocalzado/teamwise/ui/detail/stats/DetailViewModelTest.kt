package es.eduardocalzado.teamwise.ui.detail.stats

import app.cash.turbine.test
import es.eduardocalzado.teamwise.testrules.CoroutinesTestRule
import es.eduardocalzado.teamwise.usecases.FindTeamUseCase
import es.eduardocalzado.teamwise.usecases.SwitchTeamFavoriteUseCase
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.usecases.RequestTeamStatsUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get: Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock lateinit var findTeamUseCase: FindTeamUseCase
    @Mock lateinit var requestTeamStatsUseCase: RequestTeamStatsUseCase
    @Mock lateinit var switchTeamFavoriteUseCase: SwitchTeamFavoriteUseCase
    private lateinit var vm: DetailViewModel
    private val team = sampleTeam.copy(id = 2)

    @Before
    fun setUp() {
        whenever(findTeamUseCase(id = 2)).thenReturn(flowOf(team))
        vm = DetailViewModel(2, -1, -1, findTeamUseCase, requestTeamStatsUseCase, switchTeamFavoriteUseCase)
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.state.test {
            assertEquals(DetailViewModel.UiState(), awaitItem())
            assertEquals(DetailViewModel.UiState(teamData = team), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        vm.onFavoriteClicked()
        runCurrent() // run all the pending functions needed

        verify(switchTeamFavoriteUseCase).invoke(team)
    }

}