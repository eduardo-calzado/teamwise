package es.eduardocalzado.teamwise.ui.detail.player

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.eduardocalzado.teamwise.di.LeagueId
import es.eduardocalzado.teamwise.di.PlayerId
import es.eduardocalzado.teamwise.di.SeasonId
import es.eduardocalzado.teamwise.di.TeamId

@Module
@InstallIn(ViewModelComponent::class)
class PlayerViewModelModule {

    @Provides
    @ViewModelScoped
    @PlayerId
    fun providePlayerId(savedStateHandle: SavedStateHandle) =
        PlayerFragmentArgs.fromSavedStateHandle(savedStateHandle).playerId
}