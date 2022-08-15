import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.sampleTeams
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class TeamRepositoryTest {

    // Use Mockito (@Mock) to generate the mocks automatically
    @Mock lateinit var localDataSource: TeamLocalDataSource
    @Mock lateinit var remoteDataSource: TeamRemoteDataSource
    @Mock lateinit var regionRepository: RegionRepository
    private lateinit var teamRepository: TeamRepository
    private var localTeams = flowOf(sampleTeams)

    @Before
    fun setUp() {

        whenever(localDataSource.teams).thenReturn(localTeams)
        teamRepository = TeamRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Teams are taken from local data source if available` (): Unit = runBlocking {
        // GIVEN
        // ...
        // WHEN
        val result = teamRepository.teams
        // THEN
        assertEquals(localTeams, result)
    }

    @Test
    fun `Switching favorite marks as favourite an unfavourite team` (): Unit = runBlocking {
        // GIVEN
        val team = sampleTeam.copy(favorite = false)
        // WHEN
        teamRepository.switchFavorite(team)
        // THEN
        verify(localDataSource).save(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavourite an favourite team` (): Unit = runBlocking {
        // GIVEN
        val team = sampleTeam.copy(favorite = true)
        // WHEN
        teamRepository.switchFavorite(team)
        // THEN
        verify(localDataSource).save(argThat { !get(0).favorite })
    }
}