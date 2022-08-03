package es.eduardocalzado.teamwise.usecases

import arrow.core.Either
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import javax.inject.Inject

class RequestTeamStatsUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(league: Int = Constants.LEAGUE, season: Int = Constants.SEASON, team: Int) : Either<Error, Team.Stats> =
        teamRepository.requestTeamStats(league = league, season = season, team = team)
}