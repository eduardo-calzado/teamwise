package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow

interface TeamLocalDataSource {
    val teams: Flow<List<Team>>

    suspend fun isEmpty() : Boolean
    fun findById(id: Int): Flow<Team>
    suspend fun save (teams: List<Team>): Error?
    suspend fun deleteTeams()
    fun searchTeams(search: String): Flow<List<Team>>
}