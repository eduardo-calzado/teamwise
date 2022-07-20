package es.eduardocalzado.teamwise.di

import android.app.Application
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.eduardocalzado.teamwise.data.PermissionChecker
import es.eduardocalzado.teamwise.data.datasource.*
import es.eduardocalzado.teamwise.framework.AndroidPermissionChecker
import es.eduardocalzado.teamwise.framework.PlayServicesLocationDataSource
import es.eduardocalzado.teamwise.framework.database.Converters
import es.eduardocalzado.teamwise.framework.database.PlayerRoomDataSource
import es.eduardocalzado.teamwise.framework.database.TeamRoomDataSource
import es.eduardocalzado.teamwise.framework.database.TeamwiseDatabase
import es.eduardocalzado.teamwise.framework.server.PlayerServerDataSource
import es.eduardocalzado.teamwise.framework.server.TeamServerDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TeamwiseDatabase {
        return Room.databaseBuilder(
            app,
            TeamwiseDatabase::class.java,
            "team-db"
        )
        .addTypeConverter(Converters())
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideTeamDao(db: TeamwiseDatabase) = db.teamDao()

    @Provides
    @Singleton
    fun providePlayerDao(db: TeamwiseDatabase) = db.playerDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindTeamLocalDataSource(localDataSource: TeamRoomDataSource): TeamLocalDataSource
    @Binds
    abstract fun bindTeamRemoteDataSource(remoteDataSource: TeamServerDataSource): TeamRemoteDataSource
    @Binds
    abstract fun bindPlayerLocalDataSource(localDataSource: PlayerRoomDataSource): PlayerLocalDataSource
    @Binds
    abstract fun bindPlayerRemoteDataSource(remoteDataSource: PlayerServerDataSource): PlayerRemoteDataSource
    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource
    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}