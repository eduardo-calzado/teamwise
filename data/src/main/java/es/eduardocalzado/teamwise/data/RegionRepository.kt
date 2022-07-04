package es.eduardocalzado.teamwise.data

import es.eduardocalzado.teamwise.data.datasource.LocationDataSource
import javax.inject.Inject

class RegionRepository @Inject constructor (
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        private const val DEFAULT_REGION = "England"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}