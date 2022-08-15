import es.eduardocalzado.teamwise.data.PermissionChecker
import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.sampleTeam
import es.eduardocalzado.teamwise.sampleTeam2
import es.eduardocalzado.teamwise.usecases.GetRegionRepositoryUseCase
import es.eduardocalzado.teamwise.usecases.SwitchTeamFavoriteUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetRegionRepositoryUseCaseTest {
    @Test
    fun `Invoke call teams repository`() : Unit = runBlocking {
        // GIVEN
        val regionRepository = RegionRepository (
            locationDataSource = mock { onBlocking { findLastRegion() } doReturn("England") },
            permissionChecker = mock { on { check(PermissionChecker.Permission.COARSE_LOCATION) } doReturn true }
        )
        val teamRepository = TeamRepository(regionRepository, mock(), mock())
        val getRegionRepositoryCase = GetRegionRepositoryUseCase(teamRepository)
        // THEN
        val result = getRegionRepositoryCase()
        // WHEN
        Assert.assertEquals("England", result)
    }
}