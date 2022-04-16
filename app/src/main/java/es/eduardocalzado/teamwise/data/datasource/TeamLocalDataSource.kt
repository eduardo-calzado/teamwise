package es.eduardocalzado.teamwise.data.datasource

import es.eduardocalzado.teamwise.data.database.TeamDao
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import es.eduardocalzado.teamwise.data.database.Team as DatabaseTeam

class TeamLocalDataSource(private val teamDao: TeamDao) {
    // -- get all the teams
    val teams: Flow<List<Team>> = teamDao.getAll().map { it.toDomainModel() }
    // -- if there are no teams
    suspend fun isEmpty() : Boolean = teamDao.teamCount() == 0
    // -- find the team by id
    fun findById(id: Int): Flow<Team> = teamDao.findById(id).map { it.toDomainModel() }
    // -- save the team
    suspend fun save (teams: List<Team>) {
        teamDao.insertTeam(teams.fromDomainModel())
    }
}

// #MARK: toDomainModel
private fun List<DatabaseTeam>.toDomainModel(): List<Team> = map { it.toDomainModel() }
private fun DatabaseTeam.toDomainModel(): Team = Team(
    id,
    name,
    code,
    country,
    founded,
    national,
    logo,
    address,
    city,
    capacity,
    surface,
    stadiumImage,
    favorite,
)

// #MARK: fromDomainModel
private fun List<Team>.fromDomainModel(): List<DatabaseTeam> = map { it.fromDomainModel() }
private fun Team.fromDomainModel(): DatabaseTeam = DatabaseTeam(
    id,
    name,
    code,
    country,
    founded,
    national,
    logo,
    address,
    city,
    capacity,
    surface,
    stadiumImage,
    favorite,
)