package es.eduardocalzado.teamwise.ui

import es.eduardocalzado.teamwise.data.PlayerRepository
import es.eduardocalzado.teamwise.data.RegionRepository
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.ui.fakes.*

fun buildTeamRepositoryWith(
    localData: List<Team>,
    remoteData: List<Team>
): TeamRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = FakeTeamLocalDataSource().apply { inMemoryTeams.value = localData }
    val remoteDataSource = FakeTeamRemoteDataSource().apply { teams = remoteData }
    return TeamRepository(regionRepository, localDataSource, remoteDataSource)
}

fun buildPlayerRepositoryWith(
    localData: List<Player>,
    remoteData: List<Player>
): PlayerRepository {
    val localDataSource = FakePlayerLocalDataSource().apply { inMemoryPlayers.value = localData }
    val remoteDataSource = FakePlayerRemoteDataSource().apply { players = remoteData }
    return PlayerRepository(localDataSource, remoteDataSource)
}