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
    val favorite: Boolean,
)