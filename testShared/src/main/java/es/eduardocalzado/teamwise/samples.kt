package es.eduardocalzado.teamwise

import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.domain.Team

val sampleTeams by lazy { listOf(sampleTeam, sampleTeam2, sampleTeam3) }

val sampleTeam = Team (
    1,
    "Racing Santander",
    "SAN",
    "Spain",
    1913,
    false,
    "https://media.api-sports.io/football/teams/4665.png",
    "Calle del Real Racing Club",
    "Santander",
    22271,
    "grass",
    "https://media.api-sports.io/football/venues/3516.png",
    "Campos de Sport de El Sardinero",
    true,
    emptyList()
)
val sampleTeam2 = Team (
    2,
    "Manchester United",
    "MUN",
    "England",
    1878,
    false,
    "https://media.api-sports.io/football/teams/33.png",
    "Sir Matt Busby Way",
    "Manchester",
    76212,
    "grass",
    "https://media.api-sports.io/football/venues/556.png",
    "Old Trafford",
    false,
    emptyList()
)
val sampleTeam3 = Team (
    3,
    "Lazio",
    "Laz",
    "Italy",
    1900,
    false,
    "https://media.api-sports.io/football/teams/487.png",
    "Viale dei Gladiatori, 2 / Via del Foro Italico",
    "Roma",
    68530,
    "grass",
    "https://media.api-sports.io/football/venues/910.png",
    "Stadio Olimpico",
    false,
    emptyList()
)

val samplePlayers by lazy { listOf(samplePlayer) }

val samplePlayer = Player(
    1,
    "Neymar",
    "Neymar",
    "da Silva Santos Junior",
    30,
    "Brazil",
    "175 cm",
    "68 kg",
    false,
    "https://media.api-sports.io/football/players/276.png",
    1,
    emptyList()
)