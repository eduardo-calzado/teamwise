import es.eduardocalzado.teamwise.data.PlayerRepository
import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.data.datasource.PlayerLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.PlayerRemoteDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.samplePlayer
import es.eduardocalzado.teamwise.samplePlayers
import es.eduardocalzado.teamwise.sampleTeam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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
class PlayerRepositoryTest {

    // Use Mockito (@Mock) to generate the mocks automatically
    @Mock lateinit var localDataSource: PlayerLocalDataSource
    @Mock lateinit var remoteDataSource: PlayerRemoteDataSource
    private lateinit var playerRepository: PlayerRepository
    private var localPlayers = flowOf(samplePlayers)

    @Before
    fun setUp() {
        whenever(localDataSource.players).thenReturn(localPlayers)
        playerRepository = PlayerRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `Players are taken from local data source if available` (): Unit = runBlocking {
        // GIVEN
        // ...
        // WHEN
        val result = playerRepository.players
        // THEN
        assertEquals(localPlayers, result)
    }
}