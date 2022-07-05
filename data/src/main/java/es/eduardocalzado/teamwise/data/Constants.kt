package es.eduardocalzado.teamwise.data

import es.eduardocalzado.teamwise.domain.TeamLeague

class Constants {
    companion object {
        const val LEAGUE = 2
        const val SEASON = 2021

        fun getTeamLeagueByCountry(country: String) : Int? {
            when(country) {
                "Spain" -> return TeamLeague.SPAIN.leagueId
                "England" -> return TeamLeague.ENGLAND.leagueId
            }
            return null
        }
    }
}