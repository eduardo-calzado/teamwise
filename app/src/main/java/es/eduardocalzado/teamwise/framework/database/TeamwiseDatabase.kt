package es.eduardocalzado.teamwise.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Team::class,
        Player::class
    ],
    version = 14,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TeamwiseDatabase: RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao
}
