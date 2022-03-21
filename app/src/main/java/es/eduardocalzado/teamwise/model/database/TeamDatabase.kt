package es.eduardocalzado.teamwise.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Team::class],
    version = 1,
    exportSchema = false)
abstract class TeamDatabase: RoomDatabase() {
    abstract fun teamDao(): TeamDao
}