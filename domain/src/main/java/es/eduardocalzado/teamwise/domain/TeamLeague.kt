package es.eduardocalzado.teamwise.domain

import es.eduardocalzado.teamwise.domain.TeamLeague.*

enum class TeamLeague (val id: Int, val description: String) {
    PREMIER_LEAGUE(39, "Premier League"),
    EFL_CHAMPIONSHIP(40, "EFL Championship"),
    EFL_ONE(41, "EFL One"),
    LA_LIGA(140, "La Liga"),
    SEGUNDA_DIVISION(141, "Segunda Division"),
    PRIMERA_RFEF_G1(435,"Primera Division RFEF - Group 1"),
    PRIMERA_RFEF_G2(436, "Primera Division RFEF - Group 2"),
    SERIE_A(135, "Serie A"),
    SERIE_B(136, "Serie B"),
    SERIE_C(138, "Serie C"),
}


fun getTeamLeagueIdByName(leagueName: String) : Int {
    when(leagueName) {
        PREMIER_LEAGUE.description -> return PREMIER_LEAGUE.id
        EFL_CHAMPIONSHIP.description -> return EFL_CHAMPIONSHIP.id
        EFL_ONE.description -> return EFL_ONE.id
        LA_LIGA.description -> return LA_LIGA.id
        SEGUNDA_DIVISION.description -> return SEGUNDA_DIVISION.id
        PRIMERA_RFEF_G1.description -> return PRIMERA_RFEF_G1.id
        PRIMERA_RFEF_G2.description -> return PRIMERA_RFEF_G2.id
        SERIE_A.description -> return SERIE_A.id
        SERIE_B.description -> return SERIE_B.id
        SERIE_C.description -> return SERIE_C.id
        else -> return 0
    }
}


