package es.eduardocalzado.teamwise.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Team::class,
        Player::class
    ],
    version = 6,
    exportSchema = false
)
abstract class TeamwiseDatabase: RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao
}
