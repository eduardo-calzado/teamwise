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
    return when(leagueName) {
        PREMIER_LEAGUE.description -> PREMIER_LEAGUE.id
        EFL_CHAMPIONSHIP.description -> EFL_CHAMPIONSHIP.id
        EFL_ONE.description -> EFL_ONE.id
        LA_LIGA.description -> LA_LIGA.id
        SEGUNDA_DIVISION.description -> SEGUNDA_DIVISION.id
        PRIMERA_RFEF_G1.description -> PRIMERA_RFEF_G1.id
        PRIMERA_RFEF_G2.description -> PRIMERA_RFEF_G2.id
        SERIE_A.description -> SERIE_A.id
        SERIE_B.description -> SERIE_B.id
        SERIE_C.description -> SERIE_C.id
        else -> 0
    }
}

fun getTeamLeagueNameById(leagueId: Int) : String {
    return when(leagueId) {
        PREMIER_LEAGUE.id -> PREMIER_LEAGUE.description
        EFL_CHAMPIONSHIP.id -> EFL_CHAMPIONSHIP.description
        EFL_ONE.id -> EFL_ONE.description
        LA_LIGA.id -> LA_LIGA.description
        SEGUNDA_DIVISION.id -> SEGUNDA_DIVISION.description
        PRIMERA_RFEF_G1.id -> PRIMERA_RFEF_G1.description
        PRIMERA_RFEF_G2.id -> PRIMERA_RFEF_G2.description
        SERIE_A.id -> SERIE_A.description
        SERIE_B.id -> SERIE_B.description
        SERIE_C.id -> SERIE_C.description
        else -> ""
    }
}


