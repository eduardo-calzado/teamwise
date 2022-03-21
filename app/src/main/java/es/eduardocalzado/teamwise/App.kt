package es.eduardocalzado.teamwise

import android.app.Application
import androidx.room.Room
import es.eduardocalzado.teamwise.model.database.TeamDatabase

class App : Application() {

    lateinit var db: TeamDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            TeamDatabase::class.java, "team-db"
        ).build()
    }
}