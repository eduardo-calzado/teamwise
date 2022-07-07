package es.eduardocalzado.teamwise.di

import android.app.Application
import androidx.room.Room
import es.eduardocalzado.teamwise.framework.AndroidPermissionChecker
import es.eduardocalzado.teamwise.framework.PlayServicesLocationDataSource
import es.eduardocalzado.teamwise.framework.database.TeamDatabase
import es.eduardocalzado.teamwise.framework.database.TeamRoomDataSource
import es.eduardocalzado.teamwise.framework.server.TeamServerDataSource
import es.eduardocalzado.teamwise.data.PermissionChecker
import es.eduardocalzado.teamwise.data.datasource.LocationDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamLocalDataSource
import es.eduardocalzado.teamwise.data.datasource.TeamRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        TeamDatabase::class.java,
        "team-db"
    ).build()

    @Provides
    @Singleton
    fun provideTeamDao(db: TeamDatabase) = db.teamDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindLocalDataSource(localDataSource: TeamRoomDataSource): TeamLocalDataSource
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: TeamServerDataSource): TeamRemoteDataSource
    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource
    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}