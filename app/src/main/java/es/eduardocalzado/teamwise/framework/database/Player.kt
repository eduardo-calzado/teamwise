package es.eduardocalzado.teamwise.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player (
    @PrimaryKey val id: Int,
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