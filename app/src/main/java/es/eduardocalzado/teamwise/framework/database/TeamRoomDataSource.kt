package es.eduardocalzado.teamwise.framework.database

import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.domain.Team as Team
import es.eduardocalzado.teamwise.framework.database.Team as TeamDB
import es.eduardocalzado.teamwise.framework.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TeamRoomDataSource @Inject constructor(private val teamDao: TeamDao) : TeamLocalDataSource {
    // -- get all the teams
    override val teams: Flow<List<Team>> = teamDao.getAll().map { it.toDomainModel() }
    // -- if there are no teams
    override suspend fun isEmpty() : Boolean = teamDao.teamCount() == 0
    // -- find the team by id
    override fun findById(id: Int): Flow<Team> = teamDao.findById(id).map { it.toDomainModel() }
    // -- search the team
    override fun searchTeams(search: String): Flow<List<Team>> = teamDao.searchTeams(search).map { it.toDomainModel() }
    // -- save the team
    override suspend fun save (teams: List<Team>) = tryCall {
        teamDao.insertTeams(teams.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
    // -- delete teams
    override suspend fun deleteTeams() = teamDao.deleteTeams()
}

// #MARK: toDomainModel
private fun List<TeamDB>.toDomainModel(): List<Team> = map { it.toDomainModel() }
private fun TeamDB.toDomainModel(): Team =
    Team(
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
        stadiumName,
        favorite,
        emptyList(),
    )

// #MARK: fromDomainModel
private fun List<Team>.fromDomainModel(): List<TeamDB> = map { it.fromDomainModel() }
private fun Team.fromDomainModel(): TeamDB =
    TeamDB(
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
        stadiumName,
        favorite,
    )