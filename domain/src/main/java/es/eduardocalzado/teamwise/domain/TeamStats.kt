package es.eduardocalzado.teamwise.domain

data class TeamStats (
    val id: Int,
    val league_name: String,
    val league_country: String,
    val fixture_draws_total: Int,
    val fixture_loses_total: Int,
    val fixture_wins_total: Int,
)