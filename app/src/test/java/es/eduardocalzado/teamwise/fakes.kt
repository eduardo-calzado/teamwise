package es.eduardocalzado.teamwise

import arrow.core.Either
import arrow.core.right
import es.eduardocalzado.teamwise.data.PermissionChecker
import es.eduardocalzado.teamwise.data.datasource.LocationDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

val defaultFakeTeams = sampleTeams

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

    override suspend fun deleteTeams() {
        inMemoryTeams.value = emptyList()
    }

    override fun searchTeams(search: String): Flow<List<Team>> {
       return flowOf(inMemoryTeams.value.filter { it.name.contains(search) })
    }
}

class FakeRemoteDataSource : TeamRemoteDataSource {
    var teams = defaultFakeTeams

    override suspend fun getTeamsByRegion(region: String) = teams.right()

    override suspend fun getTeamStats(
        league: Int,
        season: Int,
        team: Int
    ): Either<Error, Team.Stats> {
        TODO("Not yet implemented")
    }

    override suspend fun getTeams(
        country: String,
        league: Int,
        season: Int
    ): Either<Error, List<Team>> {
        return teams.right()
    }

}

class FakeLocationDataSource : LocationDataSource {
    var location = "England"
    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true
    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}

interface Prefs {
    fun getAccessToken(): String
}