package es.eduardocalzado.teamwise.ui

import arrow.core.right
import es.eduardocalzado.teamwise.data.PermissionChecker
import es.eduardocalzado.teamwise.data.datasource.LocationDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.usecases.sampleTeam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

val defaultFakeTeams = listOf(
    sampleTeam.copy(1),
    sampleTeam.copy(2),
    sampleTeam.copy(3),
    sampleTeam.copy(4)
)

class FakeLocalDataSource : TeamLocalDataSource {
    val inMemoryTeams = MutableStateFlow<List<Team>>(emptyList())
    override val teams = inMemoryTeams
    private lateinit var findTeamFlow: MutableStateFlow<Team>
    override suspend fun isEmpty() = teams.value.isEmpty()
    override fun findById(id: Int): Flow<Team> {
        findTeamFlow = MutableStateFlow(inMemoryTeams.value.first { it.id == id})
        return findTeamFlow
    }
    override suspend fun save(teams: List<Team>): Error? {
        inMemoryTeams.value = teams
        if (::findTeamFlow.isInitialized) {
            teams.firstOrNull() { it.id == findTeamFlow.value.id }
                ?.let { findTeamFlow.value }
        }
        return null
    }
}

class FakeRemoteDataSource : TeamRemoteDataSource {
    var teams = defaultFakeTeams
    override suspend fun getTeamsByRegion(region: String) = teams.right()
}

class FakeLocationDataSource : LocationDataSource {
    var location = "England"
    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true
    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}