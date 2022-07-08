package es.eduardocalzado.teamwise.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Team::class],
    version = 2,
    exportSchema = false)
abstract class TeamDatabase: RoomDatabase() {
    abstract fun teamDao(): TeamDao
}