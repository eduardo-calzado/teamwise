package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.data.database.Team
import es.eduardocalzado.teamwise.data.database.TeamDao
import kotlinx.coroutines.flow.Flow

class   TeamLocalDataSource(private val teamDao: TeamDao) {

    val teams: Flow<List<Team>> = teamDao.getAll()

    suspend fun isEmpty() : Boolean = teamDao.teamCount() == 0

    fun findById(id: Int): Flow<Team> = teamDao.findById(id)

    suspend fun save (teams: List<Team>) {
        teamDao.insertTeam(teams)
    }
}
