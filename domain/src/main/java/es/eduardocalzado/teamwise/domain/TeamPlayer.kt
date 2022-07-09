package es.eduardocalzado.teamwise.domain

data class TeamPlayer (
    val id: Int,
    val name: String,
    val firstName: String?,
    val lastName: String?,
    val age: Int,
    val nationality: String,
    val height: String?,
    val weight: String?,
    val injured: Boolean,
    val photo: String
)