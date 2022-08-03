package es.eduardocalzado.teamwise.domain

data class Team (
    val id: Int,
    val name: String,
    val code: String?,
    val country: String,
    val founded: Int,
    val national: Boolean,
    val logo: String,
    val address: String?,
    val city: String?,
    val capacity: Int,
    val surface: String?,
    val stadiumImage: String,
    val stadiumName: String,
    val favorite: Boolean,
    val statistics: List<Stats>
) {
    data class Stats(
        val id: Int,
        val league: League,
        val form: String?,
        val fixtures: Fixtures,
        val biggest: Biggest,
        val cleanSheet: CleanSheet,
    ) {
        data class League(
            val id: Int,
            val name: String?,
            val country: String?,
            val logo: String?,
            val flag: String?,
            val season: Int?
        )
        data class Fixtures(
            val played: InnerData?,
            val wins: InnerData?,
            val draws: InnerData?,
            val loses: InnerData?,
        ) {
            data class InnerData(
                val home: Int?,
                val away: Int?,
                val total: Int?
            )
        }
        data class Biggest(
            val streak: StreakData?,
            val wins: HomeAwayData?,
            val loses: HomeAwayData?,
        ) {
            data class StreakData(
                val wins: Int?,
                val draws: Int?,
                val loses: Int?,
            )
            data class HomeAwayData(
                val home: String?,
                val away: String?,
            )
        }
        data class CleanSheet(
            val home: Int?,
            val away: Int?,
            val total: Int?,
        )
    }
}
