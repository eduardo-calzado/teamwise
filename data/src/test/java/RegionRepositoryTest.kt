import es.eduardocalzado.teamwise.data.PermissionChecker
import es.eduardocalzado.teamwise.data.PermissionChecker.Permission.COARSE_LOCATION
import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.datasource.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RegionRepositoryTest {

    @Test
    fun `Returns default region when coarse permission not granted` (): Unit = runBlocking {
        // GIVEN (sut: System Under Test -> Could it be used generally)
        val sut = buildRegionRepository(
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn false }
        )
        // WHEN
        val region = sut.findLastRegion()
        // THEN
        Assert.assertEquals(RegionRepository.DEFAULT_REGION, region)
    }

    @Test
    fun `Returns region from location data source when permission granted` () : Unit = runBlocking {
        // GIVEN
        val regionRepository = buildRegionRepository(
            locationDataSource = mock { onBlocking { findLastRegion() } doReturn "Spain" },
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn true }
        )
        // WHEN
        val region = regionRepository.findLastRegion()
        // THEN
        Assert.assertEquals("Spain", region)
    }
}

private fun buildRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
) = RegionRepository(locationDataSource, permissionChecker)