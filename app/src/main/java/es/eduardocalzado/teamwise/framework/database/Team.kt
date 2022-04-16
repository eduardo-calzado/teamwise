package es.eduardocalzado.teamwise.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team (
    @PrimaryKey val id: Int,
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