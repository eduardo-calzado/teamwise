package es.eduardocalzado.teamwise.ui.fakes

import es.eduardocalzado.teamwise.data.PermissionChecker
import es.eduardocalzado.teamwise.data.datasource.LocationDataSource

class FakeLocationDataSource : LocationDataSource {
    var location = "England"
    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true
    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}