package es.eduardocalzado.teamwise.framework.database

import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.domain.Team
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
    // -- save the team
    override suspend fun save (teams: List<Team>) = tryCall {
        teamDao.insertTeam(teams.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

// #MARK: toDomainModel
private fun List<es.eduardocalzado.teamwise.framework.database.Team>.toDomainModel(): List<Team> = map { it.toDomainModel() }
private fun es.eduardocalzado.teamwise.framework.database.Team.toDomainModel(): Team =
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
        favorite,
    )

// #MARK: fromDomainModel
private fun List<Team>.fromDomainModel(): List<es.eduardocalzado.teamwise.framework.database.Team> = map { it.fromDomainModel() }
private fun Team.fromDomainModel(): es.eduardocalzado.teamwise.framework.database.Team =
    es.eduardocalzado.teamwise.framework.database.Team(
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