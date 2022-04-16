package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow

interface TeamLocalDataSource {
    // -- get all the teams
    val teams: Flow<List<Team>>

    // -- if there are no teams
    suspend fun isEmpty() : Boolean

    // -- find the team by id
    fun findById(id: Int): Flow<Team>

    // -- save the team
    suspend fun save (teams: List<Team>)
}