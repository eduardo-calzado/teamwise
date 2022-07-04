package es.eduardocalzado.teamwise.ui

import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Team

fun buildRepositoryWith(
    localData: List<Team>,
    remoteData: List<Team>
): TeamRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = FakeLocalDataSource().apply { inMemoryTeams.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { teams = remoteData }
    return TeamRepository(regionRepository, localDataSource, remoteDataSource)
}